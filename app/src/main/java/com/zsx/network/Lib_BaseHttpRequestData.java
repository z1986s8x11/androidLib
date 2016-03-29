package com.zsx.network;

import android.os.Handler;
import android.os.Looper;

import com.zsx.debug.LogUtil;
import com.zsx.exception.Lib_Exception;
import com.zsx.util.Lib_Util_HttpURLRequest;
import com.zsx.util._NetworkUtil;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URISyntaxException;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * @param <Parameter> loadData 参数类型
 * @param <Result>    返回参数
 * @author zsx
 * @date 2014-5-9
 * @description
 */
public abstract class Lib_BaseHttpRequestData<Id, Result, Parameter> {
    private HttpWork pWorkThread;
    private Handler pHandler = new Handler(Looper.getMainLooper());
    private Id pId;
    private Lib_HttpResult<Result> pBean;
    private boolean pIsDownding = false;
    private Set<Lib_OnHttpLoadingListener<Id, Lib_HttpResult<Result>, Parameter>> pListeners = new LinkedHashSet<>();
    private Lib_HttpRequest<Parameter> pLastRequestData;

    /**
     * @return 最后一次调教的参数
     */
    public Lib_HttpRequest<Parameter> _getRequestParams() {
        if (pLastRequestData != null) {
            return pLastRequestData;
        }
        return null;
    }

    public void _setOnLoadingListener(Lib_OnHttpLoadingListener<Id, Lib_HttpResult<Result>, Parameter> listener) {
        pListeners.clear();
        pListeners.add(listener);
    }

    public void _addOnLoadingListener(Lib_OnHttpLoadingListener<Id, Lib_HttpResult<Result>, Parameter> listener) {
        pListeners.add(listener);
    }

    public Lib_BaseHttpRequestData(Id id) {
        this.pId = id;
    }

    public Id _getRequestID() {
        return pId;
    }

    public void _loadData(Parameter... objects) {
        requestData(false, objects);
    }

    public void _refreshData(Parameter... objects) {
        requestData(true, objects);
    }

    public void _reLoadData() {
        if (pLastRequestData != null) {
            requestData(pLastRequestData.isRefresh, pLastRequestData.lastObjectsParams);
        } else {
            if (LogUtil.DEBUG) {
                LogUtil.e(this, "requestData(Objects... objs) 从未主动加载过数据 不能直接刷新");
            }
        }
    }

    public boolean _isLoading() {
        return pIsDownding;
    }

    public boolean _hasCache() {
        return pBean != null;
    }

    public void _clearData() {
        pBean = null;
    }

    public Lib_HttpResult<Result> _getLastData() {
        return pBean;
    }

    /**
     * 不成功
     *
     * @Deprecated
     */
    public void _cancelLoadData() {
        if (pIsDownding) {
            pIsDownding = false;
            if (pWorkThread != null) {
                pWorkThread.cancel();
            }
        }
    }

    private synchronized void requestData(boolean isRefresh,
                                          Parameter... objects) {
        if (pIsDownding) {
            if (LogUtil.DEBUG) {
                LogUtil.e(this, "id:" + pId + "\t 正在下载");
            }
            return;
        }
        if (pLastRequestData == null) {
            pLastRequestData = new Lib_HttpRequest<>();
        }
        pLastRequestData.lastObjectsParams = objects;
        pLastRequestData.isRefresh = isRefresh;
        if (Lib_NetworkStateReceiver._Current_NetWork_Status == _NetworkUtil.NetType.NoneNet) {
            if (LogUtil.DEBUG) {
                LogUtil.e(this, "网络连接异常" + pId);
            }
            onRequestStart(pListeners, pLastRequestData);
            onRequestError(null, false, "网络连接异常", pListeners);
            return;
        }
        Lib_HttpParams pParams = getHttpParams(pId, objects);
        onRequestStart(pListeners, pLastRequestData);
        pWorkThread = new HttpWork(pParams, pListeners);
        pWorkThread.start();
    }

