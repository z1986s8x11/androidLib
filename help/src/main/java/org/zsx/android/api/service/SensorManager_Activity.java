package org.zsx.android.api.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zsx.android.api.R;
import org.zsx.android.api._BaseActivity;

import android.app.Service;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class SensorManager_Activity extends _BaseActivity {
	private TextView mContentTV;
	private Map<Integer, String> sensorType = new HashMap<Integer, String>();
	private SensorManager mSensorManager;
	{
		sensorType.put(Sensor.TYPE_ACCELEROMETER, "加速传感器");
		sensorType.put(Sensor.TYPE_AMBIENT_TEMPERATURE, "温度传感器");
		sensorType.put(Sensor.TYPE_GRAVITY, "重力传感器");
		sensorType.put(Sensor.TYPE_GYROSCOPE, "陀螺仪");
		sensorType.put(Sensor.TYPE_LIGHT, "光线传感器");
		sensorType.put(Sensor.TYPE_LINEAR_ACCELERATION, "线性加速传感器");
		sensorType.put(Sensor.TYPE_MAGNETIC_FIELD, "磁场感应传感器");
		sensorType.put(Sensor.TYPE_ORIENTATION, "方向传感器");
		sensorType.put(Sensor.TYPE_PRESSURE, "压力传感器");
		sensorType.put(Sensor.TYPE_PROXIMITY, "接近传感器");
		sensorType.put(Sensor.TYPE_RELATIVE_HUMIDITY, "相对湿度传感器");
		sensorType.put(Sensor.TYPE_ROTATION_VECTOR, "旋转传感器");
		sensorType.put(Sensor.TYPE_TEMPERATURE, "温度传感器");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.service_sensormanager);
		mContentTV = (TextView) findViewById(R.id.global_textview1);
		/** TextView 上下滑动 */
		mContentTV.setMovementMethod(ScrollingMovementMethod.getInstance());
		mSensorManager = (SensorManager) getSystemService(Service.SENSOR_SERVICE);
		List<Sensor> sensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
		StringBuilder sb = new StringBuilder();
		for (Sensor sensor : sensors) {
			sb.append("支持的感应器:" + sensorType.get(sensor.getType()));
			sb.append("\n");
			sb.append("name:" + sensor.getName());
			sb.append("\n");
			sb.append("Version:" + sensor.getVersion());
			sb.append("\n");
			sb.append("Resoution:" + sensor.getResolution());
			sb.append("\n");
			sb.append("Max Range:" + sensor.getMaximumRange());
			sb.append("\n");
			sb.append("power:" + sensor.getPower() + " mA");
			sb.append("\n");
			sb.append("\n");
		}
		mContentTV.setText(sb.toString());
	}
}
