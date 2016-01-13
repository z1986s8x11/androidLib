package zsx.com.test.ui.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.zsx.widget.XListViewHeader;


/**
 * 下拉刷新ListView
 *
 * @description onRefreshComplete() Activity中调用, 通知ListView 刷新完成<br/>
 */
public class AutoListView extends Lib_AutoListView {
    XListViewHeader headView;

    public AutoListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected View initHeadView() {
        headView = new XListViewHeader(getContext());
        addHeaderView(headView);
        return headView;
    }

    @Override
    protected void moveToReversal(View v, boolean isBack) {
        headView.onDownPullToRefresh(isBack);
    }

    @Override
    protected void moveToRefresh(View v) {
        headView.onDownReleaseToRefresh();
    }

    @Override
    protected void upToRefresh(View v) {
        headView.setPadding(0, 0, 0, 0);
        headView.onDownToRefreshing();
    }

    @Override
    protected void defaultStatus(View v) {
        this.headView.onDoneToRefresh();
        headView.setPadding(0, -1 * headContentHeight, 0, 0);
    }

    @Override
    protected void scrollToRefresh(View headView, int offset, boolean isTop) {
        headView.setPadding(0, offset, 0, 0);
    }
}
