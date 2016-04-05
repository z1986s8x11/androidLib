package zsx.com.test.ui.network;

import com.zsx.exception.Lib_Exception;
import com.zsx.itf.Lib_LifeCycle;
import com.zsx.itf.Lib_OnCancelListener;
import com.zsx.network.Lib_BaseHttpRequestData;
import com.zsx.network.Lib_HttpParams;
import com.zsx.network.Lib_HttpResult;

import org.apache.http.ParseException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import zsx.com.test.ui.refresh.DataEntity;

/**
 * Created by zhusx on 2015/8/6.
 */
public class LoadData<T> extends Lib_BaseHttpRequestData<LoadData.Api, T, Object> {
    public enum Api {
        PUT, TEST
    }

    public LoadData(LoadData.Api id, Lib_LifeCycle lifeCycle) {
        super(id);
        lifeCycle._addOnCancelListener(new Lib_OnCancelListener() {
            @Override
            public void onCancel() {
                _cancelLoadData();
            }
        });
    }

    @Override
    protected Lib_HttpParams getHttpParams(LoadData.Api id, Object... objects) {
        Lib_HttpParams params = new Lib_HttpParams();
        Map<String, Object> map = new HashMap<>();
        switch (id) {
            case PUT:
                break;
            case TEST:
                break;
        }
        return params;
    }

    @Override
    protected String __requestProtocol(Api api, Lib_HttpParams params) throws ParseException, URISyntaxException, IOException, Lib_Exception {
        switch (api) {
            case TEST:
            case PUT:
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "";
            default:
                return super.__requestProtocol(api, params);
        }

    }

    int i = 0;

    @Override
    protected Lib_HttpResult<T> parseStr(LoadData.Api id, String currentDownloadText, Lib_HttpResult<T> lastData) throws Exception {
        Lib_HttpResult<T> result = new Lib_HttpResult<T>();
        switch (id) {
            case TEST:
                DataEntity d = new DataEntity();
                if (_getRequestParams().isRefresh) {
                    i = 0;
                    d.i = i;
                } else {
                    d.i = i++;
                }
                result.setData((T) d);
                break;
            case PUT:
                result.setData((T) currentDownloadText);
                break;
        }
        result.setSuccess(true);
        return result;
    }


    @Override
    protected String __parseReadHttpCodeError(LoadData.Api id, String errorMessage) throws Exception {
        JSONObject json = new JSONObject(errorMessage);
        return json.getString("message");
    }
}
