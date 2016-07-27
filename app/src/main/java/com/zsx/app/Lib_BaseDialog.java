package com.zsx.app;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.ColorInt;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * Author       zhusx
 * Email        327270607@qq.com
 * Created      2016/3/3 17:56
 */
public class Lib_BaseDialog extends Dialog {
    public Lib_BaseDialog(Context context) {
        super(context);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);  去掉标题栏
    }

    public Lib_BaseDialog(Context context, int theme) {
        super(context, theme);
    }

    protected Lib_BaseDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    /**
     * 设置背景 可以除去显示时的padding
     */
    public void _setBackgroundColor(@ColorInt int colorValue) {
        Window win = getWindow();
        if (win != null) {
            getWindow().setBackgroundDrawable(new ColorDrawable(colorValue));
        }
    }

    public void _setGravity(int gravity) {
        Window win = getWindow();
        if (win != null) {
            WindowManager.LayoutParams lp = win.getAttributes();
            lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
            lp.gravity = gravity;
            win.setAttributes(lp);
        }
    }
}
