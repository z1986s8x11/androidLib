package org.zsx.android.api.animator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.FloatEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.zsx.adapter.Lib_BaseAdapter;

import org.zsx.android.api.R;
import org.zsx.android.base._BaseActivity;

import java.util.ArrayList;
import java.util.List;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ObjectAnimator_Activity extends _BaseActivity {
	private ObjectAnimator objectAnimator;
	private TextView contentTV;
	private int mDuration = 4000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.anim_object_animator);
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
			_showToast("必须大于Android 3.0");
			finish();
			return;
		}
		initWidget();
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		final int screenWidth = metrics.widthPixels; // 屏幕宽度
		contentTV.getViewTreeObserver().addOnGlobalLayoutListener(
				new OnGlobalLayoutListener() {

					@SuppressWarnings("deprecation")
					@Override
					public void onGlobalLayout() {
						/**
						 * @param Object
						 * @param 对应param1的Object类型setName方法的name
						 *            如:TextView.setTranslationX(float);
						 * @param 数组
						 * */
						objectAnimator = ObjectAnimator.ofFloat(contentTV,
								"translationX", 0f, screenWidth);
						/**
						 * IntEvaluator：属性的值类型为int； FloatEvaluator：属性的值类型为float
						 * ArgbEvaluator：属性的值类型为十六进制颜色值；
						 * TypeEvaluator：一个接口，可以通过实现该接口自定义Evaluator。
						 * */
						objectAnimator.setEvaluator(new FloatEvaluator());
						objectAnimator.setDuration(mDuration);
						/** 
						 * 重复次数:
						 * 		ValueAnimator.INFINITE 无限次
						 * 		0:	1次
						 * 		1: 	连续2次
						 * 		objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
						 */
						objectAnimator.setRepeatCount(0);
						/** 可以用AnimatorListenerAdapter 来简化AnimatorListener */
						objectAnimator
								.addListener(new AnimatorListenerAdapter() {
									@Override
									public void onAnimationEnd(
											Animator animation) {
									}
								});
						/**
						 * 设置当前动画显示的时间点动画
						 */
						// objectAnimator.setCurrentPlayTime(seekTime);
						objectAnimator.start();
						contentTV.getViewTreeObserver()
								.removeGlobalOnLayoutListener(this);
					}
				});
	}

	private void initWidget() {
		contentTV = (TextView) findViewById(R.id.tv_text1);
		List<String> list = new ArrayList<String>();
		/** 加速，开始时慢中间加速 */
		list.add("AccelerateInterpolator");
		/** 减速，开始时快然后减速 */
		list.add("DecelerateInterpolator");
		/** 先加速后减速，开始结束时慢，中间加速 */
		list.add("AccelerateDecelerateInterolator");
		/** 反向 ，先向相反方向改变一段再加速播放 */
		list.add("AnticipateInterpolator");
		/** 反向加回弹，先向相反方向改变，再加速播放，会超出目的值然后缓慢移动至目的值 */
		list.add("AnticipateOvershootInterpolator");
		/** 跳跃，快到目的值时值会跳跃，如目的值100，后面的值可能依次为85，77，70，80，90，100 */
		list.add("BounceInterpolator");
		/** 循环，动画循环一定次数，值的改变为一正弦函数：Math.sin(2*mCycles* Math.PI * input) */
		list.add("CycleIinterpolator");
		/** 线性，线性均匀改变 */
		list.add("LinearInterpolator");
		/** 回弹，最后超出目的值然后缓慢改变到目的值 */
		list.add("OvershottInterpolator");
		Spinner spinner = (Spinner) findViewById(R.id.spinner1);
		Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
		List<String> list2 = new ArrayList<String>();
		list2.add("不反转");
		list2.add("反转");
		spinner.setAdapter(new CustomAdapter(this, list));
		spinner2.setAdapter(new CustomAdapter(this, list2));
		spinner.setOnItemSelectedListener(list1Listener);
		spinner2.setOnItemSelectedListener(list2Listener);

		SeekBar seekbar = (SeekBar) findViewById(R.id.global_seekbar);
		seekbar.setMax(mDuration);
		seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				if (objectAnimator.isRunning()) {
					objectAnimator.cancel();
				}
			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				objectAnimator.setCurrentPlayTime(progress);
			}
		});
	}

	private OnItemSelectedListener list1Listener = new OnItemSelectedListener() {
		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			if (objectAnimator.isRunning()) {
				objectAnimator.cancel();
			}
			switch (position) {
			case 0:
				/** 加速，开始时慢中间加速 */
				objectAnimator.setInterpolator(new AccelerateInterpolator());
				break;
			case 1:
				/** 减速，开始时快然后减速 */
				objectAnimator.setInterpolator(new DecelerateInterpolator());
				break;
			case 2:
				/** 先加速后减速，开始结束时慢，中间加速 */
				objectAnimator
						.setInterpolator(new AccelerateDecelerateInterpolator());
				break;
			case 3:
				/** 反向 ，先向相反方向改变一段再加速播放 */
				objectAnimator.setInterpolator(new AnticipateInterpolator());
				break;
			case 4:
				/** 反向加回弹，先向相反方向改变，再加速播放，会超出目的值然后缓慢移动至目的值 */
				objectAnimator
						.setInterpolator(new AnticipateOvershootInterpolator());
				break;
			case 5:
				/** 跳跃，快到目的值时值会跳跃，如目的值100，后面的值可能依次为85，77，70，80，90，100 */
				objectAnimator.setInterpolator(new BounceInterpolator());
				break;
			case 6:
				/** 循环，动画循环一定次数，值的改变为一正弦函数：Math.sin(2*mCycles* Math.PI * input) */
				objectAnimator.setInterpolator(new CycleInterpolator(1.0f));
				break;
			case 7:
				/** 线性，线性均匀改变 */
				objectAnimator.setInterpolator(new LinearInterpolator());
				break;
			case 8:
				/** 回弹，最后超出目的值然后缓慢改变到目的值 */
				objectAnimator.setInterpolator(new OvershootInterpolator());
				break;
			}
			objectAnimator.start();
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
		}
	};
	/** 反转点击监听 */
	private OnItemSelectedListener list2Listener = new OnItemSelectedListener() {
		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			if (objectAnimator.isRunning()) {
				objectAnimator.cancel();
			}
			switch (position) {
			case 0:
				/** 不反弹 */
				objectAnimator.setRepeatMode(ValueAnimator.RESTART);
				break;
			case 1:
				/** 反弹 */
				objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
				break;
			}
			objectAnimator.start();
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
		}
	};

	private class CustomAdapter extends Lib_BaseAdapter<String> {
		public CustomAdapter(Context context, List<String> list) {
			super(context, list);
		}

		@Override
		public View getView(LayoutInflater inflater, String bean, int position,
				View convertView, ViewGroup parent) {
			View v = inflater.inflate(
					android.R.layout.simple_dropdown_item_1line, parent, false);
			TextView itemTV = (TextView) v.findViewById(android.R.id.text1);
			itemTV.setText(bean);
			return v;
		}
	}
}
