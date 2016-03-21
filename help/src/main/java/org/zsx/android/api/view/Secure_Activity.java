package org.zsx.android.api.view;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import org.zsx.android.api.R;
import org.zsx.android.api._BaseActivity;

public class Secure_Activity extends _BaseActivity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_secure);
		findViewById(R.id.global_btn1).setOnClickListener(this);
		findViewById(R.id.global_btn2).setOnClickListener(this);
		findViewById(R.id.global_btn3).setOnClickListener(this);
		findViewById(R.id.global_btn4).setOnClickListener(this);
		findViewById(R.id.global_btn3).setOnTouchListener(touch);
		findViewById(R.id.global_btn4).setOnTouchListener(touch);
	}

	View.OnTouchListener touch = new View.OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			/**
			 * 触摸过滤机制,在toast,dialog,popupwindow等窗口相关的组件出现时屏蔽触摸事件
			 */
			if ((event.getFlags() & MotionEvent.FLAG_WINDOW_IS_OBSCURED) != 0) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					new AlertDialog.Builder(Secure_Activity.this)
							.setMessage(
									"相应了MotionEvent.FLAG_WINDOW_IS_OBSCURED下的onTouch")
							.setNeutralButton("ok", null).show();
				}
				return true;
			}
			return false;
		}
	};

	@Override
	public void onClick(View vv) {
		switch (vv.getId()) {
		case R.id.global_btn1:
			SecureGroupView v = (SecureGroupView) LayoutInflater.from(this)
					.inflate(R.layout.view_secure_groupview, null);
			v.init(Secure_Activity.this);
			Toast t = new Toast(getApplicationContext());
			t.setGravity(Gravity.FILL, 0, 0);
			t.setView(v);
			t.show();
			break;
		case R.id.global_btn2:
		case R.id.global_btn3:
		case R.id.global_btn4:
			new AlertDialog.Builder(this).setMessage("响应了默认点击相应")
					.setNegativeButton("ok", null).create().show();
			break;
		}
	}
}
