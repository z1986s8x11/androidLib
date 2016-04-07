package org.zsx.android.api.animator;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.Explode;
import android.view.Window;

import org.zsx.android.api.R;
import org.zsx.android.api._BaseActivity;

/**
 * Author       zhusx
 * Email        327270607@qq.com
 * Created      2016/4/7 10:56
 */
public class TransitionSet_Activity extends _BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 允许使用transitions
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            // 设置一个exit transition
            getWindow().setExitTransition(new Explode());
            //普通transition的进入效果
            //Window.setEnterTransition()：
            //普通transition的退出效果
            //Window.setExitTransition()：
            //共享元素transition的进入效果
            //Window.setSharedElementEnterTransition()：
            //共享元素transition的退出效果
            //Window.setSharedElementExitTransition()：

        }
        setContentView(R.layout.anim_transitionset);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void startTransitionActivity(Intent intent) {
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void finishTransitionActivity() {
        finishAfterTransition();
    }
}
