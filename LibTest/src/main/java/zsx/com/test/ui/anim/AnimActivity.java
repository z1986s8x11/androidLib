package zsx.com.test.ui.anim;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.zsx.util.Lib_Util_Widget;

import zsx.com.test.R;
import zsx.com.test.base._BaseActivity;

/**
 * Created by Administrator on 2015/12/11.
 */
public class AnimActivity extends _BaseActivity implements View.OnClickListener {
    Button startBtn;
    Button endBtn;
    TextView t;
    FrameLayout rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_anim);
        startBtn = (Button) findViewById(R.id.btn_start);
        endBtn = (Button) findViewById(R.id.btn_end);
        endBtn.setOnClickListener(this);
        t = new TextView(this);
        t.setTextColor(Color.RED);
        t.setGravity(Gravity.CENTER);
        t.setText("我进来了");
        t.setBackgroundResource(R.color.lib_white);
        t.setVisibility(View.GONE);
        addRootView(t);
    }

    int top;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            Rect rect = new Rect();
            getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
            top = rect.top;
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_end:
                Rect rect = new Rect();
                int[] location = new int[2];
                startBtn.getLocationOnScreen(location);
                int[] location1 = new int[2];
                endBtn.getLocationOnScreen(location1);
                getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
                ObjectAnimator.ofFloat(t, "translationY", location[1], location1[1]).start();
                ObjectAnimator animator = ObjectAnimator.ofFloat(t, "translationX", startBtn.getLeft(), endBtn.getLeft());
                animator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        t.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        t.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        t.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                    }
                });
                animator.start();
                break;
        }
    }

    private void addRootView(View v) {
        ViewGroup decor = (ViewGroup) getWindow().getDecorView();
        ViewGroup decorChild = (ViewGroup) decor.getChildAt(0);
        decor.removeView(decorChild);
        rootView = new FrameLayout(this);
        decor.addView(rootView);
        Lib_Util_Widget.measureView(startBtn);
        rootView.addView(decorChild);
        rootView.addView(v, startBtn.getMeasuredWidth(), startBtn.getMeasuredHeight());
    }
}
