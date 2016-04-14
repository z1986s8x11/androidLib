package org.zsx.android.api.widget;

import org.zsx.android.api.R;
import org.zsx.android.base._BaseActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridLayout;

@SuppressLint("NewApi")
public class GridLayout_Activity extends _BaseActivity implements
		OnClickListener {
	private GridLayout mGridLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.widget_gridlayout);
		mGridLayout = (GridLayout) findViewById(R.id.act_widget_current_view);
		findViewById(R.id.global_btn1).setOnClickListener(this);
	}
	private int num = 0;
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.global_btn1:
			Button btn = new Button(this);
			btn.setText(String.valueOf(num++));
			btn.setMinEms(3);
			btn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					mGridLayout.removeView(v);
				}
			});
			mGridLayout.addView(btn,Math.min(1, mGridLayout.getChildCount()));
			break;
		}
	}
}
