package zsx.com.test.ui.network;

import com.zsx.exception.Lib_Exception;
import com.zsx.itf.Lib_LifeCycle;
import com.zsx.itf.Lib_OnCancelListener;
import com.zsx.network.Lib_BaseHttpRequestData;
import com.zsx.network.Lib_HttpParams;
import com.zsx.network.Lib_HttpResult;

import org.apache.http.ParseException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhusx on 2015/8/6.
 */
public class LoadData extends Lib_BaseHttpRequestData<LoadData.Api, String, String> {
    public enum Api {
        GET, POST, PUT, DELETE, TEST
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
    protected Lib_HttpParams getHttpParams(LoadData.Api id, String... objects) {
        Lib_HttpParams params = new Lib_HttpParams();
        Map<String, Object> map = new HashMap<>();
        switch (id) {
            case GET:
                params.setRequestMethod(Lib_HttpParams.GET);
                params.setApiUrl("http://api.m.qu.cn/test/get");
                map.put("name", "zhusixiang");
                params.setParams(map);
                break;
            case POST:
                params.setRequestMethod(Lib_HttpParams.POST);
                params.setApiUrl("http://api.m.qu.cn/test/post");
                map.put("name", "zhusixiang");
                params.setParams(map);
                break;
            case PUT:
                params.setRequestMethod(Lib_HttpParams.PUT);
                params.setApiUrl("http://api.m.qu.cn/test/put");
                map.put("name", "zhusixiang");
                params.setParams(map);
                break;
            case DELETE:
                params.setRequestMethod(Lib_HttpParams.DELETE);
                params.setApiUrl("http://api.m.qu.cn/test/delete");
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
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "";
            default:
                return super.__requestProtocol(api, params);
        }

    }

    @Override
    protected Lib_HttpResult<String> parseStr(LoadData.Api id, String currentDownloadText, Lib_HttpResult<String> lastData) throws Exception {
        Lib_HttpResult<String> result = new Lib_HttpResult<String>();
        result.setSuccess(true);
        result.setData(currentDownloadText);
        return result;
    }
}
