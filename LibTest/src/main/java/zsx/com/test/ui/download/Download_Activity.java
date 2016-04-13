package zsx.com.test.ui.download;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zsx.network.Lib_NetworkStateReceiver;
import com.zsx.util._NetworkUtil;

import zsx.com.test.R;
import zsx.com.test.base._BaseActivity;
import zsx.com.test.ui.network.LoadData;

/**
 * Created by zhusx on 2015/8/6.
 */
public class Download_Activity extends _BaseActivity implements View.OnClickListener {
    TextView mMessageTV;
    LoadData<String> loadData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_file);
        mMessageTV = (TextView) findViewById(R.id.tv_message);
        findViewById(R.id.btn_start).setOnClickListener(this);
        findViewById(R.id.btn_stop).setOnClickListener(this);
        loadData = new LoadData<String>(LoadData.Api.DOWNLOAD, this) {
            @Override
            protected void __onLoadProgress(Api api, String key, int progress, int currentSize, int totalSize) {
                mMessageTV.setText(String.valueOf(progress) + "%");
            }
        };
    }

    @Override
    public void onClick(View v) {
        if (Lib_NetworkStateReceiver._Current_NetWork_Status != _NetworkUtil.NetType.Wifi) {
            _showToast("请在wifi状态下测试...");
            return;
        }
        switch (v.getId()) {
            case R.id.btn_start:
                loadData._loadData();
                break;
            case R.id.btn_stop:
                break;
        }
    }
}
