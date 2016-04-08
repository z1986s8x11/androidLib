package com.zsx.tools;

import android.app.Service;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

/**
 * Author       zhusx
 * Email        327270607@qq.com
 * Created      2016/4/8 14:03
 */
public class Lib_WindowsHelper {
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams params;

    public Lib_WindowsHelper(Context context) {
        mWindowManager = (WindowManager) context.getSystemService(Service.WINDOW_SERVICE);
        params = new WindowManager.LayoutParams();
        params.type = WindowManager.LayoutParams.TYPE_PHONE;
        // params.format = PixelFormat.RGBA_8888;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        __init(params);
    }

    protected void __init(WindowManager.LayoutParams lp) {
        lp.gravity = Gravity.TOP;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
    }

    public void _show(View showView) {
        mWindowManager.addView(showView, params);
    }

    public void _hide(View showView) {
        mWindowManager.removeView(showView);
    }
}
