package org.zsx.android.api.widget;

import java.util.Calendar;

import org.zsx.android.api.R;
import org.zsx.android.api._BaseActivity;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

public class DatePickerDialog_Activity extends _BaseActivity implements Button.OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.widget_datepickerdialog);
		Button showDialog = (Button) findViewById(R.id.global_btn1);
		showDialog.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Calendar c = Calendar.getInstance();
		new DatePickerDialog(this, new OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				Toast.makeText(getBaseContext(), "您设置的时间是:" + year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日", Toast.LENGTH_SHORT).show();
			}
		}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
	}
}
