package zsx.com.test.ui.download;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zsx.download.Lib_DownloadInterface;
import com.zsx.download.Lib_DownloadReceiver;
import com.zsx.download.Lib_DownloadService;
import com.zsx.network.Lib_NetworkStateReceiver;
import com.zsx.util.Lib_Util_Network;

import java.io.File;

import zsx.com.test.R;
import zsx.com.test.base._BaseActivity;

/**
 * Created by zhusx on 2015/8/6.
 */
public class DownloadActivity extends _BaseActivity implements View.OnClickListener, Lib_DownloadReceiver.OnDownloadListener {
    TextView mMessageTV;
    Lib_DownloadReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_file);
        mMessageTV = (TextView) findViewById(R.id.tv_message);
        findViewById(R.id.btn_start).setOnClickListener(this);
        findViewById(R.id.btn_stop).setOnClickListener(this);

        receiver = new Lib_DownloadReceiver(this);
        IntentFilter filter = new IntentFilter(Lib_DownloadReceiver._DOWNLOAD_ACTION);
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    @Override
    public void onClick(View v) {
        if (Lib_NetworkStateReceiver._Current_NetWork_Status != Lib_Util_Network.NetType.Wifi) {
            _showToast("请在wifi状态下测试...");
            return;
        }
        switch (v.getId()) {
            case R.id.btn_start:
                Lib_DownloadService._startService(this, "22", "www.baidu.com", new File(getExternalCacheDir(), "ff.html").getPath());
                break;
            case R.id.btn_stop:
                stopService(new Intent(this, Lib_DownloadService.class));
                break;
        }
    }

    @Override
    public void onDownloadStart(String key, Lib_DownloadInterface data) {
        mMessageTV.setText("开始下载");
    }

    @Override
    public void onDownloadLoad(String key, Lib_DownloadInterface data, int progress) {
        mMessageTV.setText(String.format("下载进度%s%%100", progress));
    }

    @Override
    public void onDownloadComplete(String key, Lib_DownloadInterface data) {
        mMessageTV.setText("下载完成,存放路径:" + data.getSavePath());
    }

    @Override
    public void onDownloadError(String key, String message, Lib_DownloadInterface data) {
        mMessageTV.setText("下载失败");
    }
}
