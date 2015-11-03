package org.zsx.android.api.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;

import com.zsx.debug.Lib_DebugService;

import org.zsx.android.api.R;
import org.zsx.android.api._BaseActivity;

public class WindowManager_Activity extends _BaseActivity implements
		Button.OnClickListener {
	private WindowManager mWindowManager;
	private ImageView mImageView;
	private Button show, close;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.service_window);
		show = (Button) findViewById(R.id.global_btn1);
		close = (Button) findViewById(R.id.global_btn2);
		findViewById(R.id.global_btn3).setOnClickListener(this);
		findViewById(R.id.global_btn4).setOnClickListener(this);
		show.setOnClickListener(this);
		close.setOnClickListener(this);
		mWindowManager = (WindowManager) getSystemService(Service.WINDOW_SERVICE);
		mImageView = new ImageView(this);
		mImageView.setOnClickListener(new ImageView.OnClickListener() {

			@Override
			public void onClick(View v) {
				mWindowManager.removeView(mImageView);
			}
		});
		mImageView.setImageResource(android.R.drawable.ic_input_add);
		mImageView.setBackgroundColor(Color.BLUE);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.global_btn1:
			LayoutParams params = new LayoutParams();
			// 设置window type
			params.type = LayoutParams.TYPE_APPLICATION;
			// 设置图片格式,效果为背景透明
			params.format = PixelFormat.RGBA_8888;// PixelFormat.TRANSLUCENT
			// 锁定
			// params.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL
			// | LayoutParams.FLAG_NOT_FOCUSABLE
			// | LayoutParams.FLAG_NOT_TOUCHABLE;
			// 调整悬浮窗口 位置
			params.gravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;
			// 以屏幕左上角为原点，设置x、y初始值
			params.x = 0;
			params.y = 0;
			params.token = mImageView.getWindowToken();
			/** WindowManager.LayoutParams.FLAG_SECURE 防止截屏 */
			params.width = LayoutParams.WRAP_CONTENT;
			params.height = LayoutParams.WRAP_CONTENT;
			/**
			 * 悬浮窗权限 <uses-permission
			 * android:name="android.permission.SYSTEM_ALERT_WINDOW" />
			 */
			/* 如果view没有被加入到某个父组件中 */
			if (mImageView.getParent() == null) {
				mWindowManager.addView(mImageView, params);
			}
			break;
		case R.id.global_btn2:
			/* 如果view被加入到某个父组件中 */
			if (mImageView.getParent() != null) {
				mWindowManager.removeView(mImageView);
			}
			break;
		case R.id.global_btn3:
			startService(new Intent(this, Lib_DebugService.class));
			break;
		case R.id.global_btn4:
			stopService(new Intent(this, Lib_DebugService.class));
			break;
		}
	}
}
