package zsx.com.test.ui.refresh;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.LinearLayout;

import com.zsx.util._Views;

/**
 * Author       zhusx
 * Email        327270607@qq.com
 * Created      2016/4/22 11:24
 */
public abstract class Lib_Widget_BasePullLayout extends LinearLayout {
    private float mInitialMotionY, mInitialDownY;
    private int mTouchSlop;
    private boolean mIsBeingDragged;
    private static final int INVALID_POINTER = -1;
    private int mActivePointerId = INVALID_POINTER;
    public final int TYPE_COMPLETE = 1;
    public final int TYPE_ERROR = 2;
    public final int TYPE_OTHER = 3;
    protected View mHeadView;
    protected int mHeadViewHeight;

    public Lib_Widget_BasePullLayout(Context context) {
        super(context);
        init(context);
    }

    public Lib_Widget_BasePullLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public Lib_Widget_BasePullLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public Lib_Widget_BasePullLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = MotionEventCompat.getActionMasked(ev);
        if (!isEnabled() || isLoading() || canChildScrollUp()) {
            return false;
        }
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
                mIsBeingDragged = false;
                final float initialDownY = getMotionEventY(ev, mActivePointerId);
                if (initialDownY == -1) {
                    return false;
                }
                mInitialDownY = initialDownY;
                break;
            case MotionEvent.ACTION_MOVE:
                if (mActivePointerId == INVALID_POINTER) {
                    return false;
                }
                final float y = getMotionEventY(ev, mActivePointerId);
                if (y == -1) {
                    return false;
                }
                final float yDiff = y - mInitialDownY;
                if (yDiff > mTouchSlop && !mIsBeingDragged) {
                    mInitialMotionY = mInitialDownY + mTouchSlop;
                    mIsBeingDragged = true;
                }
                break;
            case MotionEventCompat.ACTION_POINTER_UP:
                onSecondaryPointerUp(ev);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mIsBeingDragged = false;
                mActivePointerId = INVALID_POINTER;
                break;
        }
        return mIsBeingDragged;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        final int action = MotionEventCompat.getActionMasked(ev);
        int pointerIndex = -1;
        if (!isEnabled() || isLoading() || canChildScrollUp()) {
            return false;
        }
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
                mIsBeingDragged = false;
                break;
            case MotionEvent.ACTION_MOVE: {
                pointerIndex = MotionEventCompat.findPointerIndex(ev, mActivePointerId);
                if (pointerIndex < 0) {
                    return false;
                }
                final float y = MotionEventCompat.getY(ev, pointerIndex);
                final float overscrollTop = y - mInitialMotionY;
                if (mIsBeingDragged) {
                    if (overscrollTop > 0) {
                        __scroll(mHeadView, mHeadViewHeight, overscrollTop);
                    } else {
                        return false;
                    }
                }
                break;
            }
            case MotionEventCompat.ACTION_POINTER_DOWN: {
                pointerIndex = MotionEventCompat.getActionIndex(ev);
                if (pointerIndex < 0) {
                    return false;
                }
                mActivePointerId = MotionEventCompat.getPointerId(ev, pointerIndex);
                break;
            }
            case MotionEventCompat.ACTION_POINTER_UP:
                onSecondaryPointerUp(ev);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                pointerIndex = MotionEventCompat.findPointerIndex(ev, mActivePointerId);
                if (pointerIndex < 0) {
                    return false;
                }
                final float y = MotionEventCompat.getY(ev, pointerIndex);
                final float overscrollTop = (y - mInitialMotionY);
                mIsBeingDragged = false;
                mActivePointerId = INVALID_POINTER;
                if (isToRefresh(mHeadView, mHeadViewHeight, overscrollTop)) {
                    __refreshData();
                } else {
                    __reset(mHeadView, mHeadViewHeight, TYPE_OTHER, null);
                }
                return false;
        }
        return true;
    }

    protected abstract void __refreshData();

    protected abstract void __scroll(View headView, int height, float overscrollTop);

    protected abstract void __start(View headView, int height);

    protected abstract void __reset(View headView, int height, int type, String error);

    protected abstract boolean isToRefresh(View headView, int height, float overscrollTop);

    public void _setRefreshView(View mTarget) {
        this.mHeadView = mTarget;
        mHeadViewHeight = mTarget.getMeasuredHeight();
        if (mHeadViewHeight == 0) {
            _Views.measureView(mTarget);
            mHeadViewHeight = mTarget.getMeasuredHeight();
        }
        __reset(mTarget, mHeadViewHeight, TYPE_OTHER, null);
    }

    public boolean canChildScrollUp() {
        if (mHeadView == null) {
            return true;
        }
        if (Build.VERSION.SDK_INT < 14) {
            if (mHeadView instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) mHeadView;
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                        .getTop() < absListView.getPaddingTop());
            } else {
                return ViewCompat.canScrollVertically(mHeadView, -1) || mHeadView.getScrollY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(mHeadView, -1);
        }
    }

    private void onSecondaryPointerUp(MotionEvent ev) {
        final int pointerIndex = MotionEventCompat.getActionIndex(ev);
        final int pointerId = MotionEventCompat.getPointerId(ev, pointerIndex);
        if (pointerId == mActivePointerId) {
            final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
            mActivePointerId = MotionEventCompat.getPointerId(ev, newPointerIndex);
        }
    }

    private float getMotionEventY(MotionEvent ev, int activePointerId) {
        final int index = MotionEventCompat.findPointerIndex(ev, activePointerId);
        if (index < 0) {
            return -1;
        }
        return MotionEventCompat.getY(ev, index);
    }

    protected boolean isLoading() {
        return false;
    }

    /**
     * 检查是否可以上拉
     */
    private boolean canChildScrollDown() {
        if (Build.VERSION.SDK_INT < 14) {
            if (mHeadView instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) mHeadView;
                return absListView.getChildCount() > 0
                        && (absListView.getLastVisiblePosition() < absListView.getChildCount() - 1
                        || absListView.getChildAt(absListView.getChildCount() - 1).getBottom() > absListView.getHeight() - absListView.getPaddingBottom());
            }
        }
        return ViewCompat.canScrollVertically(mHeadView, 1);

    }
}
