package org.zsx.android.api.widget;

import org.zsx.android.api.R;
import org.zsx.android.api._BaseActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Button_Activity extends _BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.widget_button);
	}

	// 自定义相应点击方法
	public void zOnClick(View view) {
		Toast.makeText(this, "你点击了按钮", Toast.LENGTH_SHORT).show();
	}
}
