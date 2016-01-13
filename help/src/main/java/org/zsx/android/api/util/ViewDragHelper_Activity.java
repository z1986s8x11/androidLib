package org.zsx.android.api.util;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zsx.debug.LogUtil;

import org.zsx.android.api._BaseActivity;

/**
 * Created by Administrator on 2016/1/13.
 */
public class ViewDragHelper_Activity extends _BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new DragView(this));
    }

    public static class DragView extends LinearLayout {
        ViewDragHelper mDragHelper;

        public DragView(Context context) {
            super(context);
            setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            View b = new View(context);
            b.setLayoutParams(new ViewGroup.LayoutParams(100, 100));
            b.setBackgroundColor(Color.RED);
            addView(b);
            b = new View(context);
            b.setLayoutParams(new ViewGroup.LayoutParams(100, 100));
            b.setBackgroundColor(Color.YELLOW);
            addView(b);
            mDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
                /**
                 * 尝试捕获子view，一定要返回true
                 * @param  child 尝试捕获的view
                 * @param  pointerId 指示器id？
                 * 这里可以决定哪个子view可以拖动
                 */
                @Override
                public boolean tryCaptureView(View child, int pointerId) {
                    return true;
                }

                /**
                 * releasedChild被释放的时候，xvel和yvel是x和y方向的加速度
                 */
                @Override
                public void onViewReleased(View releasedChild, float xvel, float yvel) {
                    super.onViewReleased(releasedChild, xvel, yvel);
                }

                @Override
                public void onViewCaptured(View capturedChild, int activePointerId) {
                    super.onViewCaptured(capturedChild, activePointerId);
                }

                /**
                 * 当拖拽到状态改变时回调
                 * @params 新的状态
                 */
                @Override
                public void onViewDragStateChanged(int state) {
                    switch (state) {
                        case ViewDragHelper.STATE_DRAGGING:  // 正在被拖动
                            LogUtil.e(this, "正在被拖动");
                            break;
                        case ViewDragHelper.STATE_IDLE:  // view没有被拖拽或者 正在进行fling/snap
                            LogUtil.e(this, "view没有被拖拽或者 正在进行fling/snap");
                            break;
                        case ViewDragHelper.STATE_SETTLING: // fling完毕后被放置到一个位置
                            LogUtil.e(this, "fling完毕后被放置到一个位置");
                            break;
                    }
                    super.onViewDragStateChanged(state);
                }

                /**
                 * 这个用来控制垂直移动的边界范围，单位是像素。
                 */
                @Override
                public int getViewVerticalDragRange(View child) {
                    return super.getViewVerticalDragRange(child);
                }

                /**
                 * 这个用来控制竖直移动的边界范围，单位是像素。
                 */
                @Override
                public int getViewHorizontalDragRange(View child) {
                    return super.getViewHorizontalDragRange(child);
                }

                /**
                 * 处理水平方向上的拖动
                 * @param  child 被拖动到view
                 * @param  left 移动到达的x轴的距离
                 * @param  dx 建议的移动的x距离
                 */
                @Override
                public int clampViewPositionHorizontal(View child, int left, int dx) {
                    return left;
                }

                /**
                 *  处理竖直方向上的拖动
                 * @param  child 被拖动到view
                 * @param  top 移动到达的y轴的距离
                 * @param  dy 建议的移动的y距离
                 */
                @Override
                public int clampViewPositionVertical(View child, int top, int dy) {
                    return top;
                }

                /**
                 * 这个是当changedView的位置发生变化时调用，我们可以在这里面控制View的显示位置和移动。
                 */
                @Override
                public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                    super.onViewPositionChanged(changedView, left, top, dx, dy);
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(MotionEvent ev) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_DOWN:
                    mDragHelper.cancel(); // 相当于调用 processTouchEvent收到ACTION_CANCEL
                    break;
            }
            return mDragHelper.shouldInterceptTouchEvent(ev);
        }

        public boolean onTouchEvent(MotionEvent event) {
            mDragHelper.processTouchEvent(event);
            return true;
        }

        @Override
        public void computeScroll() {
            super.computeScroll();
            if (mDragHelper.continueSettling(true)) {
                ViewCompat.postInvalidateOnAnimation(this);
            }
        }

        public DragView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }
    }
}
