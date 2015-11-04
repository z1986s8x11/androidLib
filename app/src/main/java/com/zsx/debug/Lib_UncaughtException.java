package com.zsx.debug;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Looper;
import android.util.Log;

import com.zsx.app.Lib_BaseActivity;
import com.zsx.app._PublicFragmentActivity;
import com.zsx.manager.Lib_FileManager;
import com.zsx.manager.Lib_SystemExitManager;
import com.zsx.util.Lib_Util_System;

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


public final class Lib_UncaughtException implements UncaughtExceptionHandler {

    // CrashHandler 实例
    private static Lib_UncaughtException INSTANCE = new Lib_UncaughtException();

    /**
     * 保证只有一个 CrashHandler 实例
     */
    private Lib_UncaughtException() {
    }

    /**
     * 获取 CrashHandler 实例 ,单例模式
     */
    public synchronized static Lib_UncaughtException _getInstance() {
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
        handleException(thread, ex);
        //FIXME 不知道为什么  一定要杀掉进程才可以..不然界面会卡住
        Lib_SystemExitManager.exitSystem(true);
    }

    /**
     * 自定义错误处理，收集错误信息，发送错误报告等操作均在此完成
     *
     * @param ex
     * @return true：如果处理了该异常信息 返回false；否则返回 true
     */
    private boolean handleException(final Thread thread, final Throwable ex) {
        LogUtil.DEBUG = true;
        if (LogUtil.DEBUG) {
            LogUtil.e(ex);
        }
        if (Lib_Util_System.isPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            // 收集设备参数信息
            Map<String, String> infos = collectDeviceInfo(mContext);
            // 保存日志文件
            final String log = saveCrashInfo2File(ex, infos);
            if (Lib_Util_System.isInMainThread()) {
                if (startActivity(log)) {
                    return false;
                }
            } else {
                Looper.prepare();
                if (startActivity(log)) {
                    return false;
                }
                Looper.loop();
            }
        } else {
            return true;
        }
        if (LogUtil.DEBUG) {
            LogUtil.e(this, "是否注册了" + P_BugReportFragment.class.getSimpleName() + ".java 在AndroidManifest?");
        }
        return true;
    }

    /**
     * 跳跳转到显示页面
     */
    private boolean startActivity(String log) {
        try {
            //打开BugReportActivity
            Intent intent = new Intent(mContext, _PublicFragmentActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(_PublicFragmentActivity._EXTRA_FRAGMENT, P_BugReportFragment.class);
            intent.putExtra(Lib_BaseActivity._EXTRA_String, log);
            mContext.startActivity(intent);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 收集设备参数信息
     *
     * @param ctx
     */
    private Map<String, String> collectDeviceInfo(Context ctx) {
        // 用来存储设备信息和异常信息
        Map<String, String> infos = new HashMap<>();
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
        return infos;
    }

    /**
     * 保存错误信息到文件中 *
     *
     * @param ex
     * @return 返回文件名称, 便于将文件传送到服务器
     */
    private String saveCrashInfo2File(Throwable ex, Map<String, String> infos) {
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
            // 用于格式化日期,作为日志文件名的一部分
            @SuppressLint("SimpleDateFormat")
            DateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
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