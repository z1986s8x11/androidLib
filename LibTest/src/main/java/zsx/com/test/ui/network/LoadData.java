package zsx.com.test.ui.network;

import com.zsx.itf.Lib_LifeCycle;
import com.zsx.itf.Lib_OnCancelListener;
import com.zsx.network.Lib_BaseHttpRequestData;
import com.zsx.network.Lib_HttpParams;
import com.zsx.network.Lib_HttpResult;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhusx on 2015/8/6.
 */
public class LoadData extends Lib_BaseHttpRequestData<LoadData.Api, String, String> {
    public enum Api {
        GET, POST, PUT, DELETE
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
        switch (id) {
            case GET:
                params.setRequestMethod(Lib_HttpParams.GET);
//                params.setApiUrl("http://192.168.10.202/test/get");
                break;
            case POST:
                params.setRequestMethod(Lib_HttpParams.POST);
//                params.setApiUrl("http://192.168.10.202/test/post");
                break;
            case PUT:
                params.setRequestMethod(Lib_HttpParams.PUT);
//                params.setApiUrl("http://192.168.10.202/test/put");
                break;
            case DELETE:
                params.setRequestMethod(Lib_HttpParams.DELETE);
//                params.setApiUrl("http://192.168.10.202/test/delete");
                break;
        }
        params.setApiUrl("http://3.cqgod.sinaapp.com/tanmao.php");
        Map<String, Object> map = new HashMap<>();
        map.put("name", "zhusixiang");
        params.setParams(map);
        return params;
    }

    @Override
    protected Lib_HttpResult<String> parseStr(LoadData.Api id, String currentDownloadText, Lib_HttpResult<String> lastData) throws Exception {
        Lib_HttpResult<String> result = new Lib_HttpResult<String>();
        result.setSuccess(true);
        result.setData(currentDownloadText);
        return result;
    }
}
