package org.zsx.android.api.widget;

import java.util.Calendar;

import org.zsx.android.api.R;
import org.zsx.android.base._BaseActivity;

import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

public class TimePickerDialog_Activity extends _BaseActivity implements Button.OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.widget_timepickerdialog);
		Button show = (Button) findViewById(R.id.global_btn1);
		show.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Calendar c = Calendar.getInstance();
		new TimePickerDialog(this, new OnTimeSetListener() {

			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				Toast.makeText(getBaseContext(), "您设置的时间是:" + hourOfDay + "时" + minute + "分", Toast.LENGTH_SHORT).show();
			}
		},
		// 设置初始时间
				c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE),
				// true 表示采用24小时制
				true).show();
	}
}
