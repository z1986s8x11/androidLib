package com.zsx.tools;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;

import com.zsx.itf.Lib_EmptyViewInterface;
import com.zsx.network.Lib_BaseHttpRequestData;
import com.zsx.network.Lib_HttpRequest;
import com.zsx.network.Lib_HttpResult;
import com.zsx.network.Lib_OnHttpLoadingListener;

/**
 * 用于需要网络请求时,显示Loading页面
 * Created by zhusx on 2015/9/25.
 */
public abstract class Lib_LoadingHelper<I, M, O> implements Lib_OnHttpLoadingListener<I, Lib_HttpResult<M>, O> {
    private View resLayout;
    private Lib_EmptyViewInterface pEmptyView;
    private Lib_BaseHttpRequestData<I, Lib_HttpResult<M>, O> pLoadData;
    private boolean isSuccess = false;

    public <T extends View & Lib_EmptyViewInterface> Lib_LoadingHelper(View resLayout, T emptyView, Lib_BaseHttpRequestData<I, Lib_HttpResult<M>, O> loadData) {
        this.resLayout = resLayout;
        this.pLoadData = loadData;
        this.pEmptyView = emptyView;
        ViewParent parent = resLayout.getParent();
        if (parent == null) {
            throw new IllegalStateException("resLayout 不能为null");
        }
        if (parent.getParent() instanceof ViewGroup) {
            ViewGroup.LayoutParams lp = resLayout.getLayoutParams();
            LinearLayout parentLayout = new LinearLayout(resLayout.getContext());
            parentLayout.setLayoutParams(lp);
            parentLayout.setOrientation(LinearLayout.VERTICAL);
            parentLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            ViewGroup group = (ViewGroup) parent;
            int index = group.indexOfChild(resLayout);
            group.removeView(resLayout);
            group.addView(parentLayout, index, lp);
            parentLayout.addView(resLayout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            parentLayout.addView(emptyView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            resLayout.setVisibility(View.GONE);
            parentLayout.invalidate();
        } else {
            throw new IllegalStateException("resLayout.getParent() 必须是ViewGroup!!");
        }
    }

    /**
     * 用于重新加载
     */
    protected void reLoad() {
        pLoadData._reLoadData();
    }

    @Override
    public void onLoadStart(I i) {
        if (!isSuccess) {
            return;
        }
        pEmptyView.showLoadingView();
    }

    @Override
    public void onLoadError(I i, Lib_HttpRequest<O> requestData, Lib_HttpResult<M> mLib_httpResult, boolean isAPIError, String error_message) {
        if (!isSuccess) {
            return;
        }
        pEmptyView.showLoadErrorView(error_message);
    }

    @Override
    public void onLoadComplete(I i, Lib_HttpRequest<O> requestData, Lib_HttpResult<M> b) {
        if (!isSuccess) {
            return;
        }
        isSuccess = true;
        pEmptyView.showLoadComplete();
        resLayout.setVisibility(View.VISIBLE);
        onSuccess(requestData, b);
    }

    public abstract void onSuccess(Lib_HttpRequest<O> request, Lib_HttpResult<M> result);

}
