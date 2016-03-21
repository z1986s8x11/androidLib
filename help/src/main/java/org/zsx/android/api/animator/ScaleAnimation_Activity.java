package org.zsx.android.api.animator;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.tools.Lib_Class_ShowCodeUtil;

import org.zsx.android.api.R;
import org.zsx.android.api._BaseActivity;

public class ScaleAnimation_Activity extends _BaseActivity implements
        OnClickListener {
    private ImageView imageTV1;
    private ImageView imageTV2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bitmap_scale_animation);
        findViewById(R.id.global_btn1).setOnClickListener(this);
        findViewById(R.id.global_btn2).setOnClickListener(this);
        imageTV1 = (ImageView) findViewById(R.id.global_imageview1);
        imageTV2 = (ImageView) findViewById(R.id.global_imageview2);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.global_btn1:
                imageTV1.startAnimation(AnimationUtils.loadAnimation(this,
                        R.anim.anim_scale));
                break;
            case R.id.global_btn2:
                /*
                 *( float fromX, float toX, float fromY, float toY, float pivotX, float pivotY)
                 *
                 * float fromX 动画起始时 X坐标上的伸缩尺寸
                 * float toX 动画结束时 X坐标上的伸缩尺寸
                 * float fromY 动画起始时Y坐标上的伸缩尺寸
                 * float toY 动画结束时Y坐标上的伸缩尺寸
                 * float pivotXValue 动画相对于物件的X坐标的开始位置
                 * float pivotYValue 动画相对于物件的Y坐标的开始位置
                 */
                ScaleAnimation animation = new ScaleAnimation(1f, 0f, 1f, 1f, 0.5f, 0.5f);
                //动画时长1000毫秒
                animation.setDuration(1000);
                //动画结束时停留在动画结束的时刻
                animation.setFillAfter(true);
                //重复3次动画
                animation.setRepeatCount(3);
                imageTV2.startAnimation(animation);
                break;
        }

    }
}
