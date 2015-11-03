package org.zsx.android.api.widget;

import org.zsx.android.api.R;
import org.zsx.android.api._BaseActivity;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;

public class Switch_Activity extends _BaseActivity implements
		OnCheckedChangeListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.widget_switch);
		Switch s = (Switch) findViewById(R.id.act_widget_current_view);
		s.setOnCheckedChangeListener(this);
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		Toast.makeText(this, isChecked ? "true" : "false", Toast.LENGTH_SHORT)
				.show();
	}
}
