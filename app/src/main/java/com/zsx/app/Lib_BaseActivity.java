package com.zsx.app;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.zsx.itf.Lib_OnLifecycleListener;
import com.zsx.manager.Lib_SystemExitManager;

import java.util.HashSet;
import java.util.Set;

/**
 * Activity 基类
 * <p/>
 * <p/>
 * Created by zhusx on 2015/7/31.
 */
public class Lib_BaseActivity extends Activity {
    public static final String _EXTRA_Serializable = "extra_Serializable";
    public static final String _EXTRA_String = "xtra_String";
    public static final String _EXTRA_Integer = "extra_Integer";
    public static final String _EXTRA_Boolean = "extra_boolean";
    /**
     * 一个Activity 只创建一个Toast
     */
    private Toast toast;
    /**
     * 上一次点击返回键退出的时间
     */
    private long exitTime;
    /**
     * 是否连续点击返回键退出
     */
    private boolean isDoubleBack = false;

    /**
     * 点击非EditText 关闭键盘
     */
    private boolean isClickNoEditTextCloseInput = false;

    /**
     * Activity生命周期回调
     */
    private Set<Lib_OnLifecycleListener> listeners = new HashSet<Lib_OnLifecycleListener>();

    public void _showToast(String message) {
        if (TextUtils.isEmpty(message)) {
            return;
        }
        if (toast == null) {
            toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        }
        toast.setText(message);
        toast.show();
    }

    public void _setClickNoEditTextCloseInput(boolean isClickNoEditTextCloseInput) {
        this.isClickNoEditTextCloseInput = isClickNoEditTextCloseInput;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (!isClickNoEditTextCloseInput) {
            return super.dispatchTouchEvent(ev);
        }
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                if (v.getWindowToken() != null) {
                    InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    im.hideSoftInputFromWindow(v.getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Lib_SystemExitManager.addActivity(this);
    }


    public void _addOnLifeCycleListener(Lib_OnLifecycleListener listener) {
        listeners.add(listener);
    }

    public void _removeOnLifeCycleListener(Lib_OnLifecycleListener listener) {
        listeners.remove(listener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        for (Lib_OnLifecycleListener l : listeners) {
            l.onActivityResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        for (Lib_OnLifecycleListener l : listeners) {
            l.onActivityPause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (Lib_OnLifecycleListener l : listeners) {
            l.onActivityDestroy();
        }
        listeners.clear();
        Lib_SystemExitManager.removeActivity(this);
    }

    @Override
    public void finish() {
        super.finish();
        for (Lib_OnLifecycleListener l : listeners) {
            l.onActivityDestroy();
        }
        listeners.clear();
        Lib_SystemExitManager.removeActivity(this);
    }

    /**
     * 关闭程序
     */
    public void _exitSystem() {
        Lib_SystemExitManager.exitSystem();
    }

    private String mToastMessage = "再次点击退出";

    /**
     * 设置 连续双击后退键 退出
     *
     * @param isDoubleBack
     */
    public final void _setDoubleBackExit(boolean isDoubleBack) {
        this.isDoubleBack = isDoubleBack;
    }


    public final void _setDoubleBackExit(boolean isDoubleBack, String toastMessage) {
        this.isDoubleBack = isDoubleBack;
        this.mToastMessage = toastMessage;
    }

    /**
     * 拿到屏幕的宽度
     */
    public int _getFullScreenWidth() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    @Override
    public void onBackPressed() {
        if (!isDoubleBack) {
            super.onBackPressed();
            return;
        }
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            _showToast(mToastMessage);
            exitTime = System.currentTimeMillis();
        } else {
            _exitSystem();
        }
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (!isDoubleBack) {
//            return super.onKeyDown(keyCode, event);
//        }
//        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
//            if ((System.currentTimeMillis() - exitTime) > 2000) {
//                _showToast(mToastMessage);
//                exitTime = System.currentTimeMillis();
//            } else {
//                _exitSystem();
//            }
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }


}
