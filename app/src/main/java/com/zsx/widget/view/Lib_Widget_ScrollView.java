package com.zsx.widget.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ScrollView;

/**
 * Author       zhusx
 * Email        327270607@qq.com
 * Created      2016/1/29.17:26
 */
public class Lib_Widget_ScrollView extends ScrollView {
    private OnScrollChangedListener listener;
    private SwipeRefreshLayout swipeRefreshLayout;

    public Lib_Widget_ScrollView(Context context) {
        super(context);
    }

    public Lib_Widget_ScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Lib_Widget_ScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public Lib_Widget_ScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        if (listener != null) {
            listener.onScrollChanged(l, t, oldl, oldt);
        }
        super.onScrollChanged(l, t, oldl, oldt);
    }

    public void _setOnScrollChangedListener(OnScrollChangedListener listener) {
        this.listener = listener;
    }

    public interface OnScrollChangedListener {
        void onScrollChanged(int x, int y, int oldX, int oldY);
    }

    private void _setRefreshListener(SwipeRefreshLayout.OnRefreshListener listener) {
        if (swipeRefreshLayout == null) {
            ViewParent parent = getParent();
            if (parent == null) {
                throw new NullPointerException(" 必须有父容器");
            }
            if (!(parent instanceof ViewGroup)) {
                throw new IllegalArgumentException("parent must is ViewGroup");
            }
            ViewGroup viewGroup = (ViewGroup) parent;
            int index = 0;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                if (viewGroup.getChildAt(i) == this) {
                    index = i;
                    break;
                }
            }
            swipeRefreshLayout = new SwipeRefreshLayout(getContext());
            initSwipeRefreshLayout(swipeRefreshLayout);
            viewGroup.removeView(this);
            ViewGroup.LayoutParams lp = getLayoutParams();
            swipeRefreshLayout.addView(this, new SwipeRefreshLayout.LayoutParams(SwipeRefreshLayout.LayoutParams.MATCH_PARENT, SwipeRefreshLayout.LayoutParams.MATCH_PARENT));
            viewGroup.addView(swipeRefreshLayout, index, lp);
        }
        swipeRefreshLayout.setOnRefreshListener(listener);
    }

    public void _setRefreshing(boolean isRefresh) {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(isRefresh);
        }
    }

    protected void initSwipeRefreshLayout(SwipeRefreshLayout swipeRefreshLayout) {
    }
}
