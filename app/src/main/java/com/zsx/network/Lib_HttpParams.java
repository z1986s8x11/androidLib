package com.zsx.network;

import org.json.JSONObject;

import java.util.Map;
import java.util.Set;

/**
 * 请求 参数 JavaBean
 *
 * @author zsx
 * @date 2014-06-16
 */
public class Lib_HttpParams {
    public static final String GET = "GET";
    public static final String POST = "POST";
    private String apiUrl;
    private String mothod = POST;
    private Object param;

    public Object getParam() {
        return param;
    }

    public Lib_HttpParams() {
    }

    public Lib_HttpParams(String url, String method, JSONObject param) {
        this.apiUrl = url;
        this.mothod = method;
        this.param = param;
    }

    public Lib_HttpParams(String url, String mothod,
                          Map<String, Object> param) {
        this.apiUrl = url;
        this.mothod = mothod;
        this.param = param;
    }

    /**
     * @return 请求URL
     */
    public final String getRequestUrl() {
        return apiUrl;
    }

    /**
     * 设置请求URL
     *
     * @param apiUrl
     */
    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    /**
     * 设置请求方式
     *
     * @param mothod
     */
    public final void setRequestMethod(String mothod) {
        this.mothod = mothod;
    }

    /**
     * @return 拿到请求参数
     */
    protected Object getParams() {
        return param;
    }

    public void setParams(Map<String, Object> param) {
        this.param = param;
    }

    public void setParams(JSONObject param) {
        this.param = param;
    }

    public void setParams(Set<Object> param) {
        this.param = param;
    }

    public void setParams(String param) {
        this.param = param;
    }

    /**
     * 拿到请求方式
     *
     * @return
     */
    public final String getRequestMethod() {
        return mothod;
    }
}
