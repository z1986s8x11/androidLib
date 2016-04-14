package org.zsx.android.api.sensor;

import org.zsx.android.api.R;
import org.zsx.android.base._BaseActivity;

import android.app.Service;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class SensorLight_Activity extends _BaseActivity implements SensorEventListener {
	private SensorManager mSensorManager;
	private Sensor mSensor;
	private TextView mSensorChangedTV;
	private TextView mAccuracyChangedTV;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sensor_light);
		mAccuracyChangedTV = (TextView) findViewById(R.id.global_textview1);
		mSensorChangedTV = (TextView) findViewById(R.id.global_textview2);
		mSensorManager = (SensorManager) getSystemService(Service.SENSOR_SERVICE);
		mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
	}

	@Override
	protected void onResume() {
		super.onResume();
		/** SensorManager.SENSOR_DELAY_NORMAL 获取传感器感应值的频率 */
		mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_UI);
	}

	@Override
	protected void onPause() {
		super.onPause();
		mSensorManager.unregisterListener(this, mSensor);
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		/** SensorEvent.values 有3个值 不同的感应器所含有的值不一样 */
		mSensorChangedTV.setText("光线传感器的值:" + event.values[0]);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		mAccuracyChangedTV.setText("传感器名字" + sensor.getName() + "\t\t" + "精度值:" + accuracy);
	}
}
