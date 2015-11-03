package org.zsx.android.api.widget;

import java.util.Calendar;

import org.zsx.android.api.R;
import org.zsx.android.api._BaseActivity;

import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.Toast;

public class DatePicker_Activity extends _BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.widget_datepicker);
		DatePicker mDatePicker = (DatePicker) findViewById(R.id.act_widget_current_view);
		Calendar c = Calendar.getInstance();
		mDatePicker.init(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
			@Override
			public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				Toast.makeText(DatePicker_Activity.this, "现在时间:" + year + "年 " + (monthOfYear + 1) + "月 " + dayOfMonth + "日", Toast.LENGTH_LONG).show();
			}
		});
	}
}
