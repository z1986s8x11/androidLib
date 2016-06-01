package zsx.com.test.ui.refresh;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.AbsListView;
import android.widget.TextView;

import com.zsx.debug.LogUtil;

/**
 * Author       zhusx
 * Email        327270607@qq.com
 * Created      2016/5/31 11:36
 */
public class PullLayout extends ViewGroup implements NestedScrollingParent, NestedScrollingChild {

    private static final String LOG_TAG = PullLayout.class.getSimpleName();

    private static final int INVALID_POINTER = -1;
    private static final float DRAG_RATE = .5f;

    private View mTarget; // the target of the gesture

    private int mTouchSlop;

    private final NestedScrollingParentHelper mNestedScrollingParentHelper;
    private final NestedScrollingChildHelper mNestedScrollingChildHelper;

    private final int[] mParentOffsetInWindow = new int[2];

    private boolean mNestedScrollInProgress;

    private float mInitialMotionY;
    private float mInitialDownY;
    private boolean mIsBeingDragged;

    private int mActivePointerId = INVALID_POINTER;

    //正在执行返回动画
    private boolean mReturningToStart;

    private int mHeadViewIndex = -1;

    float mScrollY = 0;

    private int mWidth;
    private int mHeight;

    private boolean mCorrected;


    View mRefreshHead;
    View mLoadFooter;

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        reset();
    }

    /**
     * Simple constructor to use when creating a SwipeRefreshLayout from code.
     *
     * @param context
     */
    public PullLayout(Context context) {
        this(context, null);
    }

    /**
     * Constructor that is called when inflating SwipeRefreshLayout from XML.
     *
     * @param context
     * @param attrs
     */
    public PullLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        setWillNotDraw(false);

