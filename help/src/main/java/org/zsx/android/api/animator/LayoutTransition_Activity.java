package org.zsx.android.api.animator;

import org.zsx.android.api.R;
import org.zsx.android.api._BaseActivity;

import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class LayoutTransition_Activity extends _BaseActivity implements OnClickListener {
	private TextView contentTV;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.anim_layout_transition);
		findViewById(R.id.global_btn1).setOnClickListener(this);
		findViewById(R.id.global_btn2).setOnClickListener(this);
		findViewById(R.id.global_btn3).setOnClickListener(this);
		LayoutTransition mLayoutTransition = new LayoutTransition();
		contentTV = (TextView) findViewById(R.id.global_textview1);
		ValueAnimator animScaleX = ObjectAnimator.ofFloat(contentTV, "scaleX",1f,3f,1f);
		ValueAnimator animScaleY = ObjectAnimator.ofFloat(contentTV,"scaleY",1f,3f,1f);
		ValueAnimator animTranslation = ObjectAnimator.ofFloat(contentTV,"translationX",0,30,0	);
		ValueAnimator animRotation = ObjectAnimator.ofFloat(contentTV,"rotation",0,360);
		/** APPEARING 当一个元素在其父元素中变为Visible时对这个元素应用动画*/
		setLayoutTransitionType(mLayoutTransition,LayoutTransition.APPEARING, animScaleX);
		/** CHANGE_APPEARING 当一个元素在其父元素中变为Visible时，因系统要重新布局有一些元素需要移动，对这些要移动的元素应用动画*/
		setLayoutTransitionType(mLayoutTransition,LayoutTransition.CHANGE_APPEARING, animScaleY);
		/**	DISAPPEARING 当一个元素在其父元素中变为GONE时对其应用动画*/ 
		setLayoutTransitionType(mLayoutTransition,LayoutTransition.DISAPPEARING, animTranslation);
		/**	CHANGE_DISAPPEARING 当一个元素在其父元素中变为GONE时，因系统要重新布局有一些元素需要移动，这些要移动的元素应用动画.*/
		setLayoutTransitionType(mLayoutTransition,LayoutTransition.CHANGE_DISAPPEARING, animRotation);
		LinearLayout l = (LinearLayout) findViewById(R.id.global_linearlayout1);
		l.setLayoutTransition(mLayoutTransition);		
	}
	
	private void setLayoutTransitionType(LayoutTransition lt,int layoutTransitionType,ValueAnimator anim){
		/**设置显示时执行的动画*/
		lt.setAnimator(layoutTransitionType, anim);
		/** 设置显示动画延迟时间，参数分别为类型与时间 */
		lt.setStagger(layoutTransitionType, 30);
		/**设置动画持续时间 */
		lt.setDuration(lt.getDuration(layoutTransitionType));
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.global_btn1:
			contentTV.setVisibility(View.VISIBLE);
			break;
		case R.id.global_btn2:
			contentTV.setVisibility(View.INVISIBLE);
			break;
		case R.id.global_btn3:
			contentTV.setVisibility(View.GONE);
			break;
		}
	}
}
