package com.zsx.network;

import android.os.Handler;
import android.os.Looper;

import com.zsx.debug.LogUtil;
import com.zsx.exception.Lib_Exception;
import com.zsx.util.Lib_Util_HttpRequest;
import com.zsx.util.Lib_Util_Network;

import org.apache.http.NoHttpResponseException;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @param <Parameter> loadData 参数类型
 * @param <Result>    返回参数
 * @author zsx
 * @date 2014-5-9
 * @description
 */
public abstract class Lib_BaseHttpRequestData<Result, Parameter> {
    private HttpWork pWorkThread;
    private Handler pHandler = new Handler(Looper.getMainLooper());
    private int pId;
    private Lib_HttpResult<Result> pBean;
    private boolean pIsDownding = false;
    private Lib_OnHttpLoadingListener<Lib_HttpResult<Result>, Parameter> pListener;
    private Object lock = new Object();
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

    public void _setOnLoadingListener(
            Lib_OnHttpLoadingListener<Lib_HttpResult<Result>, Parameter> listener) {
        this.pListener = listener;
    }

    public Lib_BaseHttpRequestData(int id) {
        this.pId = id;
    }

    public int _getRequestID() {
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
            if (pWorkThread != null) {
                pWorkThread.cancel();
            }
            pIsDownding = false;
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
        if (Lib_NetworkStateReceiver._Current_NetWork_Status == Lib_Util_Network.NetType.NoneNet) {
            if (LogUtil.DEBUG) {
                LogUtil.e(this, "网络连接异常" + pId);
            }
            onRequestStart(pListener);
            onRequestError(null, false, "网络连接异常", pListener);
            return;
        }
        Lib_HttpParams pParams = getHttpParams(pId, objects);
        onRequestStart(pListener);
        pWorkThread = new HttpWork(pParams, pListener);
        pWorkThread.start();
    }

    private class HttpWork extends Thread {
        private Lib_HttpParams mParams;
        private Lib_OnHttpLoadingListener<Lib_HttpResult<Result>, Parameter> mListener;

        public HttpWork(Lib_HttpParams params, Lib_OnHttpLoadingListener<Lib_HttpResult<Result>, Parameter> listener) {
            this.mParams = params;
            this.mListener = listener;
        }

        public void cancel() {
            this.mListener = null;
        }


        private void onPostError(final Lib_HttpResult<Result> result, final boolean isApiError, final String error_message, final Lib_OnHttpLoadingListener<Lib_HttpResult<Result>, Parameter> listener) {
            pHandler.post(new Runnable() {

                @Override
                public void run() {
                    onRequestError(result, isApiError, error_message, listener);
                }
            });
        }

        private void onPostComplete(final Lib_HttpResult<Result> bean, final Lib_OnHttpLoadingListener<Lib_HttpResult<Result>, Parameter> listener) {
            pHandler.post(new Runnable() {

                @Override
                public void run() {
                    onRequestComplete(bean, listener);
                }
            });
        }

        @Override
        public void run() {
            String returnStr = null;
            String error_message = null;
            try {
                returnStr = __requestProtocol(pId, mParams);
                if (LogUtil.DEBUG) {
                    LogUtil.e("requestData result:", String.valueOf(returnStr));
                }
            } catch (Lib_Exception e) {
                error_message = e._getErrorMessage();
            } catch (ClassCastException e) {
                LogUtil.e(e);
                error_message = "参数类型错误";
            } catch (URISyntaxException e) {
                LogUtil.w(e);
                error_message = "网络地址错误";
            } catch (ConnectTimeoutException e) {
                error_message = "连接超时";
                LogUtil.w(e);
            } catch (SocketTimeoutException e) {
                error_message = "请求超时";
                LogUtil.w(e);
            } catch (ClientProtocolException e) {
                error_message = "网络协议错误";
                LogUtil.w(e);
            } catch (NoHttpResponseException e) {
                error_message = "服务器未响应";
                LogUtil.w(e);
            } catch (IOException e) {
                error_message = "发生未知异常";
                LogUtil.e(e);
            } catch (Exception e) {
                error_message = "发生未知异常";
                LogUtil.e(e);
            }
            if (error_message != null) {
                onPostError(null, false, error_message, mListener);
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
                onPostError(bean, false, "解析异常", mListener);
            }
            if (!isError) {
                if (bean.isSuccess()) {
                    onPostComplete(bean, mListener);
                } else {
                    onPostError(bean, true, bean.getMessage(), mListener);
                }
                pBean = bean;
            }
        }
    }

