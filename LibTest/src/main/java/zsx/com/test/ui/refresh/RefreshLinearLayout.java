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
    TextView footView;

    public RefreshLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    ListView listView;

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        headView = (TextView) getChildAt(0);
        listView = (ListView) getChildAt(1);
        footView = (TextView) getChildAt(2);
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
        Default, 顶部滚动, 底部滚动, 刷新, Done
    }

    private Mode mMode = Mode.TopRefresh;
    private Status mStatus = Status.Default;
    private int mTouchSlop;

    private boolean isIntercept;
    private float mInterceptX, mInterceptY, mLastMotionY, mLastMotionX;
    private boolean mRefreshScrollEnabled;//刷新的时候 是否可以滑动
    private boolean mFilterTouchEvents;//是否过滤掉Touch事件

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
                mLastMotionY = mInterceptX = event.getY();
                mLastMotionX = mInterceptY = event.getX();
                isIntercept = true;
                break;
            case MotionEvent.ACTION_MOVE:
                break;
        }
        return isIntercept;
    }

    @Override
    public final boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction() & event.getActionMasked();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (!isIntercept) {
                    mLastMotionY = mInterceptX = event.getY();
                    mLastMotionX = mInterceptY = event.getX();
                    isIntercept = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                mLastMotionX = event.getX();
                mLastMotionY = event.getY();
                if (mStatus == Status.顶部滚动) {
                    headView.setPadding(0, (int) (mLastMotionY - mInterceptY), 0, 0);
                } else if (mStatus == Status.底部滚动) {
                    footView.setPadding(0, (int) (mLastMotionY - mInterceptY), 0, 0);
                } else if (mLastMotionY - mInterceptY > 0) {
                    mStatus = Status.顶部滚动;
                } else {
                    mStatus = Status.底部滚动;
                }

                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (mStatus == Status.顶部滚动) {
                    mStatus = Status.Default;
                    headView.setPadding(0, 0, 0, 0);
                } else if (mStatus == Status.底部滚动) {
                    mStatus = Status.Default;
                    footView.setPadding(0, 0, 0, 0);
                }
                break;
        }
        return super.onTouchEvent(event);
    }
}
