package org.zsx.android.api.device;

import org.zsx.android.api.R;
import org.zsx.android.api._BaseActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class SoftInputModes_Activity extends _BaseActivity {
	final int[] mResizeModeValues = new int[] {
			WindowManager.LayoutParams.SOFT_INPUT_ADJUST_UNSPECIFIED,
			WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE,
			WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN,
			WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING, };
	final CharSequence[] mResizeModeLabels = new CharSequence[] {
			"Unspecified", "Resize", "Pan", "Nothing" };
	final String[] showCode = new String[] { "SOFT_INPUT_ADJUST_UNSPECIFIED",
			"SOFT_INPUT_ADJUST_RESIZE", "SOFT_INPUT_ADJUST_PAN",
			"SOFT_INPUT_ADJUST_NOTHING" };
	TextView mShowCodeTV;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.device_softinput);
		mShowCodeTV = (TextView) findViewById(R.id.tv_showCode);
		Spinner spinner = (Spinner) findViewById(R.id.global_spinner);
		ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(
				this, android.R.layout.simple_spinner_item, mResizeModeLabels);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setSelection(0);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				getWindow().setSoftInputMode(mResizeModeValues[position]);
				mShowCodeTV.setText(String
						.format("getWindow().setSoftInputMode(WindowManager.LayoutParams.%s)",
								showCode[position]));
			}

			public void onNothingSelected(AdapterView<?> parent) {
				getWindow().setSoftInputMode(mResizeModeValues[0]);
				mShowCodeTV.setText(String
						.format("getWindow().setSoftInputMode(WindowManager.LayoutParams.%s)",
								showCode[0]));
			}
		});
	}
}
