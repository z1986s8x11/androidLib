package com.zsx.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ListView;

import com.zsx.R;

/**
 * @author zhusx
 * @date 2015-3-10
 * @descripton 由于ListView 嵌套ScrollView 中 高度显示有问题,全部显示的
 */
public class Lib_Widget_ExpandListView extends ListView {
    /**
     * 是否展开
     */
    private boolean isExpand = true;

    public Lib_Widget_ExpandListView(Context context) {
        super(context);
        init(context, null);
    }

    public Lib_Widget_ExpandListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public Lib_Widget_ExpandListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Lib_AbsListView);
        isExpand = typedArray.getBoolean(R.styleable.Lib_AbsListView_isExpand, true);
        typedArray.recycle();
    }

    /**
     * 由于ListView和GridView都是可滑动的控件. 所以需要自定义GridView,重写其onMeasure()方法.
     * 在该方法中使GridView的高为wrap_content的大小,否则GridView中 的内容只能显示很小一部分
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightSpec = heightMeasureSpec;
        if (isExpand) {
            heightSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        }
        super.onMeasure(widthMeasureSpec, heightSpec);
    }

    /**
     * @param isExpand 是否展开
     */
    public void _setExpand(boolean isExpand) {
        this.isExpand = isExpand;
        invalidate();
    }
}
