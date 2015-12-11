package zsx.com.test.ui.anim;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.zsx.util.Lib_Util_Widget;

/**
 * Created by Administrator on 2015/12/11.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class Lib_Util_ViewAnimator {
    private FrameLayout rootView;
    private View animView;

    public Lib_Util_ViewAnimator(Activity activity, View v) {
        ViewGroup decor = (ViewGroup) activity.getWindow().getDecorView();
        ViewGroup decorChild = (ViewGroup) decor.getChildAt(0);
        decor.removeView(decorChild);
        rootView = new FrameLayout(activity);
        decor.addView(rootView);
        rootView.addView(decorChild);
        if (v.getMeasuredHeight() == 0 || v.getMeasuredWidth() == 0) {
            Lib_Util_Widget.measureView(v);
        }
        rootView.addView(v, v.getMeasuredWidth(), v.getMeasuredHeight());
        this.animView = v;
    }


    public void startMove(View fromView, View toView) {
        int[] location = new int[2];
        fromView.getLocationOnScreen(location);
        int[] location1 = new int[2];
        toView.getLocationOnScreen(location1);
        animView.setX(fromView.getX());
        animView.setY(fromView.getY());
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
        animView.setX(fromView.getX() + (fromView.getMeasuredWidth()-animView.getMeasuredWidth()) / 2);
        animView.setY(fromView.getY());
        ObjectAnimator.ofFloat(animView, "translationY", location[1], location[1] - top).start();
        ObjectAnimator animator = ObjectAnimator.ofFloat(animView, "alpha", 1, 0);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                animView.setAlpha(1.0f);
                animView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animView.setVisibility(View.GONE);
                animView.setAlpha(1.0f);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                animView.setVisibility(View.GONE);
                animView.setAlpha(1.0f);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        animator.start();
    }
}
