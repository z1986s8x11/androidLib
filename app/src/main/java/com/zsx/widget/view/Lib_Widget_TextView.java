package com.zsx.widget.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

import com.zsx.tools.Lib_ShapeHelper;

/**
 * <attr name="bottomLeftRadius" format="dimension" />
 * <attr name="bottomRightRadius" format="dimension" />
 * <attr name="radius" format="dimension" />
 * <attr name="topLeftRadius" format="dimension" />
 * <attr name="topRightRadius" format="dimension" />
 * <attr name="solidColor" format="color" />
 * <attr name="solidColor2" format="color" />
 * <attr name="strokeColor" format="color" />
 * <attr name="strokeColor2" format="color" />
 * <attr name="strokeDashWidth" format="dimension" />
 * <attr name="strokeDashGap" format="dimension" />
 * <attr name="strokeWidth" format="dimension" />
 * <attr name="gradientStartColor" format="color" />
 * <attr name="gradientCenterColor" format="color" />
 * <attr name="gradientEndColor" format="color" />
 * <attr name="status">
 * <enum name="pressed" value="0" />
 * <enum name="enabled" value="1" />
 * <enum name="checked" value="2" />
 * <enum name="selected" value="3" />
 * </attr>
 * <attr name="textColorStatus">
 * <enum name="pressed" value="0" />
 * <enum name="enabled" value="1" />
 * <enum name="checked" value="2" />
 * <enum name="selected" value="3" />
 * </attr>
 * <attr name="textColor2" format="color" />
 * <p/>
 * Created by Administrator on 2015/12/22.
 */
public class Lib_Widget_TextView extends TextView {

    public Lib_Widget_TextView(Context context) {
        super(context);
        init(context, null);
    }

    public Lib_Widget_TextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public Lib_Widget_TextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public Lib_Widget_TextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        Lib_ShapeHelper.initBackground(this, context, attrs);
        Lib_ShapeHelper.initTextColor(this, context, attrs);
    }
}
