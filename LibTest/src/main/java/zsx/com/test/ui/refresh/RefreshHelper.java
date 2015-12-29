package zsx.com.test.ui.refresh;

import android.graphics.Color;
import android.view.MotionEvent;
import android.widget.AbsListView;
import android.widget.ListView;

import com.zsx.util.Lib_Util_Widget;
import com.zsx.widget.XListViewHeader;

/**
 * Created by Administrator on 2015/12/24.
 */
public class RefreshHelper {
    private AbsListView mAbsListView;
    private XListViewHeader headView;

    public RefreshHelper(AbsListView mAbsListView) {
        this.mAbsListView = mAbsListView;
        mAbsListView.setBackgroundDrawable(null);
        mAbsListView.setCacheColorHint(Color.TRANSPARENT);
        mAbsListView.setHorizontalFadingEdgeEnabled(false);
        mAbsListView.setVerticalFadingEdgeEnabled(false);
    }

    public void initHeadView() {
        headView = new XListViewHeader(mAbsListView.getContext());
        Lib_Util_Widget.measureView(headView);
        headContentHeight = headView.getMeasuredHeight();
        headView.setPadding(0, -headContentHeight, 0, 0);
        ((ListView) mAbsListView).addHeaderView(headView);
    }

    private enum HeadStatus {
        RELEASE_To_REFRESH, // 松开刷新
        PULL_To_REFRESH, // 下拉刷新
        REFRESHING, // 正在刷新
        DONE // 默认

    }

    private enum FootStatus {
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
    private int headContentHeight;
    private int startY;
    /**
     * 控制滑动下拉惯性 启动下拉刷新
     */
    private boolean isUserTouch;
    private boolean isBack;

    public void onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isUserTouch = true;
                if (mAbsListView.getFirstVisiblePosition() == 0 && !isRecored) {
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
                        //TODO
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

                    if (!isRecored && mAbsListView.getFirstVisiblePosition() == 0) {
                        // Log.v(TAG, "在move时候记录下位置");
                        isRecored = true;
                        startY = tempY;
                    }
                    if (headStatus != HeadStatus.REFRESHING && isRecored
                            && footStatus != FootStatus.LOADING) {

                        // 保证在设置padding的过程中，当前的位置一直是在head，否则如果当列表超出屏幕的话，当在上推的时候，列表会同时进行滚动

                        // 可以松手去刷新了
                        if (headStatus == HeadStatus.RELEASE_To_REFRESH) {
                            mAbsListView.setSelection(0);
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
                            mAbsListView.setSelection(0);
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
                            headView.setPadding(0, -1 * headContentHeight
                                    + (tempY - startY) / RATIO, 0, 0);
                        }

                        // 更新headView的paddingTop
                        if (headStatus == HeadStatus.RELEASE_To_REFRESH) {
                            headView.setPadding(0, (tempY - startY) / RATIO
                                    - headContentHeight, 0, 0);
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
    }

    private void changeHeaderViewByState(HeadStatus state) {
        this.headStatus = state;
        switch (state) {
            case RELEASE_To_REFRESH:
                headView.onDownReleaseToRefresh();
                // Log.v(TAG, "当前状态，松开刷新");
                break;
            case PULL_To_REFRESH:
                // 是由RELEASE_To_REFRESH状态转变来的
                if (isBack) {
                    headView.onDownPullToRefresh(isBack);
                    isBack = false;
                } else {
                    headView.onDownPullToRefresh(isBack);
                }
                // Log.v(TAG, "当前状态，下拉刷新");
                break;

            case REFRESHING:
                headView.setPadding(0, 0, 0, 0);
                headView.onDownToRefreshing();
                // Log.v(TAG, "当前状态,正在刷新...");
                break;
            case DONE:
                headView.onDoneToRefresh();
                headView.setPadding(0, -1 * headContentHeight, 0, 0);
                // Log.v(TAG, "当前状态，done");
                break;
        }
    }
}
