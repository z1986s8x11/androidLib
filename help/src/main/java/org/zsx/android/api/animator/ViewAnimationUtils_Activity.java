package org.zsx.android.api.animator;

import android.animation.Animator;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;

import org.zsx.android.api.R;
import org.zsx.android.api._BaseActivity;

/**
 * Author       zhusx
 * Email        327270607@qq.com
 * Created      2016/4/6 16:24
 */
public class ViewAnimationUtils_Activity extends _BaseActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.zsx.R.layout.anim_viewaunimationutil);
        findViewById(R.id.tv_text1).setOnClickListener(this);
        findViewById(R.id.tv_text2).setOnClickListener(this);
    }

    Animator animator1, animator2;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_text1:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    /**
                     *  参数介绍:
                     *  view 操作的视图
                     *  centerX 动画开始的中心点X
                     *  centerY 动画开始的中心点Y
                     *  startRadius 动画开始半径
                     *  startRadius 动画结束半径
                     */
                    animator1 = ViewAnimationUtils.createCircularReveal(
                            v,
                            v.getWidth() / 2,
                            v.getHeight() / 2,
                            v.getWidth(),
                            0);
                    animator1.setInterpolator(new AccelerateDecelerateInterpolator());
                    animator1.setDuration(2000);
                    animator1.start();
                }
                break;
            case R.id.tv_text2:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    animator2 = ViewAnimationUtils.createCircularReveal(
                            v,
                            0,
                            0,
                            0,
                            (float) Math.hypot(v.getWidth(), v.getHeight()));
                    animator2.setInterpolator(new AccelerateInterpolator());
                    animator2.setDuration(2000);
                    animator2.start();
                }
                break;
        }
    }
}
