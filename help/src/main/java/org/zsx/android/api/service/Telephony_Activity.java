package org.zsx.android.api.service;

import org.zsx.android.api.R;
import org.zsx.android.base._BaseActivity;

import android.app.Service;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.TextView;

public class Telephony_Activity extends _BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.service_telephony);
		TextView tv = (TextView) findViewById(R.id.act_widget_current_view);
		StringBuilder sb = new StringBuilder();
		TelephonyManager tm = (TelephonyManager) getSystemService(Service.TELEPHONY_SERVICE);
		sb.append("当前电话号码:" + tm.getLine1Number());
		sb.append("\n");

		tv.setText(sb.toString());
	}
}
