package com.zsx.app;

import android.app.Dialog;
import android.content.Context;
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
    }

    public Lib_BaseDialog(Context context, int theme) {
        super(context, theme);
    }

    protected Lib_BaseDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public void _setGravity(int gravity) {
        Window win = getWindow();
        if (win != null) {
            WindowManager.LayoutParams params = win.getAttributes();
            if (params != null) {
                params.gravity = gravity;
                win.setAttributes(params);
            }
        }
    }
}
