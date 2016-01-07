package zsx.com.test.ui.download;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
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
        String key = "key";
        switch (v.getId()) {
            case R.id.btn_start:
                if (map.containsKey(key)) {
                    return;
                }
                DownloadRunnable runnable = new DownloadRunnable(key, "http://s.qw.cc/app/app1112.apk", new File(getExternalCacheDir(), "quwang.apk").getPath());
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
                if (map.containsKey(key)) {
                    DownloadRunnable run = map.get(key);
                    run.isCancel = true;
                    map.remove(key);
                    list.remove(run);
                }
                break;
        }
    }

    public void onDownloadStart(final String key) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mMessageTV.setText("start" + key);
                PendingIntent intent = PendingIntent.getActivity(DownloadActivity.this, 11, new Intent(), PendingIntent.FLAG_NO_CREATE);
                Notification n = new NotificationCompat.Builder(DownloadActivity.this)
                        .setContentTitle("ContentTitle")//设置通知栏标题
                        .setContentText("ContentText")//设置通知栏显示内容
                        .setContentIntent(intent)//设置通知栏点击意图
                        .setTicker("ticker")//通知首次出现在通知栏，带上升动画效果的
                        .setNumber(4)//设置通知集合的数量
                        .setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
                        .setOngoing(false)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                        .setDefaults(NotificationCompat.DEFAULT_VIBRATE)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
                        .setSmallIcon(R.drawable.lib_base_iv_listview_arrow)//设置通知小ICON
                        .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                        .setOngoing(true)//设置为ture，表示它为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                                /**
                                 MAX	重要而紧急的通知，通知用户这个事件是时间上紧迫的或者需要立即处理的。
                                 HIGH	高优先级用于重要的通信内容，例如短消息或者聊天，这些都是对用户来说比较有兴趣的。
                                 DEFAULT	默认优先级用于没有特殊优先级分类的通知。
                                 LOW	低优先级可以通知用户但又不是很紧急的事件。
                                 MIN	用于后台消息 (例如天气或者位置信息)。最低优先级通知将只在状态栏显示图标，只有用户下拉通知抽屉才能看到内容。
                                 * */
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT) //设置该通知优先级
                        .setProgress(100, 1, false)  //max:进度条最大数值  、progress:当前进度、indeterminate:表示进度是否不确定，true为不确定
                        .setLights(0xff0000ff, 300, 0)//setLights(intledARGB ,intledOnMS ,intledOffMS)  描述：其中ledARGB 表示灯光颜色、 ledOnMS 亮持续时间、ledOffMS 暗的时间。
                        .setVibrate(new long[]{0, 300, 500, 700})//延迟0ms，然后振动300ms，在延迟500ms，接着在振动700ms
                        .build();
                /**
                 Notification.FLAG_SHOW_LIGHTS              //三色灯提醒，在使用三色灯提醒时候必须加该标志符
                 Notification.FLAG_ONGOING_EVENT          //发起正在运行事件（活动中）
                 Notification.FLAG_INSISTENT    （取消或者打开）
                 Notification.FLAG_ONLY_ALERT_ONCE
                 Notification.FLAG_AUTO_CANCEL      //用户单击通知后自动消失
                 Notification.FLAG_NO_CLEAR          //只有全部清除时，Notification才会清除 ，不清楚该通知(QQ的通知无法清除，就是用的这个)
                 Notification.FLAG_FOREGROUND_SERVICE    //表示正在运行的服务
                 */
                n.flags = Notification.FLAG_AUTO_CANCEL;
                NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                mNotificationManager.notify(11, n);
            }
        });
    }

    public void onDownloadProgress(final String key, final int progress) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mMessageTV.setText("loading " + progress + "%" + key);
            }
        });
    }

    public void onDownloadComplete(final String key, String path) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mMessageTV.setText("Download ok" + key);
            }
        });
    }

    public void onDownloadError(final String key, String errorMessage) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mMessageTV.setText("Download error" + key + System.err);
            }
        });
    }

    public void onDownloadCancel(final String key) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mMessageTV.setText("Download cancel");
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
                        LogUtil.e(this, String.valueOf(progress + ":" + currentSize + ":" + totalSize));
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
                    onDownloadCancel(key);
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
