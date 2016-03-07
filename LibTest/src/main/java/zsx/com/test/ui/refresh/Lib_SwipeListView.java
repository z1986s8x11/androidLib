package zsx.com.test.ui.refresh;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ListView;

import com.zsx.adapter.Lib_BaseAdapter;
import com.zsx.network.Lib_BaseHttpRequestData;
import com.zsx.network.Lib_HttpRequest;
import com.zsx.network.Lib_HttpResult;
import com.zsx.network.Lib_OnHttpLoadingListener;

/**
 * Author       zhusx
 * Email        327270607@qq.com
 * Created      2016/3/7 10:13
 */
public abstract class Lib_SwipeListView<Id, T extends IAutoLoadMore, Parameter> extends ListView implements Lib_OnHttpLoadingListener<Id, Lib_HttpResult<T>, Parameter> {
    private SwipeRefreshLayout swipeRefreshLayout;
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
        if (isRefresh) {
            if (swipeRefreshLayout == null) {
                ViewParent parent = getParent();
                if (parent == null) {
                    throw new NullPointerException(" 必须有父容器");
                }
                if (!(parent instanceof ViewGroup)) {
                    throw new IllegalArgumentException("parent must is ViewGroup");
                }
                ViewGroup viewGroup = (ViewGroup) parent;
                int index = 0;
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    if (viewGroup.getChildAt(i) == this) {
                        index = i;
                        break;
                    }
                }
                viewGroup.removeView(this);
                ViewGroup.LayoutParams lp = getLayoutParams();
                swipeRefreshLayout = new SwipeRefreshLayout(getContext());
                swipeRefreshLayout.addView(this, new SwipeRefreshLayout.LayoutParams(SwipeRefreshLayout.LayoutParams.MATCH_PARENT, SwipeRefreshLayout.LayoutParams.MATCH_PARENT));
                viewGroup.addView(swipeRefreshLayout, index, lp);
                swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        if (mLoadData._isLoading()) {
                            swipeRefreshLayout.setRefreshing(false);
                            return;
                        }
                        if (readDataListener != null) {
                            readDataListener.readData(true, 0);
                        } else {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                });
            }
        }
        if (isLoadMore) {
            this.isLoadMoreData = isLoadMore;
        }
    }

    public interface OnReadDataListener {
        void readData(boolean isRefresh, int position);
    }

    public void _setRefresh(boolean refreshing) {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(refreshing);
        }
    }

    protected IAutoView __initFootView() {
        return null;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (!isLoadMoreData) {
            return super.onTouchEvent(ev);
        }
        switch (ev.getAction() & ev.getActionMasked()) {
            case MotionEvent.ACTION_UP:
                if (!isLoading()) {
                    if (mLoadData != null && readDataListener != null) {
                        if (getCount() == getLastVisiblePosition() + 1) {
                            if (mLoadData._hasCache()) {
                                IAutoLoadMore data = mLoadData._getLastData().getData();
                                if (data.hasMoreData()) {
                                    if (!mLoadData._isLoading()) {
                                        readDataListener.readData(false, position + 1);
                                    }
                                }
                            }
                        }
                    }
                }
                break;
        }
        return super.onTouchEvent(ev);
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
            if (swipeRefreshLayout != null) {
                swipeRefreshLayout.setRefreshing(false);
            }
            if (adapter != null) {
                adapter._setItemsToUpdate(result.getData().getList());
            }
        } else {
            position++;
            if (adapter != null) {
                adapter._addItemToUpdate(result.getData().getList());
            }
            if (footView != null) {
                if (!result.getData().hasMoreData()) {
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
        if (swipeRefreshLayout != null) {
            if (swipeRefreshLayout.isRefreshing()) {
                return true;
            }
        }
        return false;
    }
}
