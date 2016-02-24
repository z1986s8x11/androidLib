package zsx.com.test.ui.refresh;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

import com.zsx.debug.LogUtil;
import com.zsx.util.Lib_Util_Widget;

/**
 * Created by Administrator on 2016/1/13.
 */
public abstract class Lib_AutoListView extends ListView {
    private View headView;

    public Lib_AutoListView(Context context) {
        super(context);
        init();
    }

    public Lib_AutoListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    protected void init() {
        setBackgroundDrawable(null);
        setCacheColorHint(Color.TRANSPARENT);
        setHorizontalFadingEdgeEnabled(false);
        setVerticalFadingEdgeEnabled(false);
        headView = initHeadView();
        Lib_Util_Widget.measureView(headView);
        headContentHeight = headView.getMeasuredHeight();
        headView.setPadding(0, -headContentHeight, 0, 0);
    }

    protected abstract View initHeadView();

    protected enum HeadStatus {
        RELEASE_To_REFRESH, // 松开刷新
        PULL_To_REFRESH, // 下拉刷新
        REFRESHING, // 正在刷新
        DONE // 默认
    }

    protected enum FootStatus {
        DONE, // 默认
        LOADING, // 正在加载更多
        NO_DATA, // 没有数据
        ERROR// 加载数据失败
    }

    private HeadStatus headStatus = HeadStatus.DONE;
    private FootStatus footStatus = null;
    // 实际的padding的距离与界面上偏移距离的比例
    private final static int RATIO = 3;
    // 用于保证startY的值在一个完整的touch事件中只被记录一次
    private boolean isRecored;
    protected int headContentHeight;
    private int startY;
    /**
     * 控制滑动下拉惯性 启动下拉刷新
     */
    private boolean isUserTouch;
    private boolean isBack;

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isUserTouch = true;
                if (getFirstVisiblePosition() == 0 && !isRecored) {
                    isRecored = true;
                    startY = (int) event.getY();
                    // Log.v(TAG, "在down时候记录当前位置‘");
                }
                break;

            case MotionEvent.ACTION_UP:
                if (footStatus != FootStatus.LOADING
                        && headStatus != HeadStatus.REFRESHING) {
                    if (headStatus == null || headStatus == HeadStatus.DONE) {
//					if (event.getY() - startY < 40) {
//					}
                    }
                    if (headStatus == HeadStatus.DONE) {
                        // 什么都不做
                    }
                    if (headStatus == HeadStatus.PULL_To_REFRESH) {
                        changeHeaderViewByState(HeadStatus.DONE);
                        // Log.v(TAG, "由下拉刷新状态，到done状态");
                    }
                    if (headStatus == HeadStatus.RELEASE_To_REFRESH) {
                        changeHeaderViewByState(HeadStatus.REFRESHING);
                        // Log.v(TAG, "由松开刷新状态，到done状态");
                    }
                }
                isUserTouch = false;
                isRecored = false;
                isBack = false;
                break;

