package zsx.com.test.ui.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Administrator on 2015/12/16.
 */
public class RefreshLinearLayout extends LinearLayout {
    TextView headView;

    public RefreshLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    ListView listView;

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        headView = (TextView) getChildAt(0);
        listView = (ListView) getChildAt(1);
        init();
    }

    private enum Mode {
        None, TopRefresh, BottomRefresh, Both
    }

    private void init() {
        ViewConfiguration config = ViewConfiguration.get(getContext());
        mTouchSlop = config.getScaledTouchSlop();
    }

    private enum Status {
        Default, 滚动, 刷新, Done
    }

    private Mode mMode = Mode.TopRefresh;
    private Status mStatus = Status.Default;
    private int mTouchSlop;

    protected boolean isTop() {
        return listView.getFirstVisiblePosition() == 0;
    }

    protected boolean isBottom() {
        return listView.getLastVisiblePosition() == listView.getAdapter().getCount();
    }

    private boolean isIntercept;
    private float mInterceptX, mInterceptY, mLastMotionY, mLastMotionX;
    private boolean mRefreshScrollEnabled;//刷新的时候 是否可以滑动
    private boolean mFilterTouchEvents;//是否过滤掉Touch事件

    private boolean isRefreshing() {
        return mStatus == Status.刷新;
    }

    private boolean isReadyForPull() {
        return listView.getFirstVisiblePosition() == 0 || listView.getLastVisiblePosition() == listView.getCount();
    }

    @Override
    public final boolean onInterceptTouchEvent(MotionEvent event) {
        int action = event.getAction() & event.getActionMasked();
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            isIntercept = false;
            return false;
        }
        if (action != MotionEvent.ACTION_DOWN && isIntercept) {
            return true;
        }
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (isReadyForPull()) {
                    mLastMotionY = mInterceptX = event.getY();
                    mLastMotionX = mInterceptY = event.getX();
                    isIntercept = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (!mRefreshScrollEnabled && isRefreshing()) {
                    return true;
                }
                if (isReadyForPull()) {
//                    float y = event.getY();
//                    float x = event.getX();
//                    final float diff, oppositeDiff, absDiff;
//                    diff = y - mLastMotionY;
//                    oppositeDiff = x - mLastMotionX;
//                    absDiff = Math.abs(diff);
//
//                    if (absDiff > mTouchSlop && (!mFilterTouchEvents || absDiff > Math.abs(oppositeDiff))) {
//                        if (mMode.showHeaderLoadingLayout() && diff >= 1f && isReadyForPullStart()) {
//                            mLastMotionY = y;
//                            mLastMotionX = x;
//                            mIsBeingDragged = true;
//                            if (mMode == Mode.BOTH) {
//                                mCurrentMode = Mode.PULL_FROM_START;
//                            }
//                        } else if (mMode.showFooterLoadingLayout() && diff <= -1f && isReadyForPullEnd()) {
//                            mLastMotionY = y;
//                            mLastMotionX = x;
//                            mIsBeingDragged = true;
//                            if (mMode == Mode.BOTH) {
//                                mCurrentMode = Mode.PULL_FROM_END;
//                            }
//                        }
//                    }
                }
                break;
        }
        return isIntercept;
    }

    @Override
    public final boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return super.onTouchEvent(event);
    }
}