    @SuppressWarnings("unchecked")
    protected String __requestProtocol(int id, Lib_HttpParams params)
            throws ParseException, URISyntaxException, IOException,
            Lib_Exception {
        String str = null;
        Object paramObject = params.getParams(id);
        switch (params.getRequestMethod()) {
            case Lib_HttpParams.GET:
                String getUrl = null;
                if (paramObject == null) {
                    getUrl = params.getRequestUrl(id);
                } else {
                    String getParam = null;
                    if (paramObject instanceof Map) {
                        Map<String, Object> param = (Map<String, Object>) paramObject;
                        if (param.size() == 0) {
                            getParam = "";
                        } else {
                            getParam = "?";
                            for (String key : param.keySet()) {
                                Object o = param.get(key);
                                getParam += key
                                        + "="
                                        + URLEncoder.encode(
                                        String.valueOf(o == null ? "" : o),
                                        "UTF-8");
                                getParam += "&";
                            }
                            getParam = getParam.substring(0, getParam.length() - 1);
                        }
                    } else {
                        getParam = String.valueOf(paramObject);
                    }
                    getUrl = params.getRequestUrl(id) + getParam;
                }
                if (LogUtil.DEBUG) {
                    LogUtil.e("requestData params:", String.valueOf(getUrl));
                }
                str = Lib_Util_HttpRequest._get(getUrl);
                break;
            case Lib_HttpParams.POST:
                if (LogUtil.DEBUG) {
                    LogUtil.e("requestData params:", String.valueOf(paramObject));
                }
                if (paramObject instanceof Map) {
                    str = Lib_Util_HttpRequest._post(params.getRequestUrl(id), (Map<String, Object>) paramObject);
                } else if (paramObject instanceof JSONObject) {
                    str = Lib_Util_HttpRequest._post(params.getRequestUrl(id), (JSONObject) paramObject);
                } else {
                    str = Lib_Util_HttpRequest._post(params.getRequestUrl(id), String.valueOf(paramObject));
                }
                break;
            default:
                throw new IllegalArgumentException("没有此请求方法");
        }
        return str;
    }


    private void onRequestStart(Lib_OnHttpLoadingListener<Lib_HttpResult<Result>, Parameter> listener) {
        pIsDownding = true;
        __onStart(pId);
        if (listener != null) {
            listener.onLoadStart(pId);
        }
    }

    private final void onRequestError(Lib_HttpResult<Result> result, boolean isApiError, String error_message, Lib_OnHttpLoadingListener<Lib_HttpResult<Result>, Parameter> listener) {
        pIsDownding = false;
        __onError(pId, pLastRequestData, result, isApiError, error_message);
        if (listener != null) {
            listener.onLoadError(pId, pLastRequestData, result, isApiError, error_message);
        }
    }

    private final void onRequestComplete(Lib_HttpResult<Result> bean, Lib_OnHttpLoadingListener<Lib_HttpResult<Result>, Parameter> listener) {
        pIsDownding = false;
        __onComplete(pId, pLastRequestData, bean);
        if (listener != null) {
            listener.onLoadComplete(pId, pLastRequestData, bean);
        }
    }

    /**
     * 构造请求参数
     *
     * @param objects
     * @return
     */
    protected abstract Lib_HttpParams getHttpParams(int id,
                                                    Parameter... objects);

    /**
     * @param currentDownloadText Http请求的数据
     * @param lastData            上一次 parseStr()方法返回的数据
     * @return 会在onComplete()中回调出去
     * @throws Exception
     */
    protected abstract Lib_HttpResult<Result> parseStr(int id, String currentDownloadText,
                                                       Lib_HttpResult<Result> lastData) throws Exception;

    /**
     * 开始下载
     *
     * @param id
     */
    protected void __onStart(int id) {
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
    protected void __onError(int id, Lib_HttpRequest<Parameter> requestData,
                             Lib_HttpResult<Result> result, boolean isAPIError, String error_message) {
    }

    protected void __onComplete(int id, Lib_HttpRequest<Parameter> requestData,
                                Lib_HttpResult<Result> b) {
    }
}
