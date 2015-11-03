package org.zsx.android.api.widget;

import org.zsx.android.api.R;
import org.zsx.android.api._BaseActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Chronometer.OnChronometerTickListener;

public class Chronometer_Activity extends _BaseActivity implements Button.OnClickListener, OnChronometerTickListener {
	private Chronometer mChronometer;
	private Button mStart;
	private Button mStop;
	private Button mReset;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.widget_chronometer);
		mChronometer = (Chronometer) findViewById(R.id.act_widget_current_view);
		mChronometer.setOnChronometerTickListener(this);
		// mChronometer.setFormat("计时:%s");
		mStart = (Button) findViewById(R.id.global_btn1);
		mStop = (Button) findViewById(R.id.global_btn2);
		mReset = (Button) findViewById(R.id.global_btn3);
		mStart.setOnClickListener(this);
		mStop.setOnClickListener(this);
		mReset.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.global_btn1:
			mChronometer.start();
			break;
		case R.id.global_btn2:
			mChronometer.stop();
			break;
		case R.id.global_btn3:
			mChronometer.setBase(SystemClock.elapsedRealtime());
			break;
		default:
			break;
		}
	}

	@Override
	public void onChronometerTick(Chronometer chronometer) {
	}
}
