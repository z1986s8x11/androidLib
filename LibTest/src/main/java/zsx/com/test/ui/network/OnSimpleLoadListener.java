package zsx.com.test.ui.network;

import com.zsx.network.Lib_HttpRequest;
import com.zsx.network.Lib_HttpResult;
import com.zsx.network.Lib_OnHttpLoadingListener;

/**
 * Created by zhusx on 2015/8/11.
 */
public class OnSimpleLoadListener<Result> implements Lib_OnHttpLoadingListener<LoadData.Api, Lib_HttpResult<Result>, String> {

    @Override
    public void onLoadStart(LoadData.Api api) {

    }

    @Override
    public void onLoadError(LoadData.Api api, Lib_HttpRequest<String> requestData, Lib_HttpResult<Result> resultLib_httpResult, boolean isAPIError, String error_message) {

    }

    @Override
    public void onLoadComplete(LoadData.Api api, Lib_HttpRequest<String> requestData, Lib_HttpResult<Result> b) {

    }
}
