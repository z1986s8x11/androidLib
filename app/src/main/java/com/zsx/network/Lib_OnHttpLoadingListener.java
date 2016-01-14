package com.zsx.network;


public interface Lib_OnHttpLoadingListener<Id,Result, Parameter> {
    /**
     * 开始下载
     *
     * @param id
     */
    void onLoadStart(Id id,Lib_HttpRequest<Parameter> request);

    /**
     * 请求发生错误
     *
     * @param id
     * @param isAPIError    <ul>
     *                      <li>true 解析之后 返回字段有表示请求错误的</li>
     *                      <li>false 请求超时 网络连接异常 解析错误等</li>
     *                      </ul>
     * @param error_message 错误消息
     */
    void onLoadError(Id id, Lib_HttpRequest<Parameter> request, Result result, boolean isAPIError, String error_message);

    /**
     * @param id
     * @param result
     */
    void onLoadComplete(Id id, Lib_HttpRequest<Parameter> request, Result result);
}
