package org.zsx.android.api.service;

import org.zsx.android.api.R;
import org.zsx.android.base._BaseActivity;

import android.app.AlarmManager;
import android.app.Service;
import android.os.Bundle;

public class AlarmManager_Activity extends _BaseActivity {
	AlarmManager mAlarmManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.service_alarm);
		mAlarmManager = (AlarmManager) getSystemService(Service.ALARM_SERVICE);
	}

}
