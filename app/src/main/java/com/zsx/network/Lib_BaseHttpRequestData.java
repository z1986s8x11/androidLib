package com.zsx.network;

import android.os.Handler;
import android.os.Looper;

import com.zsx.app.Lib_BaseApplication;
import com.zsx.debug.LogUtil;
import com.zsx.exception.Lib_Exception;

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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @param <Parameter> loadData 参数类型
 * @param <Result>    返回参数
 * @author zsx
 * @date 2014-5-9
 * @description
 */
public abstract class Lib_BaseHttpRequestData<Result, Parameter> {
    /**
     * 默认所有请求可同时开2线程
     */
    private static ExecutorService HTTPEXCUTORS = Executors
            .newFixedThreadPool(2);
    private Handler pHandler = new Handler(Looper.getMainLooper());
    private int id;
    private Result pBean;
    private boolean b_isDownding = false;
    private Lib_OnHttpLoadingListener<Result, Parameter> listener;
    private Future<?> future;

    private RequestData<Parameter> lastRequestData;

    public static class RequestData<Parameter> {
        public Parameter[] lastObjectsParams;
        public boolean isRefresh;

        /**
         * 外部不能创建
         */
        private RequestData() {
        }

        public void reset() {
            lastObjectsParams = null;
            isRefresh = false;
        }
    }

    /**
     * @return 最后一次调教的参数
     */
    public Parameter[] _getLastObjectsParams() {
        if (lastRequestData != null) {
            return lastRequestData.lastObjectsParams;
        }
        return null;
    }

    public void _setOnLoadingListener(
            Lib_OnHttpLoadingListener<Result, Parameter> listener) {
        this.listener = listener;
    }

    public Lib_BaseHttpRequestData(int id) {
        this.id = id;
    }

    public int _getRequestID() {
        return id;
    }

    public void _loadData(Parameter... objects) {
        requestData(false, objects);
    }

    public void _refreshData(Parameter... objects) {
        requestData(true, objects);
    }

    public void _refreshData() {
        if (lastRequestData.lastObjectsParams != null) {
            requestData(true, lastRequestData.lastObjectsParams);
        } else {
            if (LogUtil.DEBUG) {
                LogUtil.e(this, "requestData(Objects... objs) 从未主动加载过数据 不能直接刷新");
            }
        }
    }

    public boolean _isLoading() {
        return b_isDownding;
    }

    public boolean _hasCache() {
        return pBean != null;
    }

    public void _clearData() {
        pBean = null;
    }

    public Result _getLastData() {
        return pBean;
    }

    /**
     * 不成功
     *
     * @Deprecated
     */
    public void _cancelLoadData() {
        if (b_isDownding) {
            if (future != null) {
                future.cancel(true);
            }
            b_isDownding = false;
        }
    }

    public static void _shutdownAll() {
        HTTPEXCUTORS.shutdownNow();
        HTTPEXCUTORS = null;
        HTTPEXCUTORS = Executors.newFixedThreadPool(2);
    }

    private synchronized  void requestData(boolean isRefresh,
                                                Parameter... objects) {
        RequestData<Parameter> request = new RequestData<Parameter>();
        request.lastObjectsParams = objects;
        request.isRefresh = isRefresh;
        if (b_isDownding) {
            if (LogUtil.DEBUG) {
                LogUtil.e(this, "id:" + id + "\t 正在下载" + id);
            }
            return;
        }
        if (Lib_BaseApplication._Current_NetWork_Status == NetworkState.NetType.NoneNet) {
            if (LogUtil.DEBUG) {
                LogUtil.e(this, "网络链接异常" + id);
            }
            onRequestStart(id, request);
            onRequestError(id, request, "网络链接异常");
            return;
        }
        b_isDownding = true;
        Lib_HttpParams pParams = getHttpParams(id, objects);
        future = HTTPEXCUTORS.submit(new HttpWork(pParams, request));
    }

    private class HttpWork implements Runnable {
        private Lib_HttpParams params;
        private String error_message;
        private RequestData<Parameter> requestData;

        public HttpWork(Lib_HttpParams params,
                        RequestData<Parameter> requestData) {
            this.params = params;
            this.requestData = requestData;
            lastRequestData = requestData;
        }

