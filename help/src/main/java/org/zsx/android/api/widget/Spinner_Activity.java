package org.zsx.android.api.widget;

import org.zsx.android.api.R;
import org.zsx.android.base._BaseActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Spinner_Activity extends _BaseActivity implements
		Spinner.OnItemSelectedListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.widget_spinner);
		Spinner mSpinner1 = (Spinner) findViewById(R.id.act_widget_current_view);
		mSpinner1.setOnItemSelectedListener(this);
		Spinner mSpinner2 = (Spinner) findViewById(R.id.global_spinner);
		mSpinner2.setOnItemSelectedListener(this);
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		if (arg1 instanceof TextView) {
			TextView t = (TextView) arg1;
			Toast.makeText(this, t.getText().toString() + arg2,
					Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(this, arg2, Toast.LENGTH_SHORT).show();
		}

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
	}
}
