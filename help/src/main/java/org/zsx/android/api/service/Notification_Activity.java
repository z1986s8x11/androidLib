package org.zsx.android.api.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Button;

import org.zsx.android.api.MainActivity;
import org.zsx.android.api.R;
import org.zsx.android.api._BaseActivity;

public class Notification_Activity extends _BaseActivity implements Button.OnClickListener {
    private NotificationManager mNotificationManager;
    private final int ZSX_ID = 0x811;
    private final int ZSX_ID2 = 0x822;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_notification);
        findViewById(R.id.global_btn1).setOnClickListener(this);
        findViewById(R.id.global_btn2).setOnClickListener(this);
        findViewById(R.id.global_btn3).setOnClickListener(this);
        findViewById(R.id.global_btn4).setOnClickListener(this);
        mNotificationManager = (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.global_btn1:
                // 发送通知
                mNotificationManager.notify(ZSX_ID, notifyNotification());
                break;
            case R.id.global_btn2:
                mNotificationManager.cancel(ZSX_ID);
                break;
            case R.id.global_btn3:
                // 发送通知
                mNotificationManager.notify(ZSX_ID2, notifyNotification2());
                break;
            case R.id.global_btn4:
                mNotificationManager.cancel(ZSX_ID2);
                break;
            default:
                break;
        }
    }

    private Notification notifyNotification() {
        Notification mNotification = new Notification();
        // 为Notification 设置图标,该图标显示在状态栏
        mNotification.icon = android.R.drawable.ic_menu_camera;
        // 为notification 设置文本内容,该文本会显示在状态栏
        mNotification.tickerText = "启动其他activity的通知";
        // 为notification设置发送时间
        mNotification.when = System.currentTimeMillis();
        // 点击后取消
        mNotification.flags = Notification.FLAG_AUTO_CANCEL;
        // 为notification设置声音
        // mNotification.defaults = Notification.DEFAULT_SOUND;
        // 为notification设置默认的声音,默认振动,默认闪光灯
        mNotification.defaults = Notification.DEFAULT_ALL;
        // 设置声音可自定义notification.sound=Uri.parse("file:///sddcard/a.mp3");
        // 设置振动 可自定义notification.vibreate=new long[]{0,50,100,150};
        // 设置闪光灯颜色 notification.ledRGB=0xffff0000
        // 设置闪光灯多少毫秒后熄灭notification.ledOffMS=800
        // 设置闪光灯多少毫秒后开启 notification.ledOnMS=0xffff0000
        Intent in = new Intent(this, MainActivity.class);
        in.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent mPendingIntent = PendingIntent.getActivity(this, 0, in, 0);
        mNotification.setLatestEventInfo(this, "普通通知(标题)", "点击查看(内容)", mPendingIntent);
        return mNotification;
    }

    private Notification notifyNotification2() {
        PendingIntent intent = PendingIntent.getActivity(this, 11, new Intent(), PendingIntent.FLAG_NO_CREATE);
        Notification n = new NotificationCompat.Builder(this)
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
        return n;
    }
}