//        attachHeadView((WaterDropView) LayoutInflater.from(context).inflate(R.layout.lay_water_drop_view, this, false));
//        attachFooterView(new FooterView(context));

        ViewCompat.setChildrenDrawingOrderEnabled(this, true);
        // the absolute offset has to take into account that the circle starts at an offset
        mNestedScrollingParentHelper = new NestedScrollingParentHelper(this);

        mNestedScrollingChildHelper = new NestedScrollingChildHelper(this);
        setNestedScrollingEnabled(true);
    }

    @Override
    protected int getChildDrawingOrder(int childCount, int i) {
        if (mHeadViewIndex < 0) {
            return i;
        } else if (i == childCount - 1) {
            // Draw the selected child last
            return mHeadViewIndex;
        } else if (i >= mHeadViewIndex) {
            // Move the children after the selected child earlier one
            return i + 1;
        } else {
            // Keep the children before the selected child the same
            return i;
        }
    }

    private void ensureTarget() {
        // Don't bother getting the parent height if the parent hasn't been laid
        // out yet.
        if (mTarget == null) {
            mRefreshHead = getChildAt(0);
            mTarget = getChildAt(1);
            mLoadFooter = getChildAt(2);
            RecyclerView view = (RecyclerView) mTarget;
            view.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            view.setAdapter(new RecyclerView.Adapter() {
                @Override
                public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                    TextView t = new TextView(parent.getContext());
                    t.setPadding(40, 40, 40, 40);
                    RecyclerView.ViewHolder holder = new a(t);
                    return holder;
                }

                @Override
                public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                    ((a) holder).tv.setText(String.valueOf(position));
                }

                @Override
                public int getItemCount() {
                    return 20;
                }

                class a extends RecyclerView.ViewHolder {
                    public TextView tv;

                    public a(View itemView) {
                        super(itemView);
                        tv = (TextView) itemView;
                    }


                }
            });
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (getChildCount() == 0) {
            return;
        }
        if (mTarget == null) {
            ensureTarget();
        }
        if (mTarget == null) {
            return;
        }
        final View child = mTarget;
        final int childPaddingLeft = getPaddingLeft();
        final int childPaddingTop = getPaddingTop();
        final int childPaddingBottom = getPaddingBottom();
        final int childPaddingRight = getPaddingRight();
        final int childWidth = mWidth - childPaddingLeft - childPaddingRight;
        final int childHeight = mHeight - childPaddingTop - childPaddingBottom;

        int mFooterWidth = mLoadFooter.getMeasuredWidth();
        int mFooterHeight = mLoadFooter.getMeasuredHeight();

        int mHeadWidth = mRefreshHead.getMeasuredWidth();
        int mHeadHeight = mRefreshHead.getMeasuredHeight();

        int offset = Math.round(mScrollY);
        child.layout(childPaddingLeft,
                childPaddingTop + offset,
                childPaddingLeft + childWidth,
                childPaddingTop + childHeight + offset);

        int footerTop = childHeight + childPaddingBottom + offset;
        mLoadFooter.layout(0, footerTop, mFooterWidth, footerTop + mFooterHeight);

        int next = -mHeadHeight + childPaddingTop + offset;
        next = next > 0 ? 0 : next;
        mRefreshHead.layout((mWidth - mHeadWidth) / 2, next, (mWidth + mHeadWidth) / 2, offset + childPaddingTop);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mTarget == null) {
            ensureTarget();
        }
        if (mTarget == null) {
            return;
        }

        mTarget.measure(MeasureSpec.makeMeasureSpec(
                getMeasuredWidth() - getPaddingLeft() - getPaddingRight(),
                MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(
                getMeasuredHeight() - getPaddingTop() - getPaddingBottom(), MeasureSpec.EXACTLY));

        mLoadFooter.measure(widthMeasureSpec, heightMeasureSpec);
        mRefreshHead.measure(widthMeasureSpec, heightMeasureSpec);

        // Get the index of the mRefreshHead
        for (int index = 0; index < getChildCount(); index++) {
            if (getChildAt(index) == mRefreshHead) {
                mHeadViewIndex = index;
                break;
            }
        }
    }

    /**
     * @return child在竖直方向上是否能向上滚动
     */
    public boolean canChildScrollUp() {
        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (mTarget instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) mTarget;
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                        .getTop() < absListView.getPaddingTop());
            } else {
                return ViewCompat.canScrollVertically(mTarget, -1) || mTarget.getScrollY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(mTarget, -1);
        }
    }

    public boolean canChildScrollDown() {
        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (mTarget instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) mTarget;
                int count = absListView.getAdapter().getCount();
                int fristPos = absListView.getFirstVisiblePosition();
                if (fristPos == 0 && absListView.getChildAt(0).getTop() >= absListView.getPaddingTop()) {
                    return false;
                }
                int lastPos = absListView.getLastVisiblePosition();
                return lastPos > 0 && count > 0 && lastPos == count - 1;
            } else {
                return ViewCompat.canScrollVertically(mTarget, 1) || mTarget.getScrollY() < 0;
            }
        } else {
            return ViewCompat.canScrollVertically(mTarget, 1);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        ensureTarget();

        final int action = MotionEventCompat.getActionMasked(ev);

        if (mReturningToStart && action == MotionEvent.ACTION_DOWN) {
            mReturningToStart = false;
        }

        if (!isEnabled() || mReturningToStart || canNestScroll() || mNestedScrollInProgress) {
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
                    Log.e(LOG_TAG, "Got ACTION_MOVE event but don't have an active pointer id.");
                    return false;
                }

                final float y = getMotionEventY(ev, mActivePointerId);
                if (y == -1) {
                    return false;
                }
                final float yDiff = y - mInitialDownY;

                boolean canPullDown = canChildScrollDown() || (!canChildScrollDown() && !canChildScrollUp());

                //yDiff > mTouchSlop向下移动,这个值只赋值一次,记录child初始位置
                if (yDiff > mTouchSlop && canPullDown && !mIsBeingDragged && !isLoading()) {
                    mInitialMotionY = mInitialDownY + mTouchSlop;
                    mIsBeingDragged = true;
                } else if (-yDiff > mTouchSlop && canChildScrollUp() && !mIsBeingDragged && !isRefreshing()) {
                    //-yDiff > mTouchSlop向上移动,这个值只赋值一次,记录child初始位置
                    mInitialMotionY = mInitialDownY - mTouchSlop;
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

        return mIsBeingDragged || isShowRefreshing() || isShowLoading();
    }

    private boolean isShowRefreshing() {
        return isRefreshing() && mScrollY > 0;
    }

    private boolean isShowLoading() {
        return isLoading() && mScrollY < 0;
    }

    private boolean canNestScroll() {
        return canChildScrollDown() && canChildScrollUp();
    }

    private float getMotionEventY(MotionEvent ev, int activePointerId) {
        final int index = MotionEventCompat.findPointerIndex(ev, activePointerId);
        if (index < 0) {
            return -1;
        }
        return MotionEventCompat.getY(ev, index);
    }

    @Override
    public void requestDisallowInterceptTouchEvent(boolean b) {
        // if this is a List < L or another view that doesn't support nested
        // scrolling, ignore this request so that the vertical scroll event
        // isn't stolen
        if ((android.os.Build.VERSION.SDK_INT < 21 && mTarget instanceof AbsListView)
                || (mTarget != null && !ViewCompat.isNestedScrollingEnabled(mTarget))) {
            // Nope.
        } else {
            super.requestDisallowInterceptTouchEvent(b);
        }
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return isEnabled() &&
                canNestScroll() &&
                !mReturningToStart &&
                !isRefreshing() &&
                !isLoading() &&
                (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int axes) {
        // Reset the counter of how much leftover scroll needs to be consumed.
        mNestedScrollingParentHelper.onNestedScrollAccepted(child, target, axes);
        // Dispatch up to the nested parent
        startNestedScroll(axes & ViewCompat.SCROLL_AXIS_VERTICAL);
        mNestedScrollInProgress = true;
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
    }

    @Override
    public int getNestedScrollAxes() {
        return mNestedScrollingParentHelper.getNestedScrollAxes();
    }

    @Override
    public void onStopNestedScroll(View target) {
        mNestedScrollingParentHelper.onStopNestedScroll(target);
        mNestedScrollInProgress = false;
    }

    @Override
    public void onNestedScroll(final View target, final int dxConsumed, final int dyConsumed,
                               final int dxUnconsumed, final int dyUnconsumed) {
        dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed,
                mParentOffsetInWindow);
    }

    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        mNestedScrollingChildHelper.setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return mNestedScrollingChildHelper.isNestedScrollingEnabled();
    }

    @Override
    public boolean startNestedScroll(int axes) {
        return mNestedScrollingChildHelper.startNestedScroll(axes);
    }

    @Override
    public void stopNestedScroll() {
        mNestedScrollingChildHelper.stopNestedScroll();
    }

    @Override
    public boolean hasNestedScrollingParent() {
        return mNestedScrollingChildHelper.hasNestedScrollingParent();
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed,
                                        int dyUnconsumed, int[] offsetInWindow) {
        return mNestedScrollingChildHelper.dispatchNestedScroll(dxConsumed, dyConsumed,
                dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        return mNestedScrollingChildHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX,
                                    float velocityY) {
        return dispatchNestedPreFling(velocityX, velocityY);
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY,
                                 boolean consumed) {
        return dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return mNestedScrollingChildHelper.dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return mNestedScrollingChildHelper.dispatchNestedPreFling(velocityX, velocityY);
    }

    private void moveSpinner(float overScroll) {
        //防止在上拉或者下拉的过程中,往回拉过头的现象
        boolean canPullDown = canChildScrollDown() || (!canChildScrollDown() && !canChildScrollUp());
        overScroll = canPullDown ? overScroll < 0 ? 0 : overScroll : overScroll > 0 ? 0 : overScroll;

        updateLayout(overScroll);
    }

    /**
     * 更新界面
     *
     * @param overScroll
     */
    private void updateLayout(float overScroll) {
        if (mScrollY != overScroll) {
            mScrollY = overScroll;
            if (mScrollY >= 0) {
                LogUtil.e(this, "onPull head:" + mScrollY);
//                mRefreshHead.onPull(mScrollY, !mLoadFooter.isLoading());
            } else if (mScrollY < 0) {
                LogUtil.e(this, "onPull foot:" + mScrollY);
//                mLoadFooter.onPull(mScrollY, !mRefreshHead.isRefreshing());
            }
            requestLayout();
        }
    }

    private void finishSpinner(float overScroll) {
        clearAnimation();
        if (overScroll >= 0) {
            LogUtil.e(this, "onFingerUp head:" + overScroll);
//            mRefreshHead.onFingerUp(overScroll);
        } else {
            LogUtil.e(this, "onFingerUp foot:" + overScroll);
//            mLoadFooter.onFingerUp(overScroll);
        }
    }

    /**
     * 拉回控件
     *
     * @param reset
     */
    public void animToStartPosition(final boolean reset) {
        if (mScrollY == 0 && !isRefreshing() && !isLoading())
            return;
        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float fraction, Transformation t) {
                updateLayout(evaluate(fraction, mScrollY, 0));
            }
        };
        animation.setDuration(180);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mReturningToStart = true;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mReturningToStart = false;
                if (reset) reset();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        startAnimation(animation);
    }

    public void animToRightPosition(final float targetY, boolean isRefreshing, final boolean notify) {
        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float fraction, Transformation t) {
                updateLayout(evaluate(fraction, mScrollY, targetY));
            }
        };
        animation.setDuration(180);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mReturningToStart = true;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
