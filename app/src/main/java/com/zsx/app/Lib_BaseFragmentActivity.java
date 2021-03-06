package com.zsx.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.zsx.debug.LogUtil;
import com.zsx.debug.P_LogCatFragment;
import com.zsx.itf.Lib_LifeCycle;
import com.zsx.itf.Lib_OnBackKeyListener;
import com.zsx.itf.Lib_OnCycleListener;
import com.zsx.manager.Lib_SystemExitManager;
import com.zsx.util._Arrays;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Lib_BaseFragmentActivity extends FragmentActivity implements Lib_LifeCycle {
    public static final String _EXTRA_Serializable = Lib_BaseActivity._EXTRA_Serializable;
    public static final String _EXTRA_ListSerializable = Lib_BaseActivity._EXTRA_ListSerializable;
    public static final String _EXTRA_String = Lib_BaseActivity._EXTRA_String;
    public static final String _EXTRA_Strings = Lib_BaseActivity._EXTRA_Strings;
    public static final String _EXTRA_Integer = Lib_BaseActivity._EXTRA_Integer;
    public static final String _EXTRA_Boolean = Lib_BaseActivity._EXTRA_Boolean;
    public static final String _EXTRA_Double = Lib_BaseActivity._EXTRA_Double;
    public static final String _EXTRA_String_ID = Lib_BaseActivity._EXTRA_Strig_ID;
    protected String mToastMessage = "再次点击退出";
    private boolean pIsPause;
    private boolean pisDestroy;
    private boolean isOpenDebugMenu;
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
     * 设置 是非在顶层处理 点击非EditText 隐藏键盘
     *
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
        pisDestroy = false;
        Lib_SystemExitManager.addActivity(this);
    }

    @Override
    public void _addOnCycleListener(Lib_OnCycleListener listener) {
        if (cycleListener.contains(listener)) {
            return;
        }
        cycleListener.add(listener);
    }

    @Override
    public void _removeOnCycleListener(Lib_OnCycleListener listener) {
        cycleListener.remove(listener);
    }

    @Override
    public Set<Lib_OnCycleListener> getCycleListeners() {
        return cycleListener;
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
        super.onDestroy();
        pisDestroy = true;
        destroyActivity();
    }

    public boolean _isDestroy() {
        return pisDestroy;
    }

    public boolean _isPause() {
        return pIsPause;
    }

    private final void destroyActivity() {
        for (Lib_OnCycleListener l : cycleListener) {
            l.onDestroy();
        }
        cycleListener.clear();
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
//        transaction.commit();
        transaction.commitAllowingStateLoss();
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
        getSupportFragmentManager().beginTransaction().replace(id, fragment).commit();
    }

    public void _replaceFragment(int id, Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction().replace(id, fragment, tag).commit();
    }

    public void _setOpenDebugMenu(boolean isOpenDebugMenu) {
        this.isOpenDebugMenu = isOpenDebugMenu;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isOpenDebugMenu) {
            if (LogUtil.DEBUG) {
                menu.add(8, 11, 1, "查看LogCat");
                menu.add(8, 12, 1, "清空LogCat");
            }
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (isOpenDebugMenu) {
            if (LogUtil.DEBUG) {
                if (item.getGroupId() == 8) {
                    switch (item.getItemId()) {
                        case 11:
                            Intent intent = new Intent(this, _PublicFragmentActivity.class);
                            intent.putExtra(_PublicFragmentActivity._EXTRA_FRAGMENT, P_LogCatFragment.class);
                            startActivity(intent);
                            break;
                        case 12:
                            try {
                                Runtime.getRuntime().exec("logcat -c");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (!_Arrays.isEmpty(getSupportFragmentManager().getFragments())) {
            super.onSaveInstanceState(outState);
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
