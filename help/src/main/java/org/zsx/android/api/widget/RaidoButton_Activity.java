package org.zsx.android.api.widget;

import org.zsx.android.api.R;
import org.zsx.android.base._BaseActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

public class RaidoButton_Activity extends _BaseActivity implements RadioGroup.OnCheckedChangeListener, Button.OnClickListener {
	RadioGroup mRadioGroup;
	int i = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.widget_raidobutton);
		mRadioGroup = (RadioGroup) findViewById(R.id.act_widget_current_view);
		Button b = (Button) findViewById(R.id.global_btn1);
		b.setOnClickListener(this);
		mRadioGroup.setOnCheckedChangeListener(this);
	}

	@Override
	public void onClick(View v) {
		// 会触发2次onCheckedChanged
		mRadioGroup.clearCheck();// 清除所有已经选上的按钮
		/** 代码实现button="@null" */
		// raidoButton.setButtonDrawable(android.R.color.transparent);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		Toast.makeText(this, "setOnCheckedChangeListener触发了:" + i++ + "id:" + checkedId, Toast.LENGTH_SHORT).show();

	}
}
