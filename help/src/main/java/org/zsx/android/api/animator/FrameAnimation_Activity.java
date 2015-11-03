package org.zsx.android.api.animator;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.tools.Lib_Class_ShowCodeUtil;

import org.zsx.android.api.R;
import org.zsx.android.api._BaseActivity;

public class FrameAnimation_Activity extends _BaseActivity implements
		OnClickListener {
	private AnimationDrawable anim;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.anim_frame_animation);
		findViewById(R.id.btn_onClick).setOnClickListener(this);
		ImageView imageView = (ImageView) findViewById(R.id.iv_image);
		imageView.setBackgroundResource(R.drawable.anim_frame_list);
		anim = (AnimationDrawable) imageView.getBackground();
		// 不能在onCreate开始动画
		// AnimationDrawable还没有完全跟Window相关联，如果想要界面显示时就开始动画的话，可以在onWindowFoucsChanged()中调用start()
	}

	/**
	 * 如果想要界面显示时就开始动画的话，可以在onWindowFoucsChanged()中调用start()
	 */
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if (anim != null) {
			anim.start();
		}
	}

	@Override
	public void onClick(View v) {
		if (anim.isRunning()) {
			anim.stop();
		}
		anim.start();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (anim.isRunning()) {
			anim.stop();
		}
	}

	protected void __showCodeInit(Lib_Class_ShowCodeUtil showCode) {
		showCode.setShowXML(R.layout.anim_frame_animation,
				R.drawable.anim_frame_list);
	}

}
