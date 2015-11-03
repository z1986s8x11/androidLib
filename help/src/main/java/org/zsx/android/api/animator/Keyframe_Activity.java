package org.zsx.android.api.animator;

import org.zsx.android.api.R;
import org.zsx.android.api._BaseActivity;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class Keyframe_Activity extends _BaseActivity implements OnClickListener {
	private ObjectAnimator rotationAnim;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.anim_keyframe_animator);
		findViewById(R.id.btn_onClick).setOnClickListener(this);
		TextView contextTV = (TextView) findViewById(R.id.tv_text1);
		/** 第一个参数为时间百分比 第二个参数是时间百分比对应的时间属性值 */
		Keyframe kf0 = Keyframe.ofInt(0, 400);
		Keyframe kf1 = Keyframe.ofInt(0.25f, 200);
		Keyframe kf2 = Keyframe.ofInt(0.5f, 400);
		Keyframe kf4 = Keyframe.ofInt(0.75f, 100);
		Keyframe kf3 = Keyframe.ofInt(1f, 500);
		PropertyValuesHolder pvhRotation = PropertyValuesHolder.ofKeyframe("width", kf0, kf1, kf2, kf4, kf3);
		rotationAnim = ObjectAnimator.ofPropertyValuesHolder(contextTV, pvhRotation);
		rotationAnim.setDuration(2000);
		
	}

	@Override
	public void onClick(View v) {
		rotationAnim.cancel();
		rotationAnim.start();
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(rotationAnim.isRunning()){
			rotationAnim.cancel();
		}
	}
}
