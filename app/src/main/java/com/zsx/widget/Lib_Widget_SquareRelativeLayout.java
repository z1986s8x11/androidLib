package com.zsx.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by zhusx on 2015/9/28.
 */
public class Lib_Widget_SquareRelativeLayout extends RelativeLayout {
    private boolean isSquare = true; //是否正方向

    public Lib_Widget_SquareRelativeLayout(Context context) {
        super(context);
    }

    public Lib_Widget_SquareRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Lib_Widget_SquareRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected void _setSquare(boolean isSquare) {
        this.isSquare = isSquare;
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
