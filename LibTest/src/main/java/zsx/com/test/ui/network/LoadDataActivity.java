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
    LoadingHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_loaddata);
        findViewById(R.id.btn_load).setOnClickListener(this);
        mMessageTV = (TextView) findViewById(R.id.tv_message);
        loadData = new LoadData(LoadData.Api.TEST, this);
        helper = new LoadingHelper(mMessageTV, loadData);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_load:
                loadData._loadData();
                break;
        }
    }

    public static class LoadingHelper extends Lib_LoadingHelper<Object, Object, Object> {
        public TextView loadingTV;
        public TextView errorTV;
        public TextView noDataTV;

        public LoadingHelper(View resLayout, Lib_BaseHttpRequestData requestData) {
            super(resLayout);
            _setRepeat(true);
            loadingTV = new TextView(resLayout.getContext());
            loadingTV.setGravity(Gravity.CENTER);
            loadingTV.setText("loading...");
            _setLoadingView(loadingTV);
            errorTV = new TextView(resLayout.getContext());
            errorTV.setGravity(Gravity.CENTER);
            errorTV.setText("error...");
            _setErrorView(errorTV);
            requestData._setOnLoadingListener(this);
//            noDataTV = new TextView(resLayout.getContext());
//            noDataTV.setGravity(Gravity.CENTER);
//            noDataTV.setText("no Data...");
//            setNoDataView(noDataTV);
        }

        @Override
        public void __onComplete(Lib_HttpRequest<Object> request, Lib_HttpResult<Object> data) {

        }
    }
}
