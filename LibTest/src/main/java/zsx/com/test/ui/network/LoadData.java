package zsx.com.test.ui.network;

import com.zsx.debug.LogUtil;
import com.zsx.exception.Lib_Exception;
import com.zsx.network.Lib_BaseHttpRequestData;
import com.zsx.network.Lib_HttpParams;
import com.zsx.network.Lib_HttpResult;

import org.apache.http.ParseException;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by zhusx on 2015/8/6.
 */
public class LoadData extends Lib_BaseHttpRequestData<String, String> {
    public LoadData(int id) {
        super(id);
    }

    @Override
    protected Lib_HttpParams getHttpParams(int id, String... objects) {
        Lib_HttpParams params = new Lib_HttpParams();
        params.setRequestMethod(Lib_HttpParams.POST);
        params.setApiUrl("http://www.baidu.com");
        return params;
    }

    @Override
    protected Lib_HttpResult<String> parseStr(int id, String currentDownloadText, Lib_HttpResult<String> lastData) throws Exception {
        Lib_HttpResult<String> result = new Lib_HttpResult<String>();
        result.setMessage(_getRequestParams().isRefresh ? "成功" : "失败");
        result.setSuccess(_getRequestParams().isRefresh);
        result.setData("data");
        return result;
    }

    @Override
    protected String __requestProtocol(int id, Lib_HttpParams params) throws ParseException, URISyntaxException, IOException, Lib_Exception {
        int i = 0;
        while (i < 1) {
            i++;
            try {
                LogUtil.e(this, "loading..." + i);
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return "成功拿到数据";
    }
}
