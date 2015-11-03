package org.zsx.android.api.animator;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.tools.Lib_Class_ShowCodeUtil;

import org.zsx.android.api.R;
import org.zsx.android.api._BaseActivity;

public class TranslateAnimation_Activity extends _BaseActivity implements
		OnClickListener {
	private ImageView imageIV;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bitmap_translate_animation);
		findViewById(R.id.global_btn1).setOnClickListener(this);
		imageIV = (ImageView) findViewById(R.id.global_imageview1);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.global_btn1:
			imageIV.startAnimation(AnimationUtils.loadAnimation(this,
					R.anim.anim_translate));
			break;
		}
	}

	@Override
	protected void _showCodeInit(Lib_Class_ShowCodeUtil showCode) {
		showCode.setShowXML(R.layout.bitmap_translate_animation,
				R.anim.anim_translate);
	}

}
