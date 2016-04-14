package org.zsx.android.api.animator;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import org.zsx.android.api.R;
import org.zsx.android.base._BaseActivity;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ViewPropertyAnimator_Activity extends _BaseActivity implements OnClickListener {
	private ObjectAnimator objectAnimator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.anim_viewproperty_animation);
		ImageView imageView = (ImageView) findViewById(R.id.iv_image);
		findViewById(R.id.btn_onClick).setOnClickListener(this);
		/**
		 * 如果需要对一个View的多个属性进行动画可以用ViewPropertyAnimator类，该类对多属性动画进行了优化，
		 * 会合并一些invalidate()来减少刷新视图，该类在3.1中引入。
		 * */
		PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("x", 50f);
		PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("y", 100f);
		objectAnimator = ObjectAnimator.ofPropertyValuesHolder(imageView, pvhX, pvhY);
	}

	@Override
	public void onClick(View v) {
		objectAnimator.start();
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(objectAnimator.isRunning()){
			objectAnimator.cancel();
		}
	}
}
