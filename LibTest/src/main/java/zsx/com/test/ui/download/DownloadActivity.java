package zsx.com.test.ui.download;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.zsx.exception.Lib_Exception;
import com.zsx.network.Lib_NetworkStateReceiver;
import com.zsx.util.Lib_Util_HttpURLRequest;
import com.zsx.util.Lib_Util_Network;

import java.io.File;
import java.io.IOException;

import zsx.com.test.R;
import zsx.com.test.base._BaseActivity;

/**
 * Created by zhusx on 2015/8/6.
 */
public class DownloadActivity extends _BaseActivity implements View.OnClickListener {
    TextView mMessageTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_file);
        mMessageTV = (TextView) findViewById(R.id.tv_message);
        findViewById(R.id.btn_start).setOnClickListener(this);
        findViewById(R.id.btn_stop).setOnClickListener(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private Thread t;
    Handler mHandler = new Handler();
    boolean isCancle = false;

    @Override
    public void onClick(View v) {
        if (Lib_NetworkStateReceiver._Current_NetWork_Status != Lib_Util_Network.NetType.Wifi) {
            _showToast("请在wifi状态下测试...");
            return;
        }
        switch (v.getId()) {
            case R.id.btn_start:
                if (t == null) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mMessageTV.setText("start");
                        }
                    });
                    t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Lib_Util_HttpURLRequest.downloadFile("http://s.qw.cc/app/app1112.apk", new File(getExternalCacheDir(), "quwang.apk").getPath(), new Lib_Util_HttpURLRequest.OnProgressListener() {
                                    @Override
                                    public void onProgress(final int progress, int currentSize, int totalSize) {
                                        mHandler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                mMessageTV.setText("loading " + progress + "%");
                                            }
                                        });
                                    }

                                    @Override
                                    public boolean isCanceled() {
                                        return isCancle;
                                    }
                                });
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        mMessageTV.setText("Download ok");
                                    }
                                });
                            } catch (Lib_Exception e) {
                                e.printStackTrace();
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        mMessageTV.setText("Download error");
                                    }
                                });
                            } catch (IOException e) {
                                e.printStackTrace();
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        mMessageTV.setText("Download error");
                                    }
                                });
                            }
                            t = null;
                        }
                    });
                    isCancle = false;
                    t.start();
                }
                break;
            case R.id.btn_stop:
                isCancle = true;
                break;
        }
    }
}