//                if (notify) mRefreshHead.refreshImmediately();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        startAnimation(animation);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        final int action = MotionEventCompat.getActionMasked(ev);
        int pointerIndex = -1;

        if (mReturningToStart && action == MotionEvent.ACTION_DOWN) {
            mReturningToStart = false;
        }

        if (!isEnabled() || mReturningToStart || canNestScroll() || mNestedScrollInProgress) {
            // Fail fast if we're not in a state where a swipe is possible
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
                    Log.e(LOG_TAG, "Got ACTION_MOVE event but have an invalid active pointer id.");
                    return false;
                }

                final float y = MotionEventCompat.getY(ev, pointerIndex);
                //刷新或者加载中,防止再次点击屏幕回弹
                correctInitialMotionY(y);
                final float overScroll = (y - mInitialMotionY) * DRAG_RATE;
                if (mIsBeingDragged || isShowRefreshing() || isShowLoading()) {
                    moveSpinner(overScroll);
                }
                break;
            }
            case MotionEventCompat.ACTION_POINTER_DOWN: {
                pointerIndex = MotionEventCompat.getActionIndex(ev);
                if (pointerIndex < 0) {
                    Log.e(LOG_TAG, "Got ACTION_POINTER_DOWN event but have an invalid action index.");
                    return false;
                }
                mActivePointerId = MotionEventCompat.getPointerId(ev, pointerIndex);
                break;
            }

            case MotionEventCompat.ACTION_POINTER_UP:
                onSecondaryPointerUp(ev);
                break;

            case MotionEvent.ACTION_UP: {
                pointerIndex = MotionEventCompat.findPointerIndex(ev, mActivePointerId);
                if (pointerIndex < 0) {
                    Log.e(LOG_TAG, "Got ACTION_UP event but don't have an active pointer id.");
                    return false;
                }

                mIsBeingDragged = false;
                mCorrected = false;
                //松开手指
                finishSpinner(mScrollY);
                mActivePointerId = INVALID_POINTER;
                return false;
            }
            case MotionEvent.ACTION_CANCEL:
                return false;
        }

        return true;
    }

    /**
     * 纠正开始点击的Y坐标,防止刷新或者加载过程中再次的手势
     *
     * @param y
     */
    private void correctInitialMotionY(float y) {
        if (!mCorrected) {
            if (isRefreshing() || isLoading()) {
                mInitialMotionY = y - mScrollY / DRAG_RATE;
            }
            mCorrected = true;
        }
    }

    public boolean isRefreshing() {
        return false;
    }

    public boolean isLoading() {
        return false;
    }

    private void onSecondaryPointerUp(MotionEvent ev) {
        final int pointerIndex = MotionEventCompat.getActionIndex(ev);
        final int pointerId = MotionEventCompat.getPointerId(ev, pointerIndex);
        if (pointerId == mActivePointerId) {
            // This was our active pointer going up. Choose a new
            // active pointer and adjust accordingly.
            final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
            mActivePointerId = MotionEventCompat.getPointerId(ev, newPointerIndex);
        }
    }

    /**
     * 拉回
     */
    public void finishPull() {
        if (mScrollY >= 0) {
//            mRefreshHead.finishPull(mIsBeingDragged);
            LogUtil.e(this, "finishPull head " + String.valueOf(mIsBeingDragged));
        } else {
//            mLoadFooter.finishPull(mIsBeingDragged);
            LogUtil.e(this, "finishPull foot " + String.valueOf(mIsBeingDragged));
        }
    }

    private void reset() {
        LogUtil.e(this, "head and foot reset");
//        mRefreshHead.reset();
//        mLoadFooter.reset();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    /**
     * 估值器
     *
     * @param fraction
     * @param startValue
     * @param endValue
     * @return
     */
    public Float evaluate(float fraction, Number startValue, Number endValue) {
        float startFloat = startValue.floatValue();
        return startFloat + fraction * (endValue.floatValue() - startFloat);
    }
}