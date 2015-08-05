package com.zsx.util;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.zsx.debug.LogUtil;

import java.io.File;

public class Lib_Util_Intent {
	/**
	 * 短信分享
	 * 
	 * @param mContext
	 * @param smstext
	 *            短信分享内容
	 * @return
	 */
	public static void sendSms(Context mContext, String smstext) {
		Uri smsToUri = Uri.parse("smsto:");
		Intent mIntent = new Intent(Intent.ACTION_SENDTO,
				smsToUri);
		mIntent.putExtra("sms_body", smstext);
		mContext.startActivity(mIntent);
	}

	/**
	 * 发送邮件
	 * 
	 * @param mContext
	 *            当前显示的Context
	 * @param toUser
	 *            发送给谁
	 * @param title
	 *            标题
	 * @param text
	 *            文本
	 * @return
	 */
	public static void sendMail(Context mContext, String[] toUser,
			String title, String text) {
		// 调用系统发邮件
		Intent emailIntent = new Intent(Intent.ACTION_SEND);
		// 设置文本格式
		// emailIntent.setType("text/plain"); //模拟器
		emailIntent.setType("message/rfc822");
		// 设置对方邮件地址
		emailIntent.putExtra(Intent.EXTRA_EMAIL, toUser);
		// 设置标题内容
		emailIntent.putExtra(Intent.EXTRA_SUBJECT, title);
		// 设置邮件文本内容
		emailIntent.putExtra(Intent.EXTRA_TEXT, text);
		mContext.startActivity(Intent.createChooser(emailIntent,
				"Choose Email Client"));
	}

	/**
	 * 卸载App
	 * 
	 * @param context
	 * @param packageName
	 *            报名
	 * @throws ActivityNotFoundException
	 *             没有找到该应用
	 * @throws SecurityException
	 *             没有权限
	 */
	public static void uninstallApp(Context context, String packageName)
			throws ActivityNotFoundException, SecurityException {
		Intent uninstallIntent = new Intent(Intent.ACTION_DELETE,
				Uri.parse("package:" + packageName));
		context.startActivity(uninstallIntent);
	}

	/**
	 * 分享功能
	 * 
	 * @param activityTitle
	 *            Activity的名字
	 * @param msgTitle
	 *            消息标题
	 * @param msgText
	 *            消息内容
	 * @param imgPath
	 *            图片路径，不分享图片则传null
	 */
	public void shareMsg(Context context, String activityTitle,
			String msgTitle, String msgText, String imgPath) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		if (imgPath == null || imgPath.equals("")) {
			intent.setType("text/plain"); // 纯文本
		} else {
			File f = new File(imgPath);
			if (f != null && f.exists() && f.isFile()) {
				intent.setType("image/jpg");
				Uri u = Uri.fromFile(f);
				intent.putExtra(Intent.EXTRA_STREAM, u);
			}
		}
		intent.putExtra(Intent.EXTRA_SUBJECT, msgTitle);
		intent.putExtra(Intent.EXTRA_TEXT, msgText);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		try {
			context.startActivity(Intent.createChooser(intent, activityTitle));
		} catch (ActivityNotFoundException ex) {
			Toast.makeText(context, "分享失败", Toast.LENGTH_SHORT).show();
		}
	}

	public static void startInstallAPK(Context context, String apkPath) {
		File apk = new File(apkPath);
		if (!apk.exists()) {
			LogUtil.e(Lib_Util_Intent.class, "文件不存在，安装失败：" + apkPath);
			return;
		}
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setDataAndType(Uri.fromFile(apk),
				"application/vnd.android.package-archive");
		context.startActivity(intent);
	}

	public void a() {
		Intent intent = new Intent();
		/** 有就不创建 */
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		/** 清除当前 --目标的所有Task */
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	}
}
