package com.zsx.app;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
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

import com.zsx.manager.Lib_SystemExitManager;

public class Lib_BaseFragmentActivity extends FragmentActivity {
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
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Lib_SystemExitManager.addActivity(this);
	}

	private boolean isResume = false;
	private boolean isDestroy = false;

	public boolean _isResume() {
		return isResume;
	}

	public boolean _isDestroy() {
		return isDestroy;
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onResume() {
		super.onResume();
		isResume = true;
	}

	@Override
	protected void onPause() {
		super.onPause();
		isResume = false;
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		isDestroy = true;
		Lib_SystemExitManager.removeActivity(this);
	}

	/**
	 * 关闭程序
	 */
	public void _exitSystem() {
		Lib_SystemExitManager.exitSystem();
	}

	@Override
	public void finish() {
		super.finish();
		isDestroy = true;
		Lib_SystemExitManager.removeActivity(this);
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