    private class HttpWork extends Thread {
        private Lib_HttpParams mParams;
        private Set<Lib_OnHttpLoadingListener<Id, Lib_HttpResult<Result>, Parameter>> mListeners;
        private boolean isCancel = false;

        public HttpWork(Lib_HttpParams params, Set<Lib_OnHttpLoadingListener<Id, Lib_HttpResult<Result>, Parameter>> listeners) {
            this.mParams = params;
            this.mListeners = listeners;
        }

        public void cancel() {
            this.isCancel = true;
            this.mListeners.clear();
        }


        private void onPostError(final Lib_HttpResult<Result> result, final boolean isApiError, final String error_message, final Set<Lib_OnHttpLoadingListener<Id, Lib_HttpResult<Result>, Parameter>> listeners) {
            if (isCancel) {
                pIsDownding = false;
                return;
            }
            pHandler.post(new Runnable() {

                @Override
                public void run() {
                    onRequestError(result, isApiError, error_message, listeners);
                }
            });
        }

        private void onPostComplete(final Lib_HttpResult<Result> bean, final Set<Lib_OnHttpLoadingListener<Id, Lib_HttpResult<Result>, Parameter>> listeners) {
            if (isCancel) {
                pIsDownding = false;
                return;
            }
            pHandler.post(new Runnable() {

                @Override
                public void run() {
                    onRequestComplete(bean, listeners);
                }
            });
        }

        @Override
        public void run() {
            String returnStr = null;
            String error_message = null;
            int error_code = 0;
            try {
                returnStr = __requestProtocol(pId, mParams);
            } catch (Lib_Exception e) {
                if (e._getErrorCode() > HttpURLConnection.HTTP_OK) {
                    error_code = e._getErrorCode();
                    try {
                        error_message = __parseReadHttpCodeError(pId, e._getErrorMessage());
                    } catch (Exception ee) {
                        error_message = e._getErrorMessage();
                    }
                } else {
                    error_message = e._getErrorMessage();
                }
            } catch (ClassCastException e) {
                LogUtil.e(e);
                error_message = "参数类型错误";
            } catch (URISyntaxException e) {
                LogUtil.w(e);
                error_message = "网络地址错误";
            } catch (SocketTimeoutException e) {
                error_message = "请求超时";
                LogUtil.w(e);
            } catch (IOException e) {
                error_message = "发生未知异常";
                LogUtil.e(e);
            } catch (Exception e) {
                error_message = "发生未知异常";
                LogUtil.e(e);
            }
            if (error_message != null) {
                Lib_HttpResult<Result> xx = null;
                if (error_code != 0) {
                    xx = new Lib_HttpResult<>();
                    xx.setSuccess(false);
                    xx.setMessage(error_message);
                    xx.setErrorCode(error_code);
                }
                onPostError(xx, false, error_message, mListeners);
                return;
            }
            boolean isError = false;
            Lib_HttpResult<Result> bean = null;
            try {
                bean = parseStr(pId, returnStr, pBean);
            } catch (Exception e) {
                isError = true;
                if (LogUtil.DEBUG) {
                    LogUtil.e(e);
                }
                onPostError(bean, false, "解析异常", mListeners);
            }
            if (!isError) {
                if (bean.isSuccess()) {
                    onPostComplete(bean, mListeners);
                } else {
                    onPostError(bean, true, bean.getMessage(), mListeners);
                }
                pBean = bean;
            }
        }
    }

