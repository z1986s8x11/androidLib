package com.zsx.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
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
/**
 * Activity 基类
 *
 *
 * Created by zhusx on 2015/7/31.
 */
public class Lib_BaseActivity extends Activity {
	public static final String _EXTRA_Serializable = "extra_Serializable";
	public static final String _EXTRA_String = "extra_String";
	public static final String _EXTRA_Integer = "extra_Integer";
	public static final String _EXTRA_Boolean = "extra_boolean";

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void _addFragment(int id, Fragment from, Fragment to, String tag,
			boolean addBackStack, String stackName) {
		if (from == to) {
			return;
		}
		FragmentTransaction transaction = getFragmentManager()
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

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void _replaceFragment(int id, Fragment fragment) {
		getFragmentManager().beginTransaction().replace(id, fragment).commit();
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void _replaceFragment(int id, Fragment fragment, String tag) {
		getFragmentManager().beginTransaction().replace(id, fragment, tag)
				.commit();
	}

	private Toast toast;

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

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			View v = getCurrentFocus();
			if (isShouldHideInput(v, ev)) {
				hideSoftInput(v.getWindowToken());
			}
		}
		return super.dispatchTouchEvent(ev);
	}

	private boolean isShouldHideInput(View v, MotionEvent event) {
		if (v != null && (v instanceof EditText)) {
			int[] l = { 0, 0 };
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

	private void hideSoftInput(IBinder token) {
		if (token != null) {
			InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			im.hideSoftInputFromWindow(token,
					InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Lib_SystemExitManager.addActivity(this);
	}

	private Set<Lib_OnLifecycleListener> listeners = new HashSet<Lib_OnLifecycleListener>();

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

	private long exitTime;
	private boolean isDoubleBack = false;

	/**
	 * 设置 连续双击后退键 退出
	 * 
	 * @param isDoubleBack
	 */
	public final void _setDoubleBackExit(boolean isDoubleBack) {
		this.isDoubleBack = isDoubleBack;
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

	public int _getFullScreenWidth() {
		DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
		return displayMetrics.widthPixels;
	}
}
