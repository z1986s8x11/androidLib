package com.zsx.network;

import org.json.JSONObject;

import java.util.Map;
import java.util.Set;

/**
 * 请求 参数 JavaBean
 * 
 * @author zsx
 * 
 * @date 2014-06-16
 */
public class Lib_HttpParams {
	private String apiUrl;
	private HTTP_METHOD mothod = HTTP_METHOD.POST;
	private Object param;
	private Object obj;

	public Object getExtraParams() {
		return obj;
	}

	public static enum HTTP_METHOD {
		POST, GET
	}

	public Object getParam() {
		return param;
	}

	public Lib_HttpParams() {
	}

	public void setExtraParams(Object obj) {
		this.obj = obj;
	}

	public Lib_HttpParams(String url, HTTP_METHOD mothod, JSONObject param) {
		this.apiUrl = url;
		this.mothod = mothod;
		this.param = param;
	}

	public Lib_HttpParams(String url, HTTP_METHOD mothod,
			Map<String, Object> param) {
		this.apiUrl = url;
		this.mothod = mothod;
		this.param = param;
	}

	/**
	 * @return 请求URL
	 */
	public final String getRequestUrl(int id) {
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
	public final void setRequestMethod(HTTP_METHOD mothod) {
		this.mothod = mothod;
	}

	/**
	 * @return 拿到请求参数
	 */
	protected Object getParams(int id) {
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
	public final HTTP_METHOD getRequestMethod() {
		return mothod;
	}
}
