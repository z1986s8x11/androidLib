package org.zsx.android.api.widget;

import org.zsx.android.api.R;
import org.zsx.android.base._BaseActivity;

import android.os.Bundle;
import android.widget.CheckedTextView;

public class CheckedTextView_Activity extends _BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.widget_checktextview);
		// 不接受点击事件..只接受代码控制的点击事件
		CheckedTextView checkTextView = (CheckedTextView) findViewById(R.id.act_widget_current_view);
		checkTextView.setChecked(true);
		// checkTextView.setCheckMarkDrawable(android.R.drawable.arrow_down_float);
	}
}
