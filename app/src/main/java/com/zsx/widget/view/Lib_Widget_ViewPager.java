package com.zsx.widget.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
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
 * @author zhusx
 */
public class Lib_Widget_ViewPager extends ViewPager {
    private boolean isScrollable = true;//是否可以滑动
    private boolean isAutoScroll;//是否自动滚动
    private Bitmap mBigBitmap;
    private Paint b;

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

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (isScrollable) {
            if (isAutoScroll) {
                switch (ev.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (autoScroll != null) {
                            autoScroll._stopAutoScroll();
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_OUTSIDE:
                        if (autoScroll != null) {
                            autoScroll._startAutoScroll();
                        }
                        break;
                }
            }
        }
        return super.dispatchTouchEvent(ev);
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

    /**
     * 设置大图的背景
     */
    public void _setBigBackground(Bitmap bigBitmap) {
        this.mBigBitmap = bigBitmap;
        this.b = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.b.setFilterBitmap(true);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (this.mBigBitmap != null) {
            PagerAdapter adapter = this.getAdapter();
            if (adapter != null) {
                int count = adapter.getCount();
                int width = this.mBigBitmap.getWidth();
                int height = this.mBigBitmap.getHeight();
                if (count > 0) {
                    int x = getScrollX();
                    //子View中背景图片需要显示的宽度，放大背景图或缩小背景图。
                    int n = height * this.getWidth() / this.getHeight();
                    //(width - n) / (count - 1)表示除去显示第一个ViewPager页面用去的背景宽度，剩余的ViewPager需要显示的背景图片的宽度。
                    //getWidth()等于ViewPager一个页面的宽度，即手机屏幕宽度。在该计算中可以理解为滑动一个ViewPager页面需要滑动的像素值。
                    //((width - n) / (count - 1)) / getWidth()也就表示ViewPager滑动一个像素时，背景图片滑动的宽度。
                    //x * ((width - n) / (count - 1)) / getWidth()也就表示ViewPager滑动x个像素时，背景图片滑动的宽度。
                    //背景图片滑动的宽度的宽度可以理解为背景图片滑动到达的位置。
                    int w = (x + this.getWidth()) * ((width - n) / count) / this.getWidth();
                    canvas.drawBitmap(this.mBigBitmap, new Rect(w, 0, n + w, height), new Rect(x, 0, x + this.getWidth(), this.getHeight()), this.b);
                } else {
                    canvas.drawBitmap(this.mBigBitmap, new Rect(0, 0, width, height), new Rect(0, 0, this.getWidth(), this.getHeight()), this.b);
                }
            }
        }
        super.dispatchDraw(canvas);
    }
}
