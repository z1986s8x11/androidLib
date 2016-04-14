package org.zsx.android.api.device;

import org.zsx.android.api.R;
import org.zsx.android.base._BaseActivity;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Configuration_Activity extends _BaseActivity implements Button.OnClickListener {
	Configuration cfg;
	TextView mTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.device_configuration);
		cfg = getResources().getConfiguration();
		Button b = (Button) findViewById(R.id.global_btn1);
		Button b2 = (Button) findViewById(R.id.global_btn2);
		b.setOnClickListener(this);
		b2.setOnClickListener(this);
		mTextView = (TextView) findViewById(R.id.global_textview1);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.global_btn1:
			String st = null;
			st = cfg.orientation == Configuration.ORIENTATION_LANDSCAPE ? "横向屏幕" : "竖向屏幕";

			st += "\n";
			st += cfg.orientation == Configuration.NAVIGATION_NONAV ? "没有方向控制" : cfg.orientation == Configuration.NAVIGATION_WHEEL ? "滚轮控制方向"
					: cfg.orientation == Configuration.NAVIGATION_DPAD ? "方向键控制方向" : "轨迹球控制方向";
			st += "\n";
			st += cfg.touchscreen == Configuration.TOUCHSCREEN_NOTOUCH ? "无触摸屏" : "接受手指的触摸屏";
			st += "\n";
			st += "mnc:" + cfg.mnc;
			mTextView.setText(st);
			break;
		case R.id.global_btn2:
			// 更改屏幕方向需要在AndroidManifest.xml配置android:configChanges="orientation"和
			// 加入相应权限android.permission.CHANGE_CONFIGURATION
			if (cfg.orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			} else {
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			}
			break;
		default:
			break;
		}
	}

	// Configuration(系统设置)发生改变时候会回调该方法
	// 需在AndroidManifest中设置android:configChanges才有效
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		Toast.makeText(this, "系统设置Configuation发生改变:" + (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE ? "横向屏幕" : "竖向屏幕"), Toast.LENGTH_SHORT)
				.show();
	}
}
