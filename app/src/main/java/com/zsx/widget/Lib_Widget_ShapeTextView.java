package com.zsx.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

import com.zsx.tools.Lib_ShapeHelper;

/**
 * Created by Administrator on 2015/12/22.
 */
public class Lib_Widget_ShapeTextView extends TextView {

    public Lib_Widget_ShapeTextView(Context context) {
        super(context);
        init(context, null);
    }

    public Lib_Widget_ShapeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public Lib_Widget_ShapeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public Lib_Widget_ShapeTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        Lib_ShapeHelper.init(this, context, attrs);
    }
}
