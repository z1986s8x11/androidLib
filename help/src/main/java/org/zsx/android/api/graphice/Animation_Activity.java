package org.zsx.android.api.graphice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;

import org.zsx.android.api.R;
import org.zsx.android.api._BaseActivity;

public class Animation_Activity extends _BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new AnimationView(this));
	}

	class AnimationView extends View {
		TranslateAnimation anim;
		BitmapDrawable b;
		Transformation t;

		public AnimationView(Context context) {
			super(context);
			t = new Transformation();
			b = (BitmapDrawable) getResources().getDrawable(
					R.drawable.ic_launcher);
			b.setBounds(0, 0, b.getIntrinsicWidth(), b.getIntrinsicHeight());
			anim = new TranslateAnimation(0, 100, 0, 100);
			anim.setDuration(2000);
			// 无限循环动画
			anim.setRepeatCount(-1);
			// 必须 初始化
			anim.initialize(10, 10, 10, 10);
			anim.startNow();
		}

		@Override
		protected void onDraw(Canvas canvas) {
			int sc = canvas.save();
			anim.getTransformation(AnimationUtils.currentAnimationTimeMillis(),
					t);
			canvas.concat(t.getMatrix());
			b.draw(canvas);
			canvas.restoreToCount(sc);
			invalidate();
		}
	}
}
