package org.zsx.android.api.animator;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import org.zsx.android.api.R;
import org.zsx.android.api._BaseActivity;

public class RotateAnimation_Activity extends _BaseActivity implements OnClickListener {
    private ImageView imageIV1;
    private ImageView imageIV2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bitmap_rotate_animation);
        findViewById(R.id.global_btn1).setOnClickListener(this);
        findViewById(R.id.global_btn2).setOnClickListener(this);
        imageIV1 = (ImageView) findViewById(R.id.global_imageview1);
        imageIV2 = (ImageView) findViewById(R.id.global_imageview2);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.global_btn1:
                imageIV1.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_rotate));
                break;
            case R.id.global_btn2:
                /*
                 * (float fromDegrees, float toDegrees, float pivotX, float pivotY)
                 *
                 * float fromDegrees动画开始的度数
                 * float toDegrees 动画结束的度数
                 * float pivotX 动画围绕的旋转的中心x
                 * float pivotY 动画围绕的旋转的中心y
                 */
                RotateAnimation animation = new RotateAnimation(0f, 360f, 0f, 0f);
                animation.setDuration(1000);
                animation.setFillAfter(false);
                animation.setRepeatCount(3);
                imageIV2.startAnimation(animation);
                break;
        }

    }
}
