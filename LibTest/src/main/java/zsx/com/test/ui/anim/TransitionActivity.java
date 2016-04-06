package zsx.com.test.ui.anim;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;

import zsx.com.test.R;
import zsx.com.test.base._BaseActivity;

/**
 * Author       zhusx
 * Email        327270607@qq.com
 * Created      2016/4/6 15:49
 */
public class TransitionActivity extends _BaseActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_anim_transition);
        findViewById(R.id.tv_text1).setOnClickListener(this);
        findViewById(R.id.tv_text2).setOnClickListener(this);
    }

    Animator animator1, animator2;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_text1:
                animator1 = ViewAnimationUtils.createCircularReveal(
                        v,
                        v.getWidth() / 2,
                        v.getHeight() / 2,
                        v.getWidth(),
                        0);
                animator1.setInterpolator(new AccelerateDecelerateInterpolator());
                animator1.setDuration(2000);
                animator1.start();
                break;
            case R.id.tv_text2:
                animator2 = ViewAnimationUtils.createCircularReveal(
                        v,
                        0,
                        0,
                        0,
                        (float) Math.hypot(v.getWidth(), v.getHeight()));
                animator2.setInterpolator(new AccelerateInterpolator());
                animator2.setDuration(2000);
                animator2.start();
                break;
        }

    }
}
