package com.zsx.widget.v7;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Author       zhusx
 * Email        327270607@qq.com
 * Created      2016/4/12 9:42
 */
public class Lib_BaseRecyclerView extends RecyclerView {
    public Lib_BaseRecyclerView(Context context) {
        super(context);
    }

    public Lib_BaseRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Lib_BaseRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 添加分割线
     */
    public void _setDefaultItemDecoration() {
        addItemDecoration(new Lib_BaseRecyclerViewItemDecoration(getContext()));
    }

    /**
     * 设置Item增加、移除动画
     */
    public void _setDefaultItemAnimator() {
        setItemAnimator(new DefaultItemAnimator());
    }
}
