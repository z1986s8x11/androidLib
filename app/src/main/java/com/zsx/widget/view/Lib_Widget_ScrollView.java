package com.zsx.widget.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Author       zhusx
 * Email        327270607@qq.com
 * Created      2016/1/29.17:26
 */
public class Lib_Widget_ScrollView extends ScrollView {
    private OnScrollChangedListener listener;

    public Lib_Widget_ScrollView(Context context) {
        super(context);
    }

    public Lib_Widget_ScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Lib_Widget_ScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public Lib_Widget_ScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        if (listener != null) {
            listener.onScrollChanged(l, t, oldl, oldt);
        }
        super.onScrollChanged(l, t, oldl, oldt);
    }

    public void _setOnScrollChangedListener(OnScrollChangedListener listener) {
        this.listener = listener;
    }

    public interface OnScrollChangedListener {
        void onScrollChanged(int x, int y, int oldX, int oldY);
    }
}
