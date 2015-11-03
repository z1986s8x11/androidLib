package org.zsx.android.api.widget;

import org.zsx.android.api.R;
import org.zsx.android.api._BaseActivity;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

public class ToggleButton_Activity extends _BaseActivity implements ToggleButton.OnCheckedChangeListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.widget_togglebutton);
		ToggleButton mToggleButton = (ToggleButton) findViewById(R.id.act_widget_current_view);
		mToggleButton.setOnCheckedChangeListener(this);
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isChecked) {
			Toast.makeText(this, "打开", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(this, "关闭", Toast.LENGTH_SHORT).show();
		}
	}
}
