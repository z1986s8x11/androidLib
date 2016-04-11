package com.zsx.widget.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.zsx.R;
import com.zsx.itf.Lib_LifeCycle;
import com.zsx.itf.Lib_OnCycleListener;

/**
 * <attr name="scrollable" format="boolean" />
 * <attr name="autoScroll" format="boolean" />
 * <attr name="interval" format="integer" />
 * <p/>
 * 可禁止滑动的ViewPager
 *
 * @author zsx
 */
public class Lib_Widget_ViewPager extends ViewPager {
    private boolean isScrollable = true;//是否可以滑动
    private boolean isAutoScroll;//是否自动滚动

    private AutoScroll autoScroll;

    public Lib_Widget_ViewPager(Context context) {
        super(context);
    }

    public Lib_Widget_ViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Lib_ViewPager);
        isScrollable = typedArray.getBoolean(R.styleable.Lib_ViewPager_lib_scrollable, isScrollable);
        isAutoScroll = typedArray.getBoolean(R.styleable.Lib_ViewPager_lib_autoScroll, isAutoScroll);
        if (isAutoScroll) {
            int interval = typedArray.getInt(R.styleable.Lib_ViewPager_lib_interval, 5000);
            autoScroll = new AutoScroll(interval);
        }
        typedArray.recycle();
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        super.setAdapter(adapter);
        if (autoScroll != null) {
            autoScroll._stopAutoScroll();
            if (adapter.getCount() > 1) {
                autoScroll._startAutoScroll();
            }
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
                        if (autoScroll != null) {
                            autoScroll._stopAutoScroll();
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        if (autoScroll != null) {
                            autoScroll._startAutoScroll();
                        }
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

    private class AutoScroll {
        private int interval;//滑动间隔时间
        private Handler pHandler = new Handler();

        public AutoScroll(int interval) {
            this.interval = interval;
            if (getContext() instanceof Lib_LifeCycle) {
                Lib_LifeCycle lifeCycle = (Lib_LifeCycle) getContext();
                lifeCycle._addOnCycleListener(cycleListener);
            }
        }

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
    }
}
