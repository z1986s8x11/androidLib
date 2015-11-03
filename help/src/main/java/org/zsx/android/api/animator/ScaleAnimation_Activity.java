package org.zsx.android.api.animator;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.tools.Lib_Class_ShowCodeUtil;

import org.zsx.android.api.R;
import org.zsx.android.api._BaseActivity;

public class ScaleAnimation_Activity extends _BaseActivity implements
		OnClickListener {
	private ImageView imageTV;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bitmap_scale_animation);
		findViewById(R.id.global_btn1).setOnClickListener(this);
		imageTV = (ImageView) findViewById(R.id.global_imageview1);
	}

	@Override
	public void onClick(View v) {
		imageTV.startAnimation(AnimationUtils.loadAnimation(this,
				R.anim.anim_scale));
	}

	@Override
	protected void _showCodeInit(Lib_Class_ShowCodeUtil showCode) {
		showCode.setShowXML(R.layout.bitmap_scale_animation, R.anim.anim_scale);
	}

}
