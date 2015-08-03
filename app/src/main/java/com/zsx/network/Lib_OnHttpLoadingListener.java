package com.zsx.network;


public interface Lib_OnHttpLoadingListener<Result, Parameter> {
	/**
	 * 开始下载
	 * 
	 * @param id
	 */
	void onLoadStart(int id, Lib_BaseHttpRequestData.RequestData<Parameter> requestData);

	/**
	 * 请求发生错误
	 * 
	 * @param id
	 * @param isAPIError
	 *            <ul>
	 *            <li>true 解析之后 返回字段有表示请求错误的</li>
	 *            <li>false 请求超时 网络连接异常 解析错误等</li>
	 *            </ul>
	 * @param error_message
	 *            错误消息
	 */
	void onLoadError(int id,
					 Lib_BaseHttpRequestData<Result, Parameter> loadData,
					 Lib_BaseHttpRequestData.RequestData<Parameter> requestData, Result result,
					 boolean isAPIError, String error_message);

	/**
	 * 
	 * @param id
	 * @param b
	 */
	void onLoadComplete(int id, Lib_BaseHttpRequestData.RequestData<Parameter> requestData, Result b);
}