        @Override
        public void run() {
            b_isDownding = true;
            String str = null;
            pHandler.post(new Runnable() {

                @Override
                public void run() {
                    onRequestStart(id, requestData);
                }
            });
            error_message = null;
            try {
                str = __requestProtocol(id, params);
                if (LogUtil.DEBUG) {
                    LogUtil.e("requestData result:", String.valueOf(str));
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
            if (str == null) {
                pHandler.post(new Runnable() {

                    @Override
                    public void run() {
                        onRequestError(id, requestData, error_message);
                    }
                });
            } else {
                final String returnStr = str;
                pHandler.post(new Runnable() {

                    @Override
                    public void run() {
                        onRequestComplete(id, requestData, pBean, returnStr);
                    }
                });
            }
        }
    }

    @SuppressWarnings("unchecked")
    protected String __requestProtocol(int id, Lib_HttpParams params)
            throws ParseException, URISyntaxException, IOException,
            Lib_Exception {
        String str;
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
                str = Lib_HttpRequest._get(getUrl);
                break;
            case Lib_HttpParams.POST:
                if (LogUtil.DEBUG) {
                    LogUtil.e("requestData params:", String.valueOf(paramObject));
                }
                if (paramObject instanceof Map) {
                    str = Lib_HttpRequest._post(params.getRequestUrl(id),
                            (Map<String, Object>) paramObject);
                } else if (paramObject instanceof JSONObject) {
                    str = Lib_HttpRequest._post(params.getRequestUrl(id),
                            (JSONObject) paramObject);
                } else {
                    str = Lib_HttpRequest._post(params.getRequestUrl(id),
                            String.valueOf(paramObject));
                }
                break;
            default:
                throw new IllegalArgumentException("没有此请求方法");
        }
        return str;
    }


    private void onRequestStart(int id, RequestData<Parameter> requestData) {
        b_isDownding = true;
        __onStart(id, requestData);
        if (listener != null) {
            listener.onLoadStart(id, requestData);
        }
    }

    private final void onRequestError(int id,
                                      RequestData<Parameter> requestData, String error_message) {
        b_isDownding = false;
        __onError(id, requestData, null, false, error_message);
        if (listener != null) {
            listener.onLoadError(id, this, requestData, null, false,
                    error_message);
        }
    }

    /**
     * 检查获取的数据 是否是 服务器成功返回的数据
     *
     * @param bean 并且parseStr()成功解析
     */
    protected abstract boolean __isSucess(int id, Result bean);

    /**
     * 如果 __isSucess() 返回false 将会调用
     *
     * @return 返回服务器返回的错误信息
     */
    protected abstract String __getErrorMessage(int id, Result bean);

    private final void onRequestComplete(int id,
                                         RequestData<Parameter> requestData, Result source, String returnStr) {
        b_isDownding = false;
        boolean isError = false;
        Result bean = null;
        try {
            bean = parseStr(id, returnStr, source);
        } catch (Exception e) {
            isError = true;
            if (LogUtil.DEBUG) {
                LogUtil.e(e);
            }
            __onError(id, requestData, bean, false, "解析发生异常");
            if (listener != null) {
                listener.onLoadError(id, this, requestData, bean, false,
                        "解析发生异常");
            }

        }
        if (!isError) {
            if (__isSucess(id, bean)) {
                __onComplete(id, requestData, bean);
                if (listener != null) {
                    listener.onLoadComplete(id, requestData, bean);
                }
            } else {
                __onError(id, requestData, bean, true,
                        __getErrorMessage(id, bean));
                if (listener != null) {
                    listener.onLoadError(id, this, requestData, bean, true,
                            __getErrorMessage(id, bean));
                }
            }
            pBean = bean;
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
    protected abstract Result parseStr(int id, String currentDownloadText,
                                       Result lastData) throws Exception;

    /**
     * 开始下载
     *
     * @param id
     */
    protected void __onStart(int id, RequestData<Parameter> requestData) {
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
    protected void __onError(int id, RequestData<Parameter> requestData,
                             Result result, boolean isAPIError, String error_message) {
    }

    protected void __onComplete(int id, RequestData<Parameter> requestData,
                                Result b) {
    }
}
