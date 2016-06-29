package com.zsx.app;

import android.view.View;
import android.widget.PopupWindow;

/**
 * Author       zhusx
 * Email        327270607@qq.com
 * Created      2016/6/29 9:56
 */
public class Lib_BasePopupWindow extends PopupWindow {

    @Override
    public void showAtLocation(final View parent, final int gravity, final int x, final int y) {
        if (parent.getWindowToken() == null) {
            //防止Activity or Fragment onCreate 的时候 show会报 BadTokenException
            parent.post(new Runnable() {
                @Override
                public void run() {
                    if (parent.getWindowToken() == null) {
                        return;
                    }
                    showAtLocation(parent, gravity, x, y);
                }
            });
        } else {
            super.showAtLocation(parent, gravity, x, y);
        }
    }
}
