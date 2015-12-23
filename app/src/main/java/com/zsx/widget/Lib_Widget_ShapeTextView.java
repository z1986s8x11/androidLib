package com.zsx.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

import com.zsx.R;

/**
 * Created by Administrator on 2015/12/22.
 */
public class Lib_Widget_ShapeTextView extends TextView {
    /**
     * <selector xmlns:android="http://schemas.android.com/apk/res/android">
     * <item android:state_enabled="true">
     * <shape>
     * <corners android:bottomLeftRadius="" android:bottomRightRadius="" android:radius="" android:topLeftRadius="" android:topRightRadius=""/>
     * <gradient android:angle="" android:centerColor="" android:centerX="" android:centerY="" android:endColor=""
     * android:gradientRadius="" android:startColor="" android:type="" android:useLevel=""/>
     * <padding android:bottom="" android:left="" android:right="" android:top=""/>
     * <size android:width="" android:height=""/>
     * <solid android:color=""/>
     * <stroke android:color="" android:dashGap="" android:dashWidth="" android:width=""/>
     * </shape>
     * </item>
     * </selector>
     *
     * @param context
     */
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
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Lib_ShapeTextView);
        GradientDrawable gradientDrawable = new GradientDrawable();
        GradientDrawable gradientDrawable2 = null;
        int status = typedArray.getInt(R.styleable.Lib_ShapeTextView_status, -1);
        if (status != -1) {
            /**
             <enum name="pressed" value="0" />
             <enum name="enabled" value="1" />
             <enum name="checked" value="2" />
             <enum name="selected" value="3" />
             */
            switch (status) {
                case 0:
                    status = android.R.attr.state_pressed;
                    break;
                case 1:
                    status = android.R.attr.state_enabled;
                    break;
                case 2:
                    status = android.R.attr.state_checked;
                    break;
                case 3:
                    status = android.R.attr.state_selected;
                    break;
                default:
                    status = -1;
                    break;
            }
        }
        if (status != -1) {
            gradientDrawable2 = new GradientDrawable();
        }
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        if (gradientDrawable2 != null) {
            gradientDrawable2.setShape(GradientDrawable.RECTANGLE);
        }
        int radius = typedArray.getDimensionPixelSize(R.styleable.Lib_ShapeTextView_radius, 0);
        if (radius != 0) {
            gradientDrawable.setCornerRadius(radius);
            if (gradientDrawable2 != null) {
                gradientDrawable2.setCornerRadius(radius);
            }
        } else {
            int bottomLeftRadius = typedArray.getDimensionPixelSize(R.styleable.Lib_ShapeTextView_bottomLeftRadius, 0);
            int bottomRightRadius = typedArray.getDimensionPixelSize(R.styleable.Lib_ShapeTextView_bottomRightRadius, 0);
            int topLeftRadius = typedArray.getDimensionPixelSize(R.styleable.Lib_ShapeTextView_topLeftRadius, 0);
            int topRightRadius = typedArray.getDimensionPixelSize(R.styleable.Lib_ShapeTextView_topRightRadius, 0);
            //1、2两个参数表示左上角，3、4表示右上角，5、6表示右下角，7、8表示左下角
            gradientDrawable.setCornerRadii(new float[]{topLeftRadius, topLeftRadius, topRightRadius, topRightRadius, bottomRightRadius, bottomRightRadius, bottomLeftRadius, bottomLeftRadius});
            if (gradientDrawable2 != null) {
                gradientDrawable2.setCornerRadii(new float[]{topLeftRadius, topLeftRadius, topRightRadius, topRightRadius, bottomRightRadius, bottomRightRadius, bottomLeftRadius, bottomLeftRadius});
            }
        }
        int strokeColor = typedArray.getColor(R.styleable.Lib_ShapeTextView_strokeColor, Color.GRAY);
        int strokeDashGap = typedArray.getDimensionPixelSize(R.styleable.Lib_ShapeTextView_strokeDashGap, 0);
        int strokeWidth = typedArray.getDimensionPixelSize(R.styleable.Lib_ShapeTextView_strokeWidth, -1);
        int strokeDashWidth = typedArray.getDimensionPixelSize(R.styleable.Lib_ShapeTextView_strokeDashWidth, 0);
        if (strokeWidth > 0) {
            gradientDrawable.setStroke(strokeWidth, strokeColor, strokeDashWidth, strokeDashGap);
            if (gradientDrawable2 != null) {
                int strokeColor2 = typedArray.getColor(R.styleable.Lib_ShapeTextView_strokeColor2, strokeColor);
                gradientDrawable2.setStroke(strokeWidth, strokeColor2, strokeDashWidth, strokeDashGap);
            }
        }
        int gradientStartColor = typedArray.getColor(R.styleable.Lib_ShapeTextView_gradientStartColor, -1);
        int gradientEndColor = typedArray.getColor(R.styleable.Lib_ShapeTextView_gradientEndColor, -1);
        if (gradientStartColor != -1 && gradientEndColor != -1) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                int gradientCenterColor = typedArray.getColor(R.styleable.Lib_ShapeTextView_gradientCenterColor, -1);
                if (gradientCenterColor == -1) {
                    gradientDrawable.setColors(new int[]{gradientStartColor, gradientEndColor});
                    if (gradientDrawable2 != null) {
                        gradientDrawable2.setColors(new int[]{gradientStartColor, gradientEndColor});
                    }
                } else {
                    gradientDrawable.setColors(new int[]{gradientStartColor, gradientCenterColor, gradientEndColor});
                    if (gradientDrawable2 != null) {
                        gradientDrawable2.setColors(new int[]{gradientStartColor, gradientCenterColor, gradientEndColor});
                    }
                }
            } else {
                int solidColor = typedArray.getColor(R.styleable.Lib_ShapeTextView_solidColor, -1);
                if (solidColor != -1) {
                    gradientDrawable.setColor(solidColor);
                    if (gradientDrawable2 != null) {
                        int solidColor2 = typedArray.getColor(R.styleable.Lib_ShapeTextView_solidColor2, solidColor);
                        gradientDrawable2.setColor(solidColor2);
                    }
                } else {
                    gradientDrawable.setColor(gradientStartColor);
                    if (gradientDrawable2 != null) {
                        gradientDrawable2.setColor(gradientStartColor);
                    }
                }
            }
        } else {
            int solidColor = typedArray.getColor(R.styleable.Lib_ShapeTextView_solidColor, -1);
            if (solidColor != -1) {
                gradientDrawable.setColor(solidColor);
                if (gradientDrawable2 != null) {
                    int solidColor2 = typedArray.getColor(R.styleable.Lib_ShapeTextView_solidColor2, solidColor);
                    gradientDrawable2.setColor(solidColor2);
                }
            }
        }
        int textColorStatus = typedArray.getInt(R.styleable.Lib_ShapeTextView_textColorStatus, -1);
        if (textColorStatus != -1) {
            /**
             <enum name="pressed" value="0" />
             <enum name="enabled" value="1" />
             <enum name="checked" value="2" />
             <enum name="selected" value="3" />
             */
            switch (textColorStatus) {
                case 0:
                    textColorStatus = android.R.attr.state_pressed;
                    break;
                case 1:
                    textColorStatus = android.R.attr.state_enabled;
                    break;
                case 2:
                    textColorStatus = android.R.attr.state_checked;
                    break;
                case 3:
                    textColorStatus = android.R.attr.state_selected;
                    break;
                default:
                    textColorStatus = -1;
                    break;
            }
        }
        typedArray.recycle();
        if (textColorStatus != -1) {
            int textColor = getTextColors().getDefaultColor();
            int textColor2 = typedArray.getColor(R.styleable.Lib_ShapeTextView_textColor2, -1);
            if (textColor2 != -1) {
                ColorStateList colorStateList = new ColorStateList(new int[][]{new int[]{textColorStatus}, new int[]{-textColorStatus}, new int[]{}}, new int[]{textColor2, textColor, textColor});
                setTextColor(colorStateList);
            }
        }
        if (gradientDrawable2 != null) {
            StateListDrawable stateListDrawable = new StateListDrawable();
            stateListDrawable.addState(new int[]{status}, gradientDrawable);
            stateListDrawable.addState(new int[]{-status}, gradientDrawable2);
            stateListDrawable.addState(new int[]{}, gradientDrawable);
            _setBackgroundDrawable(stateListDrawable);
        } else {
            _setBackgroundDrawable(gradientDrawable);
        }
    }

    private void _setBackgroundDrawable(Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(drawable);
        } else {
            setBackgroundDrawable(drawable);
        }
    }

    private GradientDrawable.Orientation getOrientation(int gradientOrientation) {
//        gradientDrawable.setOrientation(getOrientation(gradientOrientation));
        GradientDrawable.Orientation orientation = null;
        switch (gradientOrientation) {
            case 0:
                orientation = GradientDrawable.Orientation.BL_TR;
                break;
            case 1:
                orientation = GradientDrawable.Orientation.BOTTOM_TOP;
                break;
            case 2:
                orientation = GradientDrawable.Orientation.BR_TL;
                break;
            case 3:
                orientation = GradientDrawable.Orientation.LEFT_RIGHT;
                break;
            case 4:
                orientation = GradientDrawable.Orientation.RIGHT_LEFT;
                break;
            case 5:
                orientation = GradientDrawable.Orientation.TL_BR;
                break;
            case 6:
                orientation = GradientDrawable.Orientation.TOP_BOTTOM;
                break;
            case 7:
                orientation = GradientDrawable.Orientation.TR_BL;
                break;
        }
        return orientation;
    }
}
