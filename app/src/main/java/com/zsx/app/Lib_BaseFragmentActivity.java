package com.zsx.app;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.zsx.itf.Lib_OnLifecycleListener;
import com.zsx.manager.Lib_SystemExitManager;

import java.util.HashSet;
import java.util.Set;

public class Lib_BaseFragmentActivity extends FragmentActivity {
    public static final String _EXTRA_Serializable = Lib_BaseActivity._EXTRA_Serializable;
    public static final String _EXTRA_String = Lib_BaseActivity._EXTRA_String;
    public static final String _EXTRA_Integer = Lib_BaseActivity._EXTRA_Integer;
    public static final String _EXTRA_Boolean = Lib_BaseActivity._EXTRA_Boolean;

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

    /**
     * 设置 是非在顶层处理 点击非E
     * @param isClickNoEditTextCloseInput
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

    /**
     * 设置 连续双击后退键 退出
     *
     * @param isDoubleBack
     */
    public final void _setDoubleBackExit(boolean isDoubleBack) {
        this.isDoubleBack = isDoubleBack;
    }

    /**
     * 拿到屏幕的宽度
     */
    public int _getFullScreenWidth() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (!isDoubleBack) {
            return super.onKeyDown(keyCode, event);
        }
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast toast = Toast
                        .makeText(this, "再次点击退出", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                exitTime = System.currentTimeMillis();
            } else {
                _exitSystem();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    public void _addFragment(int id, Fragment from, Fragment to, String tag,
                             boolean addBackStack, String stackName) {
        if (from == to) {
            return;
        }
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        if (from != null && from.isAdded() && !from.isHidden()) {
            transaction.hide(from);
        }
        if (!to.isAdded()) {
            if (tag == null) {
                transaction.add(id, to);
            } else {
                transaction.add(id, to, tag);
            }
            if (addBackStack) {
                transaction.addToBackStack(stackName);
            }
        } else {
            transaction.show(to);
        }
        transaction.commit();
    }

    public void _addFragment(int id, Fragment from, Fragment to) {
        _addFragment(id, from, to, null, false, null);
    }

    public void _addFragment(int id, Fragment from, Fragment to, String tag) {
        _addFragment(id, from, to, tag, false, null);
    }

    public void _addFragmentToStack(int id, Fragment from, Fragment to) {
        _addFragment(id, from, to, null, true, null);
    }

    public void _addFragmentToStack(int id, Fragment from, Fragment to,
                                    String tag) {
        _addFragment(id, from, to, tag, true, null);
    }

    public void _replaceFragment(int id, Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(id, fragment)
                .commit();
    }

    public void _replaceFragment(int id, Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction()
                .replace(id, fragment, tag).commit();
    }

}
