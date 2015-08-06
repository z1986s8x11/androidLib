package com.zsx.network;


/**
 * @Author zsx
 * @date 2015-5-6
 */
public class Lib_SimpleOnHttpLoadingListener<Result, Parameter> implements
        Lib_OnHttpLoadingListener<Lib_HttpResult<Result>, Parameter> {

    @Override
    public void onLoadStart(int id) {

    }

    @Override
    public void onLoadError(int id, Lib_HttpRequest<Parameter> requestData, Lib_HttpResult<Result> result, boolean isAPIError, String error_message) {

    }

    @Override
    public void onLoadComplete(int id, Lib_HttpRequest<Parameter> requestData, Lib_HttpResult<Result> result) {

    }
}
