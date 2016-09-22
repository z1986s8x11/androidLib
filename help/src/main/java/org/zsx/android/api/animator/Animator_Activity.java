package org.zsx.android.api.animator;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;

import org.zsx.android.api.R;
import org.zsx.android.base._BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 动画
 *
 * @author zsx
 * @date 2015-5-4
 */
public class Animator_Activity extends _BaseActivity {
    @InjectView(R.id.et_input)
    EditText editET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anim_animator);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.global_btn1)
    public void onClick(View v) {
        switch (v.getId()) {
            /** 平移动画 + 动画重复次数 */
            case R.id.global_btn1:
                Animation anim = AnimationUtils.loadAnimation(this, R.anim.anim_anim_translate);
                editET.startAnimation(anim);
                break;
            default:
                break;
        }
    }
}
