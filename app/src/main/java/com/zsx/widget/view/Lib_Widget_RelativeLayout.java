package com.zsx.widget.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.zsx.tools.Lib_ShapeHelper;

/**
 * Created by zhusx on 2015/9/28.
 */
public class Lib_Widget_RelativeLayout extends RelativeLayout {
    private boolean isSquare; //是否正方形

    public Lib_Widget_RelativeLayout(Context context) {
        super(context);
        init(context, null);
    }

    public Lib_Widget_RelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public Lib_Widget_RelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    protected void _setSquare(boolean isSquare) {
        this.isSquare = isSquare;
        invalidate();
    }

    private void init(Context context, AttributeSet attrs) {
        Lib_ShapeHelper.initBackground(this, context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (isSquare) {
            setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));
            int childWidthSize = getMeasuredWidth();
            heightMeasureSpec = widthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthSize, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
