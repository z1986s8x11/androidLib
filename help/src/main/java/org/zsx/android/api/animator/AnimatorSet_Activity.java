package org.zsx.android.api.animator;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.zsx.android.api.R;
import org.zsx.android.base._BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class AnimatorSet_Activity extends _BaseActivity {
    private ValueAnimator anim1;
    private ValueAnimator anim2;
    private ValueAnimator anim3;
    private ValueAnimator anim4;
    private AnimatorSet mAnimatorSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anim_animator_set);
        ButterKnife.inject(this);
        TextView contentTV = (TextView) findViewById(R.id.tv_text1);
        /** 手动调用TranslationX平移之后 需要注意View位置*/
        anim1 = ObjectAnimator.ofFloat(contentTV, "TranslationX", 0f, 100f, 0f);
        anim1.setDuration(2000);
        anim2 = ObjectAnimator.ofFloat(contentTV, "RotationX", 0f, 360f);
        anim2.setDuration(2000);
        anim3 = ObjectAnimator.ofFloat(contentTV, "ScaleY", 0.5f, 1.0f);
        anim3.setDuration(2000);
        anim4 = ObjectAnimator.ofFloat(contentTV, "Alpha", 0.0f, 1.0f);
        anim4.setDuration(2000);

    }

    @OnClick({R.id.global_btn1, R.id.global_btn2, R.id.global_btn3})
    public void onClick(View v) {
        if (mAnimatorSet != null) {
            if (mAnimatorSet.isRunning()) {
                /**
                 * end()
                 * 	停止动画播放 动画直接到最终状态
                 * 		会回调AnimatorListener.onAnimationEnd(Animator)
                 * cancel()
                 * 	取消动画播放 停留在当前位置
                 * 		回调AnimatorListener.onAnimationCancel(Animator)
                 **/
                mAnimatorSet.cancel();
                mAnimatorSet = null;
            }
        }
        mAnimatorSet = new AnimatorSet();
        switch (v.getId()) {
            case R.id.global_btn1:
                /** 同时播放动画 */
                mAnimatorSet.playTogether(anim1, anim2, anim3, anim4);
                mAnimatorSet.start();
                break;
            case R.id.global_btn2:
                /** 顺序播放动画 */
                mAnimatorSet.playSequentially(anim1, anim2, anim3, anim4);
                mAnimatorSet.start();
                break;
            case R.id.global_btn3:
                /** 播放anim1在播放anim2以前 */
                mAnimatorSet.play(anim1).before(anim2);
                /** 播放anim2同时播放anim3 */
                mAnimatorSet.play(anim2).with(anim3);
                /** 播放anim4 在播放完anim2的时候 */
                mAnimatorSet.play(anim4).after(anim2);
                mAnimatorSet.start();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAnimatorSet != null) {
            if (mAnimatorSet.isRunning()) {
                mAnimatorSet.cancel();
            }
        }
    }
}
