package org.zsx.android.api.animator;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import org.zsx.android.api.R;
import org.zsx.android.base._BaseActivity;

public class AlphaAnimation_Activity extends _BaseActivity implements
        OnClickListener {
    private ImageView imageIV1;
    private ImageView imageIV2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bitmap_alpha_animation);
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
                        R.anim.anim_alpha));
                break;
            case R.id.global_btn2:
                //透明度从1~0
                AlphaAnimation animation = new AlphaAnimation(1, 0);
                //动画时长1000毫秒
                animation.setDuration(1000);
                //动画结束时停留在动画结束的时刻
                animation.setFillAfter(true);
                //动画延迟400毫秒开始
                animation.setStartOffset(400);
                //重复3次动画
                animation.setRepeatCount(3);
                imageIV2.startAnimation(animation);
                break;
        }
    }
}
