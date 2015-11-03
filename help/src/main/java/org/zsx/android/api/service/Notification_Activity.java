package org.zsx.android.api.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.zsx.android.api.MainActivity;
import org.zsx.android.api.R;
import org.zsx.android.api._BaseActivity;

public class Notification_Activity extends _BaseActivity implements Button.OnClickListener {
	private NotificationManager mNotificationManager;
	private Notification mNotification;
	private PendingIntent mPendingIntent;
	private int ZSX_ID = 0x811;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.service_notification);
		Button show = (Button) findViewById(R.id.global_btn1);
		show.setOnClickListener(this);
		Button cancel = (Button) findViewById(R.id.global_btn2);
		cancel.setOnClickListener(this);
		mNotificationManager = (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);
		mNotification = new Notification();
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
		mPendingIntent = PendingIntent.getActivity(this, 0, in, 0);
		mNotification.setLatestEventInfo(this, "普通通知(标题)", "点击查看(内容)", mPendingIntent);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.global_btn1:
			// 发送通知
			mNotificationManager.notify(ZSX_ID, mNotification);
			break;
		case R.id.global_btn2:
			mNotificationManager.cancel(ZSX_ID);
			break;
		default:
			break;
		}

	}
}
