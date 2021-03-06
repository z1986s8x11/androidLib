package com.zsx.tools;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;
import com.zsx.util._Views;

/**
 * Created by Administrator on 2015/12/11.
 */
public class Lib_AnimatorHelper {
    protected FrameLayout rootView;

    public Lib_AnimatorHelper(Activity activity) {
        ViewGroup decor = (ViewGroup) activity.getWindow().getDecorView();
        ViewGroup decorChild = (ViewGroup) decor.getChildAt(0);
        decor.removeView(decorChild);
        rootView = new FrameLayout(activity);
        decor.addView(rootView);
        rootView.addView(decorChild);
    }

    public AnimView getAnimView(IMakeAnimView makeAnimView) {
        return new AnimView(makeAnimView.makeView(rootView.getContext()));
    }

    public interface IMakeAnimView {
        View makeView(Context context);
    }

    public class AnimView {
        private View animView;

        public AnimView(View v) {
            if (v.getParent() != null) {
                throw new IllegalArgumentException("animView 构造器参数不能有父类  ");
            }
            if (v.getMeasuredHeight() == 0 || v.getMeasuredWidth() == 0) {
                _Views.measureView(v);
            }
            rootView.addView(v, v.getMeasuredWidth(), v.getMeasuredHeight());
            this.animView = v;
        }

        public void startMove(View fromView, View toView) {
            int[] location = new int[2];
            fromView.getLocationOnScreen(location);
            int[] location1 = new int[2];
            toView.getLocationOnScreen(location1);
            ViewHelper.setX(animView, ViewHelper.getX(fromView));
            ViewHelper.setY(animView, ViewHelper.getY(fromView));
            ObjectAnimator.ofFloat(animView, "translationY", location[1], location1[1]).start();
            ObjectAnimator animator = ObjectAnimator.ofFloat(animView, "translationX", fromView.getLeft(), toView.getLeft());
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    animView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    animView.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    animView.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
            animator.start();
        }

        public void startTop(View fromView, int top) {
            int[] location = new int[2];
            fromView.getLocationOnScreen(location);
            ViewHelper.setX(animView, ViewHelper.getX(fromView) + (fromView.getMeasuredWidth() - animView.getMeasuredWidth()) / 2);
            ViewHelper.setY(animView, ViewHelper.getY(fromView));
            ObjectAnimator.ofFloat(animView, "translationY", location[1], location[1] - top).start();
            ObjectAnimator animator = ObjectAnimator.ofFloat(animView, "alpha", 1, 0);
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    ViewHelper.setAlpha(animView, 1.0f);
                    animView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    animView.setVisibility(View.GONE);
                    ViewHelper.setAlpha(animView, 1.0f);
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    animView.setVisibility(View.GONE);
                    ViewHelper.setAlpha(animView, 1.0f);
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
            animator.start();
        }
    }
}
