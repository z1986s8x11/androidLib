package org.zsx.android.api.widget;

import org.zsx.android.api.R;
import org.zsx.android.base._BaseActivity;

import android.os.Bundle;
import android.widget.TimePicker;
import android.widget.Toast;

public class TimePicker_Activity extends _BaseActivity implements TimePicker.OnTimeChangedListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.widget_timepicker);
		TimePicker mTimePicker = (TimePicker) findViewById(R.id.act_widget_current_view);
		mTimePicker.setOnTimeChangedListener(this);
	}

	@Override
	public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
		Toast.makeText(this, hourOfDay + "时" + minute + "分", Toast.LENGTH_SHORT).show();
	}
}
