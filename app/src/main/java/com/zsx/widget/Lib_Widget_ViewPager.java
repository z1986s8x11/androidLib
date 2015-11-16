package com.zsx.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.zsx.R;
import com.zsx.itf.Lib_LifeCycle;
import com.zsx.itf.Lib_OnCycleListener;

/**
 * 可禁止滑动的ViewPager
 *
 * @author zsx
 */
public class Lib_Widget_ViewPager extends ViewPager {
    private boolean isScrollable = true;
    private boolean isAutoScroll;
    private int interval = 5000;

    public Lib_Widget_ViewPager(Context context) {
        super(context);
    }

    public Lib_Widget_ViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Lib_ViewPager);
        int n = typedArray.getIndexCount();
        for (int i = 0; i < n; i++) {
            int index = typedArray.getIndex(i);
            if (index == R.styleable.Lib_ViewPager_scrollable) {
                isScrollable = typedArray.getBoolean(index, isScrollable);
            } else if (index == R.styleable.Lib_ViewPager_autoScroll) {
                isAutoScroll = typedArray.getBoolean(index, isAutoScroll);
            } else if (index == R.styleable.Lib_ViewPager_interval) {
                interval = typedArray.getInt(index, interval);
            }
        }
        typedArray.recycle();
        if (getContext() instanceof Lib_LifeCycle) {
            Lib_LifeCycle lifeCycle = (Lib_LifeCycle) getContext();
            lifeCycle._addOnCycleListener(cycleListener);
        }
    }

    private int i = 0;
    private Handler pHandler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (getAdapter() != null && getAdapter().getCount() > 1) {
                setCurrentItem((getCurrentItem() + 1) % getAdapter().getCount());
                pHandler.postDelayed(runnable, interval);
            }
        }
    };
    private Lib_OnCycleListener cycleListener = new Lib_OnCycleListener() {
        @Override
        public void onResume() {
            if (isAutoScroll) {
                _startAutoScroll();
            }
        }

        @Override
        public void onPause() {
            if (isAutoScroll) {
                _stopAutoScroll();
            }
        }
    };

    public void _stopAutoScroll() {
        if (getAdapter() == null) {
            return;
        }
        pHandler.removeCallbacks(runnable);
    }

    public void _startAutoScroll() {
        if (getAdapter() == null) {
            return;
        }
        pHandler.removeCallbacks(runnable);
        if (getAdapter().getCount() > 1) {
            pHandler.postDelayed(runnable, interval);
        }
    }

    /**
     * 是否可以滑动
     */
    public void _setScrollable(boolean isScrollable) {
        this.isScrollable = isScrollable;
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        if (isScrollable) {
            if (isAutoScroll) {
                switch (arg0.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        _stopAutoScroll();
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        _startAutoScroll();
                        break;
                }
            }
            return super.onTouchEvent(arg0);
        }
        getParent().requestDisallowInterceptTouchEvent(true);
        super.onTouchEvent(arg0);
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (isScrollable) {
            return super.onInterceptTouchEvent(arg0);
        }
        switch (arg0.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_UP:
                super.onInterceptTouchEvent(arg0);
                return false;
            default:
                break;
        }
        return false;
    }
}
