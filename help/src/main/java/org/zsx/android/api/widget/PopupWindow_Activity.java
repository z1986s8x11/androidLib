package org.zsx.android.api.widget;

import org.zsx.android.api.R;
import org.zsx.android.api._BaseActivity;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

public class PopupWindow_Activity extends _BaseActivity implements Button.OnClickListener {
	Button show;
	PopupWindow mPopupWindow;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.widget_popupwindow);
		show = (Button) findViewById(R.id.global_btn1);
		show.setOnClickListener(this);
		// 第一个参数 需要现实的(View,宽,高,是否获得焦点)
		// 必须设置 宽度 和高度..不然无显示效果
		mPopupWindow = new PopupWindow(getZSXView(), 100, 100, true);
		// 返回键可以关闭PopupWindow
		mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.global_btn1:
			if (mPopupWindow.isShowing()) {
				mPopupWindow.dismiss();
			} else {
				mPopupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
			}
			break;

		default:
			break;
		}
	}

	public View getZSXView() {
		ImageView iv = new ImageView(this);
		iv.setOnClickListener(new ImageView.OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(PopupWindow_Activity.this, "点击了图片", Toast.LENGTH_SHORT).show();
			}
		});
		iv.setBackgroundResource(R.drawable.ic_launcher);
		return iv;
	}
}
