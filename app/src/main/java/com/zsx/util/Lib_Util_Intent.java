package com.zsx.util;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.provider.Settings;
import android.text.TextUtils;
import android.widget.Toast;

import com.zsx.debug.LogUtil;

import java.io.File;

public class Lib_Util_Intent {
    /**
     * 短信分享
     *
     * @param mContext
     * @param smstext  短信分享内容
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
     * @param mContext 当前显示的Context
     * @param toUser   发送给谁
     * @param title    标题
     * @param text     文本
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
     * @param packageName 报名
     * @throws ActivityNotFoundException 没有找到该应用
     * @throws SecurityException         没有权限
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
     * @param activityTitle Activity的名字
     * @param msgTitle      消息标题
     * @param msgText       消息内容
     * @param imgPath       图片路径，不分享图片则传null
     */
    public static void shareMsg(Context context, String activityTitle, String msgTitle, String msgText, String imgPath) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        if (TextUtils.isEmpty(imgPath)) {
            intent.setType("text/plain"); // 纯文本
        } else {
            File f = new File(imgPath);
            if (f != null && f.exists() && f.isFile()) {
                intent.setType("image/jpg");
                //当用户选择短信时使用sms_body取得文字
                intent.putExtra("sms_body", msgText);
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

    private void a() {
        Intent intent = new Intent();
        /** 有就不创建 */
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        /** 清除当前 --目标的所有Task */
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    }

    public static boolean installFromMarket(Context activity) {
        try {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + activity.getPackageName())));
            return true;
        } catch (ActivityNotFoundException e) {
            return false;
        }
    }

    /**
     * 运行某包名的应用
     */
    public static Intent startApp(Activity activity, String packageName) {
        Intent intent = activity.getPackageManager().getLaunchIntentForPackage(packageName);
        return intent;
    }

    /**
     * 创建快捷方式
     */
    private void createShortCut(Context context, int titleRes, int iconRes) {
        if (!Lib_Util_System.isPermission(context, Manifest.permission.INSTALL_SHORTCUT)) {
            if (LogUtil.DEBUG) {
                LogUtil.e(Lib_Util_Intent.class, "need usePermission uses-permission android:name=\"com.android.launcher.permission.INSTALL_SHORTCUT\"");
            }
            return;
        }
        // 创建快捷方式的Intent
        Intent shortcutIntent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        // 不允许重复创建
        shortcutIntent.putExtra("duplicate", false);
        // 快捷方式的名字
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, context.getString(titleRes));
        // 快捷方式icon
        Parcelable icon = Intent.ShortcutIconResource.fromContext(
                context, iconRes);
        //设置icon
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
        // 点击快捷图片，运行的程序主入口
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);
        // 发送广播,创建快捷方式
        context.sendBroadcast(shortcutIntent);
    }

    /**
     * 跳转到应用系统设置详情页
     */
    public static void startAppSettingDetail(Activity activity) {
        try {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    .setData(Uri.parse("package:" + activity.getPackageName()));
            activity.startActivityForResult(intent, 11);
        } catch (ActivityNotFoundException ee) {
            if (LogUtil.DEBUG) {
                LogUtil.w(ee);
            }
        }
    }
}
