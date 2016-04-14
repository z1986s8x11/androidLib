package org.zsx.android.api.widget;

import org.zsx.android.api.R;
import org.zsx.android.base._BaseActivity;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;

public class CheckBox_Activity extends _BaseActivity implements OnCheckedChangeListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.widget_checkbox);
		CheckBox mCheckBox = (CheckBox) findViewById(R.id.act_widget_current_view);
		mCheckBox.setOnCheckedChangeListener(this);
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isChecked) {
			Toast.makeText(this, "选择", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(this, "取消", Toast.LENGTH_SHORT).show();
		}
	}
}
