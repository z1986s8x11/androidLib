package org.zsx.android.api.animator;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import org.zsx.android.api.R;
import org.zsx.android.api._BaseActivity;

public class TranslateAnimation_Activity extends _BaseActivity implements
        OnClickListener {
    private ImageView imageIV1;
    private ImageView imageIV2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bitmap_translate_animation);
        findViewById(R.id.global_btn1).setOnClickListener(this);
        findViewById(R.id.global_btn2).setOnClickListener(this);
        imageIV1 = (ImageView) findViewById(R.id.global_imageview1);
        imageIV2 = (ImageView) findViewById(R.id.global_imageview2);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.global_btn1:
                imageIV1.startAnimation(AnimationUtils.loadAnimation(this,
                        R.anim.anim_translate));
                break;
            case R.id.global_btn2:
                /*
                 * TranslateAnimation(float fromXDelta, float toXDelta, float fromYDelta, float toYDelta)
                 *
                 * float fromXDelta 动画开始X轴上的位置
                 * float toXDelta 动画结束在X轴上的位置
                 * float fromYDelta 动画开始Y轴上的位置
                 * float toYDelta 动画结束在Y轴上的位置
                 */
                TranslateAnimation animation = new TranslateAnimation(-500f, 500f, -200, 200);
                animation.setDuration(1500);
                animation.setFillAfter(false);
                imageIV2.startAnimation(animation);
                break;
        }
    }
}
