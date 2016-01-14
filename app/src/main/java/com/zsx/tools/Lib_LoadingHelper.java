package com.zsx.tools;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.zsx.network.Lib_HttpRequest;
import com.zsx.network.Lib_HttpResult;
import com.zsx.network.Lib_OnHttpLoadingListener;

/**
 * 用于需要网络请求时,显示Loading页面
 * Created by zhusx on 2015/9/25.
 */
public abstract class Lib_LoadingHelper<Id, Result, Parameter> implements Lib_OnHttpLoadingListener<Id, Lib_HttpResult<Result>, Parameter> {
    private ViewGroup helperLayout;
    private View loadingView;
    private View errorView;
    private boolean isSuccess = false;
    private View resLayout;

    public Lib_LoadingHelper(View resLayout) {
        this.resLayout = resLayout;
        ViewParent parent = resLayout.getParent();
        if (parent == null) {
            throw new IllegalStateException("resLayout 不能为null");
        }
        if (parent.getParent() instanceof ViewGroup) {
            ViewGroup.LayoutParams lp = resLayout.getLayoutParams();
            LinearLayout parentLayout = new LinearLayout(resLayout.getContext());
            parentLayout.setOrientation(LinearLayout.VERTICAL);
            parentLayout.setLayoutParams(lp);
            ViewGroup group = (ViewGroup) parent;
            int index = group.indexOfChild(resLayout);
            group.removeView(resLayout);
            group.addView(parentLayout, index, lp);
            parentLayout.addView(resLayout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            parentLayout.addView(helperLayout = new FrameLayout(resLayout.getContext()), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            resLayout.setVisibility(View.GONE);
            parentLayout.invalidate();
        } else {
            throw new IllegalStateException("resLayout.getParent() 必须是ViewGroup!!");
        }
    }

    public final void _setLoadingView(View child) {
        if (loadingView != null) {
            helperLayout.removeView(loadingView);
        }
        if (child.getParent() != null) {
            ((ViewGroup) child.getParent()).removeView(child);
        }
        helperLayout.addView(child, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        child.setVisibility(View.GONE);
        loadingView = child;
    }

    public final void _setErrorView(View child) {
        if (errorView != null) {
            helperLayout.removeView(errorView);
        }
        if (child.getParent() != null) {
            ((ViewGroup) child.getParent()).removeView(child);
        }
        helperLayout.addView(child, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        child.setVisibility(View.GONE);
        errorView = child;
    }

    public void __onError(View errorView, Lib_HttpRequest<Parameter> request, Lib_HttpResult<Result> data, boolean isAPIError, String error_message) {
    }

    public abstract void __onComplete(Lib_HttpRequest<Parameter> request, Lib_HttpResult<Result> data);

    @Override
    public final void onLoadStart(Id id, Lib_HttpRequest<Parameter> request) {
        if (request.isRefresh || !isSuccess) {
            loadingView.setVisibility(View.VISIBLE);
            if (resLayout.getVisibility() == View.VISIBLE) {
                resLayout.setVisibility(View.GONE);
            }
            if (errorView != null && errorView.getVisibility() == View.VISIBLE) {
                errorView.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public final void onLoadError(Id id, Lib_HttpRequest<Parameter> request, Lib_HttpResult<Result> data, boolean isAPIError, String error_message) {
        if (request.isRefresh || !isSuccess) {
            if (loadingView != null) {
                loadingView.setVisibility(View.GONE);
            }
            if (errorView != null) {
                errorView.setVisibility(View.VISIBLE);
            }
        }
        __onError(errorView, request, data, isAPIError, error_message);
    }

    @Override
    public final void onLoadComplete(Id id, Lib_HttpRequest<Parameter> request, Lib_HttpResult<Result> data) {
        if (request.isRefresh || !isSuccess) {
            if (loadingView != null) {
                loadingView.setVisibility(View.GONE);
            }
            resLayout.setVisibility(View.VISIBLE);
        }
        isSuccess = true;
        __onComplete(request, data);
    }
}
