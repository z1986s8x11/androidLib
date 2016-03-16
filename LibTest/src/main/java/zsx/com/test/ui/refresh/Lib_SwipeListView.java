package zsx.com.test.ui.refresh;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.AbsListView;
import android.widget.ListView;

import com.zsx.adapter.Lib_BaseAdapter;
import com.zsx.debug.LogUtil;
import com.zsx.network.Lib_BaseHttpRequestData;
import com.zsx.network.Lib_HttpRequest;
import com.zsx.network.Lib_HttpResult;
import com.zsx.network.Lib_OnHttpLoadingListener;

/**
 * Author       zhusx
 * Email        327270607@qq.com
 * Created      2016/3/7 10:13
 */
public abstract class Lib_SwipeListView<Id, T extends IAutoLoadMore, Parameter> extends ListView implements Lib_OnHttpLoadingListener<Id, Lib_HttpResult<T>, Parameter>, AbsListView.OnScrollListener {
    private Lib_BaseHttpRequestData<Id, T, Parameter> mLoadData;
    private int position = 0;
    private OnReadDataListener readDataListener;
    private Lib_BaseAdapter<Object> adapter;
    private boolean isLoadMoreData;
    private IAutoView footView;

    public Lib_SwipeListView(Context context) {
        super(context);
        init();
    }

    public Lib_SwipeListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Lib_SwipeListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public Lib_SwipeListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setCacheColorHint(Color.TRANSPARENT);
        setHorizontalFadingEdgeEnabled(false);
        setVerticalFadingEdgeEnabled(false);
    }

    public void _setLoadData(Lib_BaseHttpRequestData<Id, T, Parameter> loadData, OnReadDataListener listener, boolean isRefresh, boolean isLoadMore) {
        this.mLoadData = loadData;
        this.readDataListener = listener;
        mLoadData._addOnLoadingListener(this);
        if (isLoadMore) {
            this.isLoadMoreData = isLoadMore;
        }
        setOnScrollListener(this);
    }

    boolean mLastItemVisible;

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        LogUtil.e(this, "====" + String.valueOf(mLastItemVisible));
        if (scrollState == OnScrollListener.SCROLL_STATE_IDLE && mLastItemVisible) {
            LogUtil.e(this, "==1==");
            if (!isLoading()) {
                LogUtil.e(this, "==2==");
                if (mLoadData != null && readDataListener != null) {
                    LogUtil.e(this, "==3==");
                    if (getCount() == getLastVisiblePosition() + 1) {
                        LogUtil.e(this, "==4==");
//                        if (mLoadData._hasCache()) {
//                            IAutoLoadMore data = mLoadData._getLastData().getData();
//                            if (data.hasMoreData()) {
                        if (!mLoadData._isLoading()) {
                            readDataListener.readData(false, position + 1);
                        }
//                            }
//                        }
                    }
                }
            }
        }

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        mLastItemVisible = (totalItemCount > 0) && (firstVisibleItem + visibleItemCount >= totalItemCount - 1);
    }

    public interface OnReadDataListener {
        void readData(boolean isRefresh, int position);
    }

    protected IAutoView __initFootView() {
        return null;
    }

    @Override
    public void onLoadStart(Id id, Lib_HttpRequest<Parameter> request) {
        if (!request.isRefresh) {
            if (footView != null) {
                footView.startLoad();
            }
        }
    }

    @Override
    public void onLoadError(Id id, Lib_HttpRequest<Parameter> request, Lib_HttpResult<T> result, boolean isAPIError, String error_message) {
        if (!request.isRefresh) {
            if (footView != null) {
                footView.loadError(error_message);
            }
        }
    }

    @Override
    public void onLoadComplete(Id id, Lib_HttpRequest<Parameter> request, Lib_HttpResult<T> result) {
        if (request.isRefresh) {
            position = 0;
            if (adapter != null) {
                adapter._setItemsToUpdate(result.getData().getList());
            }
        } else {
            position++;
            if (adapter != null) {
                adapter._addItemToUpdate(result.getData().getList());
            }
            if (footView != null) {
                if (result.getData().hasMoreData(result.getCurrentCount())) {
                    footView.noMoreData();
                } else {
                    footView.reset();
                }
            }
        }
    }

    public void _setAdapter(Lib_BaseAdapter adapter) {
        this.adapter = adapter;
        if (footView == null) {
            footView = __initFootView();
            if (footView != null) {
                addFooterView(footView.getView());
            }
        }
        super.setAdapter(adapter);
    }

    private boolean isLoading() {
        if (mLoadData != null) {
            if (mLoadData._isLoading()) {
                return true;
            }
        }
        return false;
    }
}
