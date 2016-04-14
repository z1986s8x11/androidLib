package org.zsx.android.api.activity;

import android.os.Bundle;
import android.widget.TextView;

import org.zsx.android.api.R;
import org.zsx.android.base._BaseActivity;

public class Activity_Activity_TestDialogActivity extends _BaseActivity {
	public static final String _EXTRA_FLAG_KEY = "Activity_Activity_TestActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		int flag = getIntent().getIntExtra(_EXTRA_FLAG_KEY, -1);
		switch (flag) {
		case android.R.style.Theme_Dialog:
			// 代码设置不起作用 (不知道为什么) 在 Mainifest.xml设置
			// setTheme(R.style.Theme_CustomDialog);
			break;
		}
		super.onCreate(savedInstanceState);
		setContentView(R.layout.util_activity_test);
		TextView showCodeTV = (TextView) findViewById(R.id.global_textview1);
		showCodeTV
				.setText("这是一个Activity\n通过Mainifest设置(android:style/Theme.Dialog)");
	}
}
