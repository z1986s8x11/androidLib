package zsx.com.test.ui.download;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.zsx.debug.LogUtil;
import com.zsx.exception.Lib_Exception;
import com.zsx.network.Lib_NetworkStateReceiver;
import com.zsx.util.Lib_Util_HttpURLRequest;
import com.zsx.util.Lib_Util_Network;

import org.apache.http.conn.ConnectTimeoutException;

import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

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

    Handler mHandler = new Handler();
    public static ConcurrentHashMap<String, DownloadRunnable> map = new ConcurrentHashMap<String, DownloadRunnable>();
    public LinkedList<DownloadRunnable> list = new LinkedList<>();
    Thread t;
    public int i = 0;

    @Override
    public void onClick(View v) {
        if (Lib_NetworkStateReceiver._Current_NetWork_Status != Lib_Util_Network.NetType.Wifi) {
            _showToast("请在wifi状态下测试...");
            return;
        }

        switch (v.getId()) {
            case R.id.btn_start:
                int aaa = i++ % 2;
                String key = "key" + aaa;
                if (map.containsKey(key)) {
                    return;
                }
                DownloadRunnable runnable = new DownloadRunnable(key, "http://s.qw.cc/app/app1112.apk", new File(getExternalCacheDir(), "quwang.apk" + aaa).getPath());
                map.put(key, runnable);
                list.add(runnable);
                if (t == null) {
                    t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while (!list.isEmpty()) {
                                DownloadRunnable runnable = list.pop();
                                runnable.run();
                                try {
                                    Thread.sleep(5);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            t = null;
                        }
                    });
                    t.start();
                }
                break;
            case R.id.btn_stop:
                if (map.containsKey("key1")) {
                    DownloadRunnable run = map.get("key1");
                    run.isCancel = true;
                    map.remove("key1");
                    list.remove(run);
                }
                break;
        }
    }

    public void onDownloadStart(String key) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mMessageTV.setText("start");
            }
        });
    }

    public void onDownloadProgress(String key, final int progress) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mMessageTV.setText("loading " + progress + "%");
            }
        });
    }

    public void onDownloadComplete(String key, String path) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mMessageTV.setText("Download ok");
            }
        });
    }

    public void onDownloadError(String key, String errorMessage) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mMessageTV.setText("Download error");
            }
        });
    }

    public class DownloadRunnable implements Runnable {
        private String key;
        private String path;
        private String url;
        private boolean isCancel = false;

        public DownloadRunnable(String key, String url, String path) {
            this.url = url;
            this.key = key;
            this.path = path;
        }


        @Override
        public void run() {
            try {
                onDownloadStart(key);
                Lib_Util_HttpURLRequest.downloadFile(url, path, new Lib_Util_HttpURLRequest.OnProgressListener() {
                    @Override
                    public void onProgress(final int progress, int currentSize, int totalSize) {
                        onDownloadProgress(key, progress);
                    }

                    @Override
                    public boolean isCanceled() {
                        return isCancel;
                    }
                });
                onDownloadComplete(key, path);
            } catch (Lib_Exception e) {
                e.printStackTrace();
                if (e._getErrorCode() != Lib_Exception.ERROR_CODE_CANCEL) {
                    onDownloadError(key, e._getErrorMessage());
                } else {
                    //用户取消
                }
            } catch (ConnectTimeoutException e) {
                if (LogUtil.DEBUG) {
                    LogUtil.w(e);
                }
                onDownloadError(key, "连接超时");
            } catch (SocketTimeoutException e) {
                if (LogUtil.DEBUG) {
                    LogUtil.w(e);
                }
                onDownloadError(key, "请求超时");
            } catch (IOException e) {
                if (LogUtil.DEBUG) {
                    LogUtil.w(e);
                }
                if (e.getMessage().contains("write failed: ENOSPC (No space left on device)")) {
                    onDownloadError(key, "磁盘空间不足");
                } else {
                    onDownloadError(key, "发生未知错误");
                }
            } catch (Exception e) {
                if (LogUtil.DEBUG) {
                    LogUtil.w(e);
                }
                onDownloadError(key, "发生未知错误");
            } finally {
                map.remove(key);
            }
        }
    }
}
