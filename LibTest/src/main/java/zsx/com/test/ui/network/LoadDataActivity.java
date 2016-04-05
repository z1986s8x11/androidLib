package zsx.com.test.ui.network;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.zsx.network.Lib_BaseHttpRequestData;
import com.zsx.network.Lib_HttpRequest;
import com.zsx.network.Lib_HttpResult;
import com.zsx.tools.Lib_LoadingHelper;

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
        findViewById(R.id.btn_load).setOnClickListener(this);
        mMessageTV = (TextView) findViewById(R.id.tv_message);
        loadData = new LoadData(LoadData.Api.PUT, this);
        loadData._setOnLoadingListener(new LoadingHelper(mMessageTV, loadData) {
            @Override
            public void __onComplete(Lib_HttpRequest<String> request, Lib_HttpResult<String> data) {
                mMessageTV.setText(data.getData());
            }

            @Override
            public void __onError(View errorView, Lib_HttpRequest<String> request, Lib_HttpResult<String> data, boolean isAPIError, String error_message) {
                ((TextView) errorView).setText(error_message);
            }
        });
        loadData._refreshData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_load:
                loadData._refreshData();
                break;
        }
    }

    public abstract static class LoadingHelper extends Lib_LoadingHelper<LoadData.Api, String, String> {
        public TextView loadingTV;
        public TextView errorTV;

        public LoadingHelper(View resLayout, Lib_BaseHttpRequestData requestData) {
            super(resLayout);
            loadingTV = new TextView(resLayout.getContext());
            loadingTV.setGravity(Gravity.CENTER);
            loadingTV.setText("loading...");
            _setLoadingView(loadingTV);
            errorTV = new TextView(resLayout.getContext());
            errorTV.setGravity(Gravity.CENTER);
            errorTV.setText("error...");
            _setErrorView(errorTV);
        }
    }
}
