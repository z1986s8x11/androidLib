package com.zsx.network;


/**
 * @Author zsx
 * @date 2015-5-6
 */
public class Lib_SimpleOnHttpLoadingListener<Result, Parameter> implements
        Lib_OnHttpLoadingListener<Result, Parameter> {

    @Override
    public void onLoadStart(int id, Lib_BaseHttpRequestData.RequestData<Parameter> requestData) {
    }

    @Override
    public void onLoadError(int id,
                            Lib_BaseHttpRequestData<Result, Parameter> loadData,
                            Lib_BaseHttpRequestData.RequestData<Parameter> requestData, Result result,
                            boolean isAPIError, String error_message) {
    }

    @Override
    public void onLoadComplete(int id, Lib_BaseHttpRequestData.RequestData<Parameter> requestData,
                               Result b) {
    }

}
