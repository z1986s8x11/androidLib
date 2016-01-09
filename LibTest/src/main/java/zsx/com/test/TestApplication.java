package zsx.com.test;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;

import com.zsx.app.ZsxApplicationManager;
import com.zsx.debug.LogUtil;
import com.zsx.util.Lib_Util_File;


/**
 * Created by zhusx on 2015/8/5.
 */
public class TestApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.DEBUG = true;
        ZsxApplicationManager.builder(this).setMonitorNet(true).setUncaughtException(true).build();
        Lib_Util_File.createFileDir(this, "Lib");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showNotification();
            }
        }, 5000);
    }

    public void showNotification() {
        PendingIntent intent = PendingIntent.getActivity(this, 11, new Intent(), PendingIntent.FLAG_NO_CREATE);
        Notification n = new NotificationCompat.Builder(this)
                .setContentTitle("LibTest")//设置通知栏标题
                .setContentText("正在后台运行..")//设置通知栏显示内容
                .setContentIntent(intent)//设置通知栏点击意图
                .setTicker("开启")//通知首次出现在通知栏，带上升动画效果的
                .setAutoCancel(false)//设置这个标志当用户单击面板就可以让通知将自动取消
                .setOngoing(false)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                .setDefaults(NotificationCompat.DEFAULT_SOUND)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
                .setSmallIcon(R.mipmap.ic_launcher)//设置通知小ICON
                .setOngoing(true)//设置为ture，表示它为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                .setProgress(100, 20, false)  //max:进度条最大数值  、progress:当前进度、indeterminate:表示进度是否不确定，true为不确定
                .build();
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotificationManager.notify(11, n);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        if (LogUtil.DEBUG) {
            LogUtil.e(this, "onTerminate");
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (LogUtil.DEBUG) {
            LogUtil.e(this, "onLowMemory");
        }
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (LogUtil.DEBUG) {
            LogUtil.e(this, "onTrimMemory" + level);
        }
    }
}
