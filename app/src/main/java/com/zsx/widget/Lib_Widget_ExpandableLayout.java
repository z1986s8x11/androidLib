package com.zsx.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zsx.R;


/**
 * Created by SilenceDut on 16/6/6.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class Lib_Widget_ExpandableLayout extends LinearLayout {
    private static final int EXPAND_DURATION = 300;
    private final int PREINIT = -1;
    private final int CLOSED = 0;
    private final int EXPANDED = 1;
    private final int EXPANDING = 2;
    private final int CLOSEING = 3;
    private int mExpandState;
    private ValueAnimator mExpandAnimator;
    private ValueAnimator mParentAnimator;
    private int mExpandedViewHeight;
    private boolean sIsInit = true;
    private int mExpandDuration = EXPAND_DURATION;
    private boolean mExpandWithParentScroll;
    private boolean mExpandScrollTogether;
    private OnExpandListener mOnExpandListener;

    public Lib_Widget_ExpandableLayout(Context context) {
        super(context);
        init(null);
    }

    public Lib_Widget_ExpandableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public Lib_Widget_ExpandableLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs) {
        setClickable(true);
        setOrientation(VERTICAL);
        this.setClipChildren(false);
        this.setClipToPadding(false);
        mExpandState = PREINIT;
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.Lib_ExpandableLayout);
            mExpandDuration = typedArray.getInt(R.styleable.Lib_ExpandableLayout_lib_expDuration, EXPAND_DURATION);
            mExpandWithParentScroll = typedArray.getBoolean(R.styleable.Lib_ExpandableLayout_lib_expWithParentScroll, false);
            mExpandScrollTogether = typedArray.getBoolean(R.styleable.Lib_ExpandableLayout_lib_expExpandScrollTogether, false);
            typedArray.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        if (childCount != 2) {
            throw new IllegalStateException("ExpandableLayout must has two child view !");
        }
        if (sIsInit) {
            ((MarginLayoutParams) getChildAt(0).getLayoutParams()).bottomMargin = 0;
            MarginLayoutParams marginLayoutParams = ((MarginLayoutParams) getChildAt(1).getLayoutParams());
            marginLayoutParams.bottomMargin = 0;
            marginLayoutParams.topMargin = 0;
            marginLayoutParams.height = 0;
            mExpandedViewHeight = getChildAt(1).getMeasuredHeight();
            sIsInit = false;
            mExpandState = CLOSED;
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }


    private void verticalAnimate(final int startHeight, final int endHeight) {
        final ViewGroup mViewParent = (ViewGroup) getParent();
        int distance = (int) (getY() + getMeasuredHeight() + mExpandedViewHeight - mViewParent.getMeasuredHeight());
        final View target = getChildAt(1);
        mExpandAnimator = ValueAnimator.ofInt(0, 1);
        mExpandAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            private IntEvaluator intEvaluator = new IntEvaluator();
            int lastHeight = 0;

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                    float fraction = animation.getAnimatedFraction();
                    int height = intEvaluator.evaluate(fraction, startHeight, endHeight);
                    int scrollStep = height - lastHeight;
                    lastHeight = height;
                    target.getLayoutParams().height = height;
                    target.requestLayout();
                }
            }
        });
        mExpandAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (endHeight - startHeight < 0) {
                    mExpandState = CLOSED;
                    if (mOnExpandListener != null) {
                        mOnExpandListener.onExpand(false);
                    }
                } else {
                    mExpandState = EXPANDED;
                    if (mOnExpandListener != null) {
                        mOnExpandListener.onExpand(true);
                    }

                }
            }
        });
        mExpandState = mExpandState == EXPANDED ? CLOSEING : EXPANDING;
        mExpandAnimator.setDuration(mExpandDuration);
        if (mExpandState == EXPANDING && mExpandWithParentScroll && distance > 0) {
            mExpandAnimator = parentScroll(distance);
            AnimatorSet animatorSet = new AnimatorSet();
            if (mExpandScrollTogether) {
                animatorSet.playSequentially(mExpandAnimator, mParentAnimator);
            } else {
                animatorSet.playTogether(mExpandAnimator, mParentAnimator);
            }
            animatorSet.start();
        } else {
            mExpandAnimator.start();
        }

    }

    private ValueAnimator parentScroll(final int distance) {
        final ViewGroup mViewParent = (ViewGroup) getParent();
        mParentAnimator = ValueAnimator.ofInt(0, distance);
        mParentAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            int lastDy;
            int dy;

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                dy = (int) animation.getAnimatedValue() - lastDy;
                lastDy = (int) animation.getAnimatedValue();
                mViewParent.scrollBy(0, dy);
            }
        });
        mParentAnimator.setDuration(mExpandDuration);
        return mExpandAnimator;
    }

    public void _setExpand(boolean expand) {
        if (mExpandState == PREINIT) {
            return;
        }
        getChildAt(1).getLayoutParams().height = expand ? mExpandedViewHeight : 0;
        requestLayout();
        mExpandState = expand ? EXPANDED : CLOSED;
    }

    public boolean _isExpanded() {
        return mExpandState == EXPANDED;
    }

    public void _toggle() {
        if (mExpandState == EXPANDED) {
            _close();
        } else if (mExpandState == CLOSED) {
            _expand();
        }
    }

    public void _expand() {
        verticalAnimate(0, mExpandedViewHeight);
    }

    public void _close() {
        verticalAnimate(mExpandedViewHeight, 0);
    }

    @Override
    public boolean performClick() {
        _toggle();
        return super.performClick();
    }

    public interface OnExpandListener {
        void onExpand(boolean expanded);
    }

    public void _setOnExpandListener(OnExpandListener onExpandListener) {
        this.mOnExpandListener = onExpandListener;
    }


    public void _setExpandScrollTogether(boolean expandScrollTogether) {
        this.mExpandScrollTogether = expandScrollTogether;
    }

    public void _setExpandWithParentScroll(boolean expandWithParentScroll) {
        this.mExpandWithParentScroll = expandWithParentScroll;
    }

    public void _setExpandDuration(int expandDuration) {
        this.mExpandDuration = expandDuration;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mExpandAnimator != null && mExpandAnimator.isRunning()) {
            mExpandAnimator.cancel();
        }
        if (mParentAnimator != null && mParentAnimator.isRunning()) {
            mParentAnimator.cancel();
        }
    }
}
