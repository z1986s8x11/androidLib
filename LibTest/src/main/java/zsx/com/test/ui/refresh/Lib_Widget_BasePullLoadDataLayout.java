package zsx.com.test.ui.refresh;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.zsx.network.Lib_BaseHttpRequestData;
import com.zsx.network.Lib_HttpRequest;
import com.zsx.network.Lib_HttpResult;
import com.zsx.network.Lib_OnHttpLoadingListener;

/**
 * Author       zhusx
 * Email        327270607@qq.com
 * Created      2016/4/22 11:24
 */
public abstract class Lib_Widget_BasePullLoadDataLayout<Id, Result, Parameter> extends Lib_Widget_BasePullLayout implements Lib_OnHttpLoadingListener<Id, Lib_HttpResult<Result>, Parameter> {
    private Lib_BaseHttpRequestData loadData;

    public Lib_Widget_BasePullLoadDataLayout(Context context) {
        super(context);
    }

    public Lib_Widget_BasePullLoadDataLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Lib_Widget_BasePullLoadDataLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public Lib_Widget_BasePullLoadDataLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    public void init(Lib_BaseHttpRequestData loadData) {
        this.loadData = loadData;
        loadData._addOnLoadingListener(this);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (loadData == null || loadData._getRequestParams() == null) {
            return super.onInterceptTouchEvent(ev);
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (loadData == null || loadData._getRequestParams() == null) {
            return super.onTouchEvent(ev);
        }
        return super.onTouchEvent(ev);
    }

    @Override
    protected void __refreshData() {
        loadData._reLoadData(true);
    }

    @Override
    public boolean isLoading() {
        if (loadData == null) {
            return false;
        }
        return loadData._isLoading();
    }

    @Override
    public void onLoadStart(Id o, Lib_HttpRequest<Parameter> request) {
        if (request.isRefresh) {
            __start(mHeadView, mHeadViewHeight);
        } else {

        }
    }

    @Override
    public void onLoadError(Id o, Lib_HttpRequest<Parameter> request, Lib_HttpResult<Result> o2, boolean b, String errorMessage) {
        if (request.isRefresh) {
            __reset(mHeadView, mHeadViewHeight, TYPE_ERROR, errorMessage);
        } else {

        }
    }

    @Override
    public void onLoadComplete(Id o, Lib_HttpRequest<Parameter> request, Lib_HttpResult<Result> o2) {
        if (request.isRefresh) {
            __reset(mHeadView, mHeadViewHeight, TYPE_COMPLETE, null);
        } else {

        }
        __bindData(request.isRefresh, o2.getData());
    }

    protected abstract void __bindData(boolean isRefresh, Result data);
}
