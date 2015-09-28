package com.zsx.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * 正方向
 * Created by zhusx on 2015/9/28.
 */
public class Lib_Widget_SquareRelativeLayout extends RelativeLayout {
    public Lib_Widget_SquareRelativeLayout(Context context) {
        super(context);
    }

    public Lib_Widget_SquareRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Lib_Widget_SquareRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));
        int childWidthSize = getMeasuredWidth();
        heightMeasureSpec = widthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthSize, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
