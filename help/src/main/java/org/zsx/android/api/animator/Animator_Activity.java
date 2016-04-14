package org.zsx.android.api.animator;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;

import org.zsx.android.api.R;
import org.zsx.android.base._BaseActivity;

/**
 * 动画
 * 
 * @author zsx
 * @date 2015-5-4
 */
public class Animator_Activity extends _BaseActivity implements OnClickListener {
	private EditText editET;
	private TextView showCodeTV;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.anim_animator);
		findViewById(R.id.global_btn1).setOnClickListener(this);
		showCodeTV = (TextView) findViewById(R.id.tv_showCode);
		editET = (EditText) findViewById(R.id.et_input);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		/** 平移动画 + 动画重复次数 */
		case R.id.global_btn1:
			Animation anim = AnimationUtils.loadAnimation(this, R.anim.anim_anim_translate);
			editET.startAnimation(anim);
			showCodeTV.setText(R.string.dev_animator_1);
			break;

		default:
			break;
		}
	}
}
