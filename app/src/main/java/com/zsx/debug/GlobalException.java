package com.zsx.debug;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Looper;
import android.util.Log;

import com.zsx.manager.Lib_FileManager;
import com.zsx.manager.Lib_SystemExitManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class GlobalException implements UncaughtExceptionHandler {

	// CrashHandler 实例
	private static GlobalException INSTANCE = new GlobalException();

	// 系统默认的 UncaughtException 处理类
	private UncaughtExceptionHandler mDefaultHandler;

	// 用来存储设备信息和异常信息
	private Map<String, String> infos = new HashMap<>();

	// 用于格式化日期,作为日志文件名的一部分
	@SuppressLint("SimpleDateFormat")
	private DateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");

	/** 保证只有一个 CrashHandler 实例 */
	private GlobalException() {
	}

	/** 获取 CrashHandler 实例 ,单例模式 */
	public synchronized static GlobalException _getInstance() {
		return INSTANCE;
	}

	private Context mContext;

	/**
	 * 初始化
	 * 
	 * @param context
	 */
	public void _init(Context context) {

		if (mContext == null) {
			// 获取系统默认的 UncaughtException 处理器
			mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
			// 设置该 CrashHandler 为程序的默认处理器
			Thread.setDefaultUncaughtExceptionHandler(this);
			if (mContext instanceof Application) {
				this.mContext = context;
			} else {
				this.mContext = context.getApplicationContext();
			}
		} else {
			if (LogUtil.DEBUG) {
				LogUtil.e(this, "_init() is Repeat Call");
			}
		}
	}

	/**
	 * 当 UncaughtException 发生时会转入该函数来处理
	 */
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		if (!handleException(thread, ex) && mDefaultHandler != null) {
			// 如果用户没有处理则让系统默认的异常处理器来处理
			Thread.setDefaultUncaughtExceptionHandler(mDefaultHandler);
			mDefaultHandler.uncaughtException(thread, ex);
		}
	}

	/**
	 * 自定义错误处理，收集错误信息，发送错误报告等操作均在此完成
	 * 
	 * @param ex
	 * @return true：如果处理了该异常信息；否则返回 false
	 */
	private boolean handleException(final Thread thread, final Throwable ex) {
		if (ex == null) {
			return false;
		}
		if (LogUtil.DEBUG) {
			LogUtil.e(ex);
		}
		// 收集设备参数信息
		collectDeviceInfo(mContext);
		// 保存日志文件
		final String log = saveCrashInfo2File(ex);
		final Activity topActivity = Lib_SystemExitManager.getLastActivity();
		if (topActivity == null) {
			return false;
		}
		boolean isTop = Lib_SystemExitManager.isTopActivity(topActivity);
		if (!isTop) {
			return false;
		}
		new Thread() {
			public void run() {
				Looper.prepare();
				sendAppCrashReport(topActivity, log, thread, ex);
				Looper.loop();
			}
		}.start();
		return true;
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void sendAppCrashReport(final Context cont, final String crashReport, final Thread thread, final Throwable ex) {
		AlertDialog.Builder builder;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			builder = new AlertDialog.Builder(cont, AlertDialog.THEME_HOLO_LIGHT);
		} else {
			builder = new AlertDialog.Builder(cont);
		}
		builder.setIcon(android.R.drawable.ic_dialog_info);
		builder.setTitle("应用程序错误");
		builder.setMessage("很抱歉，应用程序出现错误，即将退出。\n请提交错误报告，我们会尽快修复这个问题！");
		builder.setPositiveButton("提交报告", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				// 发送异常报告
				Intent i = new Intent(Intent.ACTION_SEND);
				// i.setType("text/plain"); //模拟器
				i.setType("message/rfc822"); // 真机
				i.putExtra(Intent.EXTRA_EMAIL, new String[] { "z1986s8x11@163.com" });
				i.putExtra(Intent.EXTRA_SUBJECT, "Android客户端 - 错误报告");
				i.putExtra(Intent.EXTRA_TEXT, crashReport);
				cont.startActivity(Intent.createChooser(i, "发送错误报告"));
				// 退出
				Lib_SystemExitManager.exitSystem();
				if (mDefaultHandler != null) {
					mDefaultHandler.uncaughtException(thread, ex);
				}
			}
		});
		builder.setNegativeButton("退出", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				// 退出
				Lib_SystemExitManager.exitSystem();
				if (mDefaultHandler != null) {
					mDefaultHandler.uncaughtException(thread, ex);
				}
			}
		});
		builder.setCancelable(false);
		builder.show();
	}

	/**
	 * 收集设备参数信息
	 * 
	 * @param ctx
	 */
	private void collectDeviceInfo(Context ctx) {
		try {
			PackageManager pm = ctx.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
			if (pi != null) {
				String versionName = pi.versionName == null ? "null" : pi.versionName;
				String versionCode = pi.versionCode + "";
				infos.put("versionName", versionName);
				infos.put("versionCode", versionCode);
			}
		} catch (NameNotFoundException e) {
			if (LogUtil.DEBUG) {
				LogUtil.e(getClass().getSimpleName(), "an error occured when collect package info");
			}
		}

		Field[] fields = Build.class.getDeclaredFields();
		for (Field field : fields) {
			try {
				field.setAccessible(true);
				infos.put(field.getName(), field.get(null).toString());
				Log.d(getClass().getSimpleName(), field.getName() + " : " + field.get(null));
			} catch (Exception e) {
				if (LogUtil.DEBUG) {
					LogUtil.e(getClass().getSimpleName(), "an error occured when collect crash info");
				}
			}
		}
	}

	/**
	 * 保存错误信息到文件中 *
	 * 
	 * @param ex
	 * @return 返回文件名称,便于将文件传送到服务器
	 */
	private String saveCrashInfo2File(Throwable ex) {
		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, String> entry : infos.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			sb.append(key + "=" + value + "\n");
		}
		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		ex.printStackTrace(printWriter);
		Throwable cause = ex.getCause();
		while (cause != null) {
			cause.printStackTrace(printWriter);
			cause = cause.getCause();
		}
		printWriter.close();
		String result = writer.toString();
		sb.append(result);
		try {
			String time = formatter.format(new Date());
			File saveFile = new File(Lib_FileManager.getLogPath(), time + "_log.txt");
			FileOutputStream fos = new FileOutputStream(saveFile);
			fos.write(sb.toString().getBytes());
			fos.close();
		} catch (Exception e) {
			if (LogUtil.DEBUG) {
				LogUtil.e(getClass().getSimpleName(), "an error occured while writing file...");
			}
		}
		return sb.toString();
	}
}