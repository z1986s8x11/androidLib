package zsx.com.test.ui.network;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.zsx.network.Lib_HttpRequest;
import com.zsx.network.Lib_HttpResult;

import zsx.com.test.R;
import zsx.com.test.base._BaseActivity;

/**
 * Created by Administrator on 2015/11/24.
 */
public class HttpRequestActivity extends _BaseActivity implements View.OnClickListener {
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_httprequest);
        findViewById(R.id.btn_get).setOnClickListener(this);
        findViewById(R.id.btn_post).setOnClickListener(this);
        findViewById(R.id.btn_put).setOnClickListener(this);
        findViewById(R.id.btn_delete).setOnClickListener(this);
        mTextView = (TextView) findViewById(R.id.tv_message);
        mTextView.setFocusable(true);
        mTextView.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_get:
                new LoadData<String>(LoadData.Api.GET, this) {
                    @Override
                    protected void __onComplete(Api api, Lib_HttpRequest<Object> requestData, Lib_HttpResult<String> b) {
                        super.__onComplete(api, requestData, b);
                        mTextView.setText(b.getData());
                    }

                    @Override
                    protected void __onError(Api api, Lib_HttpRequest<Object> requestData, Lib_HttpResult<String> result, boolean isAPIError, String error_message) {
                        super.__onError(api, requestData, result, isAPIError, error_message);
                        mTextView.setText(error_message);
                    }
                }._loadData();
                break;
            case R.id.btn_post:
                new LoadData<String>(LoadData.Api.POST, this) {
                    @Override
                    protected void __onComplete(Api api, Lib_HttpRequest<Object> requestData, Lib_HttpResult<String> b) {
                        super.__onComplete(api, requestData, b);
                        mTextView.setText(b.getData());
                    }

                    @Override
                    protected void __onError(Api api, Lib_HttpRequest<Object> requestData, Lib_HttpResult<String> result, boolean isAPIError, String error_message) {
                        super.__onError(api, requestData, result, isAPIError, error_message);
                        mTextView.setText(error_message);
                    }
                }._loadData();
                break;
            case R.id.btn_put:
                new LoadData<String>(LoadData.Api.PUT, this) {
                    @Override
                    protected void __onError(Api api, Lib_HttpRequest<Object> requestData, Lib_HttpResult<String> result, boolean isAPIError, String error_message) {
                        super.__onError(api, requestData, result, isAPIError, error_message);
                        mTextView.setText(error_message);
                    }

                    @Override
                    protected void __onComplete(Api api, Lib_HttpRequest<Object> requestData, Lib_HttpResult<String> b) {
                        super.__onComplete(api, requestData, b);
                        mTextView.setText(b.getData());
                    }
                }._loadData();
                break;
            case R.id.btn_delete:
                new LoadData<String>(LoadData.Api.DELETE, this) {
                    @Override
                    protected void __onError(Api api, Lib_HttpRequest<Object> requestData, Lib_HttpResult<String> result, boolean isAPIError, String error_message) {
                        super.__onError(api, requestData, result, isAPIError, error_message);
                        mTextView.setText(error_message);
                    }

                    @Override
                    protected void __onComplete(Api api, Lib_HttpRequest<Object> requestData, Lib_HttpResult<String> b) {
                        super.__onComplete(api, requestData, b);
                        mTextView.setText(b.getData());
                    }
                }._loadData();
                break;
        }
    }
}