    @SuppressWarnings("unchecked")
    protected String __requestProtocol(Id id, Lib_HttpParams params)
            throws URISyntaxException, IOException,
            Lib_Exception {
        String str;
        Object paramObject = params.getParams();
        switch (params.getRequestMethod()) {
            case Lib_HttpParams.GET:
                String getUrl = null;
                if (paramObject == null) {
                    getUrl = params.getRequestUrl();
                } else {
                    if (paramObject instanceof Map) {
                        Map<String, Object> param = (Map<String, Object>) paramObject;
                        getUrl = Lib_Util_HttpURLRequest.encodeUrl(params.getRequestUrl(), param);
                    } else {
                        getUrl = params.getRequestUrl() + String.valueOf(paramObject);
                    }
                }
                str = Lib_Util_HttpURLRequest.httpRequest(params.getRequestMethod(), getUrl, null, null, params.getHttpHead(), params.isReadHttpCodeErrorMessage());
                break;
            case Lib_HttpParams.POST:
            case Lib_HttpParams.PUT:
            case Lib_HttpParams.DELETE:
                if (paramObject instanceof Map) {
                    str = Lib_Util_HttpURLRequest.httpRequest(params.getRequestMethod(), params.getRequestUrl(), (Map<String, Object>) paramObject, params.getHttpHead(), params.isReadHttpCodeErrorMessage());
                } else {
                    str = Lib_Util_HttpURLRequest.httpRequest(params.getRequestMethod(), params.getRequestUrl(), paramObject == null ? "" : paramObject.toString(), params.getHttpHead(), params.isReadHttpCodeErrorMessage());
                }
                break;
            default:
                throw new IllegalArgumentException("没有此请求方法");
        }
        return str;
    }


    private void onRequestStart(Set<Lib_OnHttpLoadingListener<Id, Lib_HttpResult<Result>, Parameter>> listeners, Lib_HttpRequest<Parameter> request) {
        pIsDownding = true;
        __onStart(pId, request);
        for (Lib_OnHttpLoadingListener<Id, Lib_HttpResult<Result>, Parameter> listener : listeners) {
            if (listener != null) {
                listener.onLoadStart(pId, request);
            }
        }
    }

    private final void onRequestError(Lib_HttpResult<Result> result, boolean isApiError, String error_message, Set<Lib_OnHttpLoadingListener<Id, Lib_HttpResult<Result>, Parameter>> listeners) {
        pIsDownding = false;
        __onError(pId, pLastRequestData, result, isApiError, error_message);
        for (Lib_OnHttpLoadingListener<Id, Lib_HttpResult<Result>, Parameter> listener : listeners) {
            if (listener != null) {
                listener.onLoadError(pId, pLastRequestData, result, isApiError, error_message);
            }
        }
    }

    private final void onRequestComplete(Lib_HttpResult<Result> bean, Set<Lib_OnHttpLoadingListener<Id, Lib_HttpResult<Result>, Parameter>> listeners) {
        pIsDownding = false;
        __onComplete(pId, pLastRequestData, bean);
        for (Lib_OnHttpLoadingListener<Id, Lib_HttpResult<Result>, Parameter> listener : listeners) {
            if (listener != null) {
                listener.onLoadComplete(pId, pLastRequestData, bean);
            }
        }
    }

    /**
     * 构造请求参数
     *
     * @param objects
     * @return
     */
    protected abstract Lib_HttpParams getHttpParams(Id id,
                                                    Parameter... objects);

    /**
     * @param currentDownloadText Http请求的数据
     * @param lastData            上一次 parseStr()方法返回的数据
     * @return 会在onComplete()中回调出去
     * @throws Exception
     */
    protected abstract Lib_HttpResult<Result> parseStr(Id id, String currentDownloadText,
                                                       Lib_HttpResult<Result> lastData) throws Exception;

    /**
     * 开始下载
     *
     * @param id
     */
    protected void __onStart(Id id, Lib_HttpRequest<Parameter> request) {
    }

    /**
     * 请求发生错误
     *
     * @param id
     * @param isAPIError    <ul>
     *                      <li>true parStr 解析错误</li>
     *                      <li>false 请求超时 网络连接异常等</li>
     *                      </ul>
     * @param result        当前请求解析返回 如果false result ==null;
     * @param error_message 错误消息
     */
    protected void __onError(Id id, Lib_HttpRequest<Parameter> requestData,
                             Lib_HttpResult<Result> result, boolean isAPIError, String error_message) {
    }

    protected void __onComplete(Id id, Lib_HttpRequest<Parameter> requestData,
                                Lib_HttpResult<Result> b) {
    }

    /**
     * 解析HttpCode !=200 的错误信息
     */
    protected String __parseReadHttpCodeError(Id id, String errorMessage) throws Exception {
        return errorMessage;
    }
}
