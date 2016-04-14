package org.zsx.android.api.animator;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.view.animation.CycleInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import org.zsx.android.api.R;
import org.zsx.android.base._BaseActivity;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ValueAnimator_Activity extends _BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anim_value_animator);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            _showToast("必须大于Android 3.0");
            finish();
            return;
        }
        contentTV = (TextView) findViewById(R.id.tv_text1);
        valueAnimator = ValueAnimator.ofFloat(0f, 1f);
        /** 设置持续时间 */
        valueAnimator.setDuration(5000);
        valueAnimator.addUpdateListener(new AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                /** 此方法会回调多次 */
                contentTV.setAlpha((Float) animation.getAnimatedValue());
            }
        });
        /** 动画监听 */
        valueAnimator.addListener(new AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                Toast.makeText(ValueAnimator_Activity.this, "动画开始",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Toast.makeText(ValueAnimator_Activity.this, "动画结束",
                        Toast.LENGTH_SHORT).show();
            }

            /** 当动画被取消时调用，同时会调用onAnimationEnd() */
            @Override
            public void onAnimationCancel(Animator animation) {
                Toast.makeText(ValueAnimator_Activity.this, "动画取消",
                        Toast.LENGTH_SHORT).show();
            }
        });
        /* 反复次数 */
        valueAnimator.setRepeatCount(5);
        valueAnimator.setInterpolator(new CycleInterpolator(3));
        valueAnimator.start();
    }

    private TextView contentTV;

    private ValueAnimator valueAnimator;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (valueAnimator.isRunning()) {
            valueAnimator.cancel();
        }
    }
}
