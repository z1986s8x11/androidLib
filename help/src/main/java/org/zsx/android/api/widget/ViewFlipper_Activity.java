package org.zsx.android.api.widget;

import org.zsx.android.api.R;
import org.zsx.android.api._BaseActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ViewFlipper;

public class ViewFlipper_Activity extends _BaseActivity implements
		Button.OnClickListener {

	ViewFlipper flipper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.widget_viewflipper);
		Button up = (Button) findViewById(R.id.global_btn1);
		up.setOnClickListener(this);
		Button down = (Button) findViewById(R.id.global_btn2);
		down.setOnClickListener(this);
		flipper = (ViewFlipper) findViewById(R.id.act_widget_current_view);
		/* 也可以代码添加View */
		//	flipper.addView(View);
		/*设置间隔时间*/
		//	flipper.setFlipInterval(int);
		/*开始循环*/
		//	flipper.startFlipping();
		/*停止循环*/
		//	flipper.stopFlipping();
	}	

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.global_btn1:
			flipper.setInAnimation(this, android.R.anim.fade_in);
			flipper.setOutAnimation(this, android.R.anim.fade_out);
			flipper.showPrevious();
			break;
		case R.id.global_btn2:
			flipper.setInAnimation(this, android.R.anim.slide_in_left);
			flipper.setOutAnimation(this, android.R.anim.slide_out_right);
			flipper.showNext();
			break;

		default:
			break;
		}
	}

}
