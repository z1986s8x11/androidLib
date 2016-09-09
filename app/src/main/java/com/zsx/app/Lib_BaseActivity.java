package com.zsx.app;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.zsx.itf.Lib_LifeCycle;
import com.zsx.itf.Lib_OnBackKeyListener;
import com.zsx.itf.Lib_OnCancelListener;
import com.zsx.itf.Lib_OnCycleListener;
import com.zsx.manager.Lib_SystemExitManager;

import java.util.HashSet;
import java.util.Set;

/**
 * Activity 基类
 * <p>
 * <p>
 * Created by zhusx on 2015/7/31.
 */
public class Lib_BaseActivity extends Activity implements Lib_LifeCycle {
    public static final String _EXTRA_Serializable = "extra_Serializable";
    public static final String _EXTRA_String = "extra_String";
    public static final String _EXTRA_Strings = "extra_Strings";
    public static final String _EXTRA_ListSerializable = "extra_ListSerializable";
    public static final String _EXTRA_Integer = "extra_Integer";
    public static final String _EXTRA_Boolean = "extra_boolean";
    public static final String _EXTRA_Double = "extra_double";
    public static final String _EXTRA_Strig_ID = "extra_id";
    protected String mToastMessage = "再次点击退出";
    private boolean pIsPause;
    private boolean pisDestroy;
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
     * 基于Activity生命周期回调
     */
    private Set<Lib_OnCancelListener> cancelListener = new HashSet<Lib_OnCancelListener>();
    private Set<Lib_OnCycleListener> cycleListener = new HashSet<Lib_OnCycleListener>();
    private Lib_OnBackKeyListener onBackKeyListener;

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

    /**
     * 设置点击非EditText 关闭软键盘
     */
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
        pisDestroy = false;
        Lib_SystemExitManager.addActivity(this);
    }

    /**
     * 添加取消监听
     */
    @Override
    public void _addOnCancelListener(Lib_OnCancelListener listener) {
        if (cancelListener.contains(listener)) {
            return;
        }
        cancelListener.add(listener);
    }

    /**
     * 移除取消监听
     */
    @Override
    public void _removeOnCancelListener(Lib_OnCancelListener listener) {
        cancelListener.remove(listener);
    }

    /**
     * 添加周期监听
     */
    @Override
    public void _addOnCycleListener(Lib_OnCycleListener listener) {
        if (cycleListener.contains(listener)) {
            return;
        }
        cycleListener.add(listener);
    }

    /**
     * 移除周期监听
     */
    @Override
    public void _removeOnCycleListener(Lib_OnCycleListener listener) {
        cycleListener.remove(listener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        pIsPause = false;
        for (Lib_OnCycleListener l : cycleListener) {
            l.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        pIsPause = true;
        for (Lib_OnCycleListener l : cycleListener) {
            l.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        pisDestroy = true;
        destroyActivity();
        cycleListener.clear();
        super.onDestroy();
    }

    public boolean _isDestroy() {
        return pisDestroy;
    }

    public boolean _isPause() {
        return pIsPause;
    }

    private final void destroyActivity() {
        for (Lib_OnCancelListener l : cancelListener) {
            l.onCancel();
        }
        cancelListener.clear();
        Lib_SystemExitManager.removeActivity(this);
    }

    @Override
    public void finish() {
        super.finish();
        pisDestroy = true;
        destroyActivity();
    }

    /**
     * 关闭程序
     */
    public void _exitSystem() {
        Lib_SystemExitManager.exitSystem();
    }

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

    /**
     * 拿到屏幕的高度
     */
    public int _getFullScreenHeight() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        return displayMetrics.heightPixels;
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (onBackKeyListener != null) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (onBackKeyListener.onBackKey()) {
                    return true;
                }
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public void _setOnBackKeyListener(Lib_OnBackKeyListener onBackKeyListener) {
        this.onBackKeyListener = onBackKeyListener;
    }
}