            case MotionEvent.ACTION_MOVE:
                if (isUserTouch) {
                    int tempY = (int) event.getY();

                    if (!isRecored && getFirstVisiblePosition() == 0) {
                        // Log.v(TAG, "在move时候记录下位置");
                        isRecored = true;
                        startY = tempY;
                    }
                    if (headStatus != HeadStatus.REFRESHING && isRecored
                            && footStatus != FootStatus.LOADING) {

                        // 保证在设置padding的过程中，当前的位置一直是在head，否则如果当列表超出屏幕的话，当在上推的时候，列表会同时进行滚动

                        // 可以松手去刷新了
                        if (headStatus == HeadStatus.RELEASE_To_REFRESH) {
                            setSelection(0);
                            // 往上推了，推到了屏幕足够掩盖head的程度，但是还没有推到全部掩盖的地步
                            if (((tempY - startY) / RATIO < headContentHeight)
                                    && (tempY - startY) > 0) {
                                changeHeaderViewByState(HeadStatus.PULL_To_REFRESH);
                                // Log.v(TAG, "由松开刷新状态转变到下拉刷新状态");
                            }
                            // 一下子推到顶了
                            else if (tempY - startY <= 0) {
                                changeHeaderViewByState(HeadStatus.DONE);
                                // Log.v(TAG, "由松开刷新状态转变到done状态");
                            }
                            // 往下拉了，或者还没有上推到屏幕顶部掩盖head的地步
                            else {
                                // 不用进行特别的操作，只用更新paddingTop的值就行了
                            }
                        }
                        // 还没有到达显示松开刷新的时候,DONE或者是PULL_To_REFRESH状态
                        if (headStatus == HeadStatus.PULL_To_REFRESH) {
                            setSelection(0);
                            // 下拉到可以进入RELEASE_TO_REFRESH的状态
                            if ((tempY - startY) / RATIO >= headContentHeight) {
                                isBack = true;
                                changeHeaderViewByState(HeadStatus.RELEASE_To_REFRESH);
                                // Log.v(TAG, "由done或者下拉刷新状态转变到松开刷新");
                            }
                            // 上推到顶了
                            else if (tempY - startY <= 0) {
                                changeHeaderViewByState(HeadStatus.DONE);
                                // Log.v(TAG, "由DOne或者下拉刷新状态转变到done状态");
                            }
                        }

                        // done状态下
                        if (headStatus == HeadStatus.DONE) {
                            if (tempY - startY > 0) {
                                changeHeaderViewByState(HeadStatus.PULL_To_REFRESH);
                            }
                        }

                        // 更新headView的size
                        if (headStatus == HeadStatus.PULL_To_REFRESH) {
                            int offset = -1 * headContentHeight + (tempY - startY) / RATIO;
                            changeHeaderViewByState(HeadStatus.PULL_To_REFRESH, offset);
                        }

                        // 更新headView的paddingTop
                        if (headStatus == HeadStatus.RELEASE_To_REFRESH) {
                            int offset = (tempY - startY) / RATIO - headContentHeight;
                            changeHeaderViewByState(HeadStatus.RELEASE_To_REFRESH, offset);
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                if (headStatus != HeadStatus.REFRESHING && isRecored
                        && footStatus != FootStatus.LOADING) {
                    changeHeaderViewByState(HeadStatus.DONE);
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction() & ev.getActionMasked();
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_DOWN) {
            return true;
        }
        switch (action) {
//            case MotionEvent.ACTION_CANCEL:
//                LogUtil.e("dispatchTouchEvent", "ACTION_CANCEL");
//                return true;
//            case MotionEvent.ACTION_UP:
//                LogUtil.e("dispatchTouchEvent", "ACTION_UP");
//                return true;
            case MotionEvent.ACTION_MOVE:
                LogUtil.e("dispatchTouchEvent", "ACTION_MOVE");
                if (HeadStatus.DONE != headStatus) {
                    return false;
                }
                break;
//            case MotionEvent.ACTION_DOWN:
//                LogUtil.e("dispatchTouchEvent", "ACTION_DOWN");
//                return true;

        }
        return super.dispatchTouchEvent(ev);
    }

    private void changeHeaderViewByState(HeadStatus state) {
        this.headStatus = state;
        switch (state) {
            case RELEASE_To_REFRESH:
                moveToRefresh(headView);
                // Log.v(TAG, "当前状态，松开刷新");
                break;
            case PULL_To_REFRESH:
                // 是由RELEASE_To_REFRESH状态转变来的
                moveToReversal(headView, isBack);
                isBack = false;
                // Log.v(TAG, "当前状态，下拉刷新");
                break;

            case REFRESHING:
                upToRefresh(headView);
                // Log.v(TAG, "当前状态,正在刷新...");
                break;
            case DONE:
                defaultStatus(headView);
                // Log.v(TAG, "当前状态，done");
                break;
        }
    }

    protected abstract void moveToReversal(View headView, boolean isBack);

    protected abstract void moveToRefresh(View headView);

    protected abstract void upToRefresh(View headView);

    protected abstract void defaultStatus(View headView);

    private void changeHeaderViewByState(HeadStatus state, int offset) {
        switch (state) {
            case RELEASE_To_REFRESH:
                scrollToRefresh(headView, offset, true);
                break;
            case PULL_To_REFRESH:
                scrollToRefresh(headView, offset, false);
                break;
        }
    }

    protected abstract void scrollToRefresh(View headView, int offset, boolean isTop);
}
