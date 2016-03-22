package com.zsx.debug;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.IBinder;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;

import com.zsx.util.Lib_Util_String;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

@Deprecated
public class Lib_DebugService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	private TextView t;
	private WindowManager mWindowManager;
	private LayoutParams params;
	private ActivityManager mActivityManager;
	private Timer timer = new Timer();

	@Override
	public void onCreate() {
		super.onCreate();
		final long total = getmem_TOLAL() * 1000;
		mWindowManager = (WindowManager) getSystemService(Service.WINDOW_SERVICE);
		mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				if (t != null) {
					t.post(new Runnable() {
						public void run() {
							MemoryInfo memoryInfo = new MemoryInfo();
							// 获得系统可用内存，保存在MemoryInfo对象上
							mActivityManager.getMemoryInfo(memoryInfo);
							t.setText(Lib_Util_String
									.toFileSize(memoryInfo.availMem)
									+ "/"
									+ Lib_Util_String.toFileSize(total));
						}
					});
				}
			}
		}, 50, 500);
		/**
		 * 悬浮窗权限 <uses-permission
		 * android:name="android.permission.SYSTEM_ALERT_WINDOW" />
		 */
		if (t == null) {
			params = new LayoutParams();
			params.type = LayoutParams.TYPE_PHONE;
			// params.format = PixelFormat.RGBA_8888;
			params.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL
					| LayoutParams.FLAG_NOT_FOCUSABLE;
			params.gravity = Gravity.LEFT | Gravity.TOP;
			params.width = LayoutParams.WRAP_CONTENT;
			params.height = LayoutParams.WRAP_CONTENT;
			params.x = 120;
			params.y = 123;
			t = new TextView(getBaseContext());
			t.setTextColor(Color.WHITE);
			t.setBackgroundColor(Color.BLACK);
			t.setOnTouchListener(new View.OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						params.x = (int) event.getRawX() - t.getWidth() / 2;
						params.y = (int) event.getRawY() - t.getHeight() / 2;
						mWindowManager.updateViewLayout(v, params);
						break;
					case MotionEvent.ACTION_MOVE:
						params.x = (int) event.getRawX() - t.getWidth() / 2;
						params.y = (int) event.getRawY() - t.getHeight() / 2;
						mWindowManager.updateViewLayout(v, params);
						break;
					case MotionEvent.ACTION_UP:
						params.x = (int) event.getRawX() - t.getWidth() / 2;
						params.y = (int) event.getRawY() - t.getHeight() / 2;
						mWindowManager.updateViewLayout(v, params);
						break;
					default:
						break;
					}
					return true;
				}
			});
		}
		/* 如果view没有被加入到某个父组件中 */
		if (t.getParent() == null) {
			mWindowManager.addView(t, params);
		}
		MemoryInfo memoryInfo = new MemoryInfo();
		// 获得系统可用内存，保存在MemoryInfo对象上
		mActivityManager.getMemoryInfo(memoryInfo);
		t.setText(Lib_Util_String.toFileSize(memoryInfo.availMem) + "/"
				+ Lib_Util_String.toFileSize(Runtime.getRuntime().maxMemory()));
	}

	public static long getmem_TOLAL() {
		long mTotal;
		// /proc/meminfo读出的内核信息进行解释
		String path = "/proc/meminfo";
		String content = null;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(path), 8);
			String line;
			if ((line = br.readLine()) != null) {
				content = line;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		// beginIndex
		int begin = content.indexOf(':');
		// endIndex
		int end = content.indexOf('k');
		// 截取字符串信息
		content = content.substring(begin + 1, end).trim();
		mTotal = Integer.parseInt(content);
		return mTotal;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		/* 如果view被加入到某个父组件中 */
		if (t.getParent() != null) {
			mWindowManager.removeView(t);
		}
		timer.cancel();
	}
}
