package com.zsx.util;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

/**
 * Author       zhusx
 * Email        327270607@qq.com
 * Created      2016/3/25 16:54
 */
public class _AnimUtil {

    /**
     * 初始化顶部View Title 动画
     */
    public static void initVisibilityAnim(int gravity, View animView) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            if (animView.getParent() != null && animView.getParent() instanceof ViewGroup) {
                android.animation.LayoutTransition mLayoutTransition = new android.animation.LayoutTransition();
                android.animation.ValueAnimator animVisible = null;
                android.animation.ValueAnimator animGone = null;
                switch (gravity) {
                    case Gravity.TOP:
                        Lib_Util_Widget.measureView(animView);
                        animVisible = android.animation.ObjectAnimator.ofFloat(animView, "translationY", -animView.getMeasuredHeight(), 0);
                        animGone = android.animation.ObjectAnimator.ofFloat(animView, "translationY", 0, -animView.getMeasuredHeight());
                        break;
                    case Gravity.BOTTOM:
                        Lib_Util_Widget.measureView(animView);
                        animVisible = android.animation.ObjectAnimator.ofFloat(animView, "translationY", animView.getMeasuredHeight(), 0);
                        animGone = android.animation.ObjectAnimator.ofFloat(animView, "translationY", 0, animView.getMeasuredHeight());
                        break;
                    case Gravity.CENTER:
                        animVisible = android.animation.ObjectAnimator.ofFloat(animView, "alpha", 0f, 1f);
                        animGone = android.animation.ObjectAnimator.ofFloat(animView, "alpha", 1f, 0f);
                        break;
                    default:
                        return;
                }
                mLayoutTransition.setAnimator(android.animation.LayoutTransition.APPEARING, animVisible);
                mLayoutTransition.setStagger(android.animation.LayoutTransition.APPEARING, 30);
                mLayoutTransition.setDuration(mLayoutTransition.getDuration(android.animation.LayoutTransition.APPEARING));
                mLayoutTransition.setAnimator(android.animation.LayoutTransition.DISAPPEARING, animGone);
                mLayoutTransition.setStagger(android.animation.LayoutTransition.DISAPPEARING, 30);
                mLayoutTransition.setDuration(mLayoutTransition.getDuration(android.animation.LayoutTransition.DISAPPEARING));
                ((ViewGroup) animView.getParent()).setLayoutTransition(mLayoutTransition);
            }
        }
    }
}
