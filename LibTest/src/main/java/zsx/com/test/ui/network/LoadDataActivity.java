package zsx.com.test.ui.network;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zsx.debug.LogUtil;
import com.zsx.network.Lib_HttpRequest;
import com.zsx.network.Lib_HttpResult;

import zsx.com.test.R;
import zsx.com.test.base._BaseActivity;

/**
 * Created by zhusx on 2015/8/6.
 */
public class LoadDataActivity extends _BaseActivity implements View.OnClickListener {
    TextView mMessageTV;
    LoadData loadData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_loaddata);
        findViewById(R.id.btn_stop).setOnClickListener(this);
        findViewById(R.id.btn_load).setOnClickListener(this);
        findViewById(R.id.btn_loadError).setOnClickListener(this);
        mMessageTV = (TextView) findViewById(R.id.tv_message);
        loadData = new LoadData(LoadData.Api.GET, this);
        loadData._setOnLoadingListener(new OnSimpleLoadListener<String>() {
            @Override
            public void onLoadStart(LoadData.Api id) {
                LogUtil.e("onLoadStart", "onLoadStart");
                mMessageTV.setText("onLoadStart");
            }

            @Override
            public void onLoadError(LoadData.Api id, Lib_HttpRequest<String> requestData, Lib_HttpResult<String> stringLib_httpResult, boolean isAPIError, String error_message) {
                LogUtil.e("onLoadError", String.valueOf(isAPIError) + ":" + String.valueOf(error_message));
                mMessageTV.setText("onLoadError:" + String.valueOf(error_message));
            }

            @Override
            public void onLoadComplete(LoadData.Api id, Lib_HttpRequest<String> requestData, Lib_HttpResult<String> b) {
                LogUtil.e("onLoadComplete", String.valueOf(b.getData()));
                mMessageTV.setText("onLoadComplete" + b.getData());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_stop:
                loadData._cancelLoadData();
                break;
            case R.id.btn_load:
                loadData._refreshData();
                break;
            case R.id.btn_loadError:
                loadData._loadData();
                break;
        }
    }
}
