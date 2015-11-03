package org.zsx.android.api.service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;

import com.tools.Lib_Class_ShowCodeUtil;

import org.zsx.android.api.R;
import org.zsx.android.api._BaseActivity;

public class Service_Activity extends _BaseActivity implements
		Button.OnClickListener {
	private Intent mIntent;
	private ServiceConnection conn = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {

		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.util_service);
		findViewById(R.id.global_btn1).setOnClickListener(this);
		findViewById(R.id.global_btn2).setOnClickListener(this);
		findViewById(R.id.global_btn3).setOnClickListener(this);
		findViewById(R.id.global_btn4).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.global_btn1:
			if (mIntent == null) {
				mIntent = new Intent("com.zhusixiang.android.help.util.Service");
			}
			startService(mIntent);
			break;
		case R.id.global_btn2:
			if (mIntent == null) {
				mIntent = new Intent("com.zhusixiang.android.help.util.Service");
			}
			bindService(mIntent, conn, BIND_AUTO_CREATE);
			break;
		case R.id.global_btn3:
			if (mIntent != null) {
				stopService(mIntent);
			}
			break;
		case R.id.global_btn4:
			if (conn != null && mIntent != null) {
				unbindService(conn);
			}
			break;
		}
	}

	@Override
	protected void _showCodeInit(Lib_Class_ShowCodeUtil showCode){
		showCode.setShowJava(this.getClass(), Service_Service.class);
	}
}
