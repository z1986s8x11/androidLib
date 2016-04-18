package org.zsx.android.api.util;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

import org.zsx.android.base._BaseFragment;

/**
 * Author       zhusx
 * Email        327270607@qq.com
 * Created      2016/4/18 16:58
 */
public class Scroller_Fragment extends _BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        CustomLinearLayout layout = new CustomLinearLayout(inflater.getContext());
        layout.setLayoutParams(new ViewGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        layout.setBackgroundResource(android.R.color.holo_red_light);

        TextView tv = new TextView(inflater.getContext());
        tv.setPadding(50, 50, 50, 50);
        tv.setText("scroller");
        tv.setBackgroundResource(android.R.color.holo_green_light);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(50, 50, 50, 50);

        layout.addView(tv, lp);
        return layout;
    }

    private static class CustomLinearLayout extends LinearLayout implements View.OnClickListener {
        Scroller mScroller;

        public CustomLinearLayout(Context context) {
            super(context);
            setOrientation(LinearLayout.VERTICAL);
            mScroller = new Scroller(context, new OvershootInterpolator(0.75f));
            setOnClickListener(this);
        }

        @Override
        public void computeScroll() {
            super.computeScroll();
            //View 在初始化时 会多次调用
            if (mScroller.computeScrollOffset()) {
                //更新滚动位置
                scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
                if (mScroller.isFinished()) {
                    //回到原位
                    scrollTo(0, 0);
                }
                //必须加才有效果
                postInvalidate();
            }
        }

        @Override
        public void onClick(View v) {
            //开始
            mScroller.startScroll(0, 0, -100, -100, 2000);
            //停止动画
            //mScroller.abortAnimation();
            //必须加才有效果
            invalidate();
        }
    }
}
