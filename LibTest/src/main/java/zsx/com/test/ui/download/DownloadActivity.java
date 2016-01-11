package zsx.com.test.ui.download;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zsx.network.Lib_NetworkStateReceiver;
import com.zsx.util.Lib_Util_Network;

import zsx.com.test.R;
import zsx.com.test.base._BaseActivity;

/**
 * Created by zhusx on 2015/8/6.
 */
public class DownloadActivity extends _BaseActivity implements View.OnClickListener, DownloadHelper.OnDownloadListener {
    TextView mMessageTV;
    DownloadHelper downloadHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_file);
        mMessageTV = (TextView) findViewById(R.id.tv_message);
        findViewById(R.id.btn_start).setOnClickListener(this);
        findViewById(R.id.btn_stop).setOnClickListener(this);
        downloadHelper = new DownloadHelper(this);
        downloadHelper.setDownloadListener(this);
    }

    int i = 0;

    @Override
    public void onClick(View v) {
        if (Lib_NetworkStateReceiver._Current_NetWork_Status != Lib_Util_Network.NetType.Wifi) {
            _showToast("请在wifi状态下测试...");
            return;
        }
        String key = "key" + (i++ % 3);
        switch (v.getId()) {
            case R.id.btn_start:
                downloadHelper.download(new DownloadBean(key, key, ""));
                break;
            case R.id.btn_stop:
                downloadHelper.cancelDownload(key);
                break;
        }
    }

    @Override
    public void onStart(String key) {
        mMessageTV.setText("开始");
    }

    @Override
    public void onComplete(String key) {
        mMessageTV.setText("完成");
    }

    @Override
    public void onError(String key, String errorMessage) {
        mMessageTV.setText(errorMessage);
    }

    @Override
    public void onProgress(String key, int progress) {
        mMessageTV.setText(String.format("已下载:%d%%", progress));
    }
}
