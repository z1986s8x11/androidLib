package com.zsx.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Looper;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.telephony.TelephonyManager;
import android.text.Selection;
import android.text.Spannable;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.zsx.debug.LogUtil;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;

public class Lib_Util_System {

    /**
     * 拿到apk版本
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (NameNotFoundException e) {
            if (LogUtil.DEBUG) {
                LogUtil.e(e);
            }
        }
        return versionName;
    }

    /**
     * 拿到apk version Code
     */
    public static int getAppVersionCode(Context context) {
        int versionCode = -1;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionCode = pi.versionCode;
        } catch (NameNotFoundException e) {
            if (LogUtil.DEBUG) {
                LogUtil.e(e);
            }
        }
        return versionCode;
    }

    /**
     * 复制字符串
     */
    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    public static void copy(Context context, String str) {
        if (Build.VERSION.SDK_INT >= 11) {
            android.content.ClipboardManager cmb1 = (android.content.ClipboardManager) context.getSystemService(Activity.CLIPBOARD_SERVICE);
            cmb1.setPrimaryClip(ClipData.newPlainText(null, str));
        } else {
            android.text.ClipboardManager cmb2 = (android.text.ClipboardManager) context.getSystemService(Activity.CLIPBOARD_SERVICE);
            cmb2.setText(str);
        }
    }

    /**
     * @return 拿到上一次复制的字符串
     */
    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    public static String getLastCopyText(Context context) {
        if (Build.VERSION.SDK_INT >= 11) {
            android.content.ClipboardManager cmb1 = (android.content.ClipboardManager) context.getSystemService(Activity.CLIPBOARD_SERVICE);
            return cmb1.getText().toString();
        } else {
            android.text.ClipboardManager cmb2 = (android.text.ClipboardManager) context.getSystemService(Activity.CLIPBOARD_SERVICE);
            return cmb2.getText().toString();
        }
    }

    /**
     * @param filePath
     * @return 文件MimeType
     */
    @SuppressLint("DefaultLocale")
    public static String getMimeType(String filePath) {
        String type = "";
        File file = new File(filePath);
        String fName = file.getName();
        /* 取得扩展名 */
        String end = fName.substring(fName.lastIndexOf(".") + 1, fName.length()).toLowerCase();
        /* 按扩展名的类型决定MimeType */
        if (end.equals("m4a") || end.equals("mp3") || end.equals("mid") || end.equals("xmf") || end.equals("ogg") || end.equals("wav")) {
            type = "audio";
        } else if (end.equals("3gp") || end.equals("mp4")) {
            type = "video";
        } else if (end.equals("jpg") || end.equals("gif") || end.equals("png") || end.equals("jpeg") || end.equals("bmp")) {
            type = "image";
        } else if (end.equals("doc") || end.equals("docx")) {
            type = "application/msword";
        } else if (end.equals("xls")) {
            type = "application/vnd.ms-excel";
        } else if (end.equals("ppt") || end.equals("pptx") || end.equals("pps") || end.equals("dps")) {
            type = "application/vnd.ms-powerpoint";
        } else {
            type = "*";
        }
        /* 如果无法直接打开，就弹出软件列表给用户选择 */
        type += "/*";
        return type;
    }

    /**
     * 显示或者隐藏 系统状态栏 在OnCreate 中加入
     * getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY)
     * onWindowVisibilityChanged(int visibility){}
     *
     * @param fullView 全屏View
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setNavVisibility(View fullView, boolean visible) {
        int newVis = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        if (!visible) {
            newVis |= View.SYSTEM_UI_FLAG_LOW_PROFILE | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }
        fullView.setSystemUiVisibility(newVis);
    }

    /**
     * 显示或者隐藏 系统状态栏 在OnCreate 中加入
     * getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY)
     * onWindowVisibilityChanged() onSystemUiVisibilityChange() 显示系统状态栏
     */
    public void showAndroidToolsBar(Activity activity) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        activity.getWindow().setAttributes(lp);
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    /**
     * onWindowVisibilityChanged() onSystemUiVisibilityChange 隐藏系统状态栏
     */
    public void hideAndroidToolsBar(Activity activity) {
        WindowManager.LayoutParams attr = activity.getWindow().getAttributes();
        attr.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activity.getWindow().setAttributes(attr);
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    /**
     * 返回手机服务商名字
     *
     * @param context
     * @return
     */
    public static String getProvidersName(Context context) {
        String ProvidersName = null;
        // 返回唯一的用户ID;就是这张卡的编号神马的
        String IMSI = getIMSI(context);
        // IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。
        if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
            ProvidersName = "中国移动";
        } else if (IMSI.startsWith("46001")) {
            ProvidersName = "中国联通";
        } else if (IMSI.startsWith("46003")) {
            ProvidersName = "中国电信";
        } else {
            ProvidersName = "其他服务商";
        }
        return ProvidersName;
    }

    /**
     * 与电话卡相关
     *
     * @param context
     * @return 手机IMSI号码(国际移动用户识别码)
     */
    public static String getIMSI(Context context) {
        TelephonyManager telephonyManager;
        telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        // 返回唯一的用户ID;就是这张卡的IMSI编号
        return telephonyManager.getSubscriberId();
    }

    /**
     * @return 返回手机ICCID号码(国际移动装备辨识码)
     */
    public static String getICCID(Context context) {
        TelephonyManager telephonyManager;
        telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        // 返回唯一的用户ID;就是这张卡的IMSI编号
        return telephonyManager.getSimSerialNumber();
    }

    /**
     * @return 手机串号
     */
    public static String getIMEI(Context context) {
        TelephonyManager telephonyManager;
        telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

    /**
     * 返回本地手机号码，这个号码不一定能获取到
     *
     * @param context
     * @return
     */
    public static String getNativePhoneNumber(Context context) {
        TelephonyManager telephonyManager;
        telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String NativePhoneNumber = null;
        NativePhoneNumber = telephonyManager.getLine1Number();
        return NativePhoneNumber;
    }

    /**
     * 使用Wifi时获取IP 设置用户权限
     * <p/>
     * <uses-permission
     * android:name="android.permission.ACCESS_WIFI_STATE"></uses-permission>
     *
     * @return
     */
    public static String getWifiIp(Context context) {
        // 获取wifi服务
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        // 判断wifi是否开启
        if (!wifiManager.isWifiEnabled()) {
            LogUtil.e(Lib_Util_System.class, "wifi 未开启!");
            //boolean b = wifiManager.setWifiEnabled(true);
            return null;
        }
        if (Lib_Util_System.isPermission(context, Manifest.permission.ACCESS_WIFI_STATE)) {
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int ipAddress = wifiInfo.getIpAddress();
            String ip = (ipAddress & 0xFF) + "." + ((ipAddress >> 8) & 0xFF) + "." + ((ipAddress >> 16) & 0xFF) + "." + (ipAddress >> 24 & 0xFF);
            return ip;
        } else {
            LogUtil.e(Lib_Util_System.class, " 没有添加权限 permission.ACCESS_WIFI_STATE");
            return null;
        }
    }

    /**
     * 打开Wifi 按钮
     * <p/>
     * <uses-permission
     * android:name="android.permission.CHANGE_WIFI_STATE"></uses-permission>
     *
     * @param context
     * @return
     */
    public static boolean openWifeConnect(Context context) {
        // 获取wifi服务
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        // 判断wifi是否开启
        if (!wifiManager.isWifiEnabled()) {
            return wifiManager.setWifiEnabled(true);
        }
        return false;
    }

    /**
     * 使用GPRS上网，时获取ip地址，设置用户上网权限
     * <p/>
     * <uses-permission
     * android:name="android.permission.INTERNET"></uses-permission>
     *
     * @return
     */
    public static String getGPRSIp() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            LogUtil.w(ex);
        }
        return "";
    }


    public static void setSelection(EditText ed) {
        CharSequence text = ed.getText();
        Selection.setSelection((Spannable) text, text.length());
    }

    /**
     * 隐藏键盘
     *
     * @param context
     */
    public static void hideInputMethod(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        // 强制显示
        // imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
        boolean isOpen = imm.isActive();// isOpen若返回true，则表示输入法打开
        if (isOpen) {
            // 强制隐藏键盘
            // imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            if (context instanceof Activity) {
                Activity activity = (Activity) context;
                View v = activity.getCurrentFocus();
                if (v != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        }
    }

    /**
     * 键盘自动弹出
     */
    public static void showInputMethod(final EditText mEditText) {
        mEditText.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager inputManager = (InputMethodManager) mEditText
                        .getContext().getSystemService(
                                Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(mEditText, 0);
                setSelection(mEditText);
            }
        }, 500);
    }

    /**
     * 用户储存当前屏幕休眠时间
     */
    private static int currentScreenTimeout = -2;
    /**
     * 是否自动亮度
     */
    private static boolean isAutoScreen;

    /**
     * 设置 当前 屏幕超时休眠时间 需要<uses-permission
     * android:name=\"android.permission.WRITE_SETTINGS\"/>
     *
     * @param context
     * @param currentScreen
     */
    public static void setScreenTimeOut(Context context, int currentScreen) {
        try {
            if (currentScreenTimeout == -2) {
                isAutoScreen = isAutoBrightness(context.getContentResolver());
                if (isAutoScreen) {
                    stopAutoBrightness(context);
                }
                currentScreenTimeout = Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, -1);
            }
            Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, currentScreen);
        } catch (Exception e) {
            LogUtil.w(e);
            if (LogUtil.DEBUG) {
                LogUtil.e(Lib_Util_System.class, "<uses-permission android:name=\"android.permission.WRITE_SETTINGS\"/> ");
            }
        }
    }

    /**
     * 恢复 当前 屏幕超时休眠时间 需要<uses-permission
     * android:name=\"android.permission.WRITE_SETTINGS\"/>
     *
     * @param context
     */
    public void restoreScreenTimeOut(Context context) {
        try {
            if (currentScreenTimeout != -2) {
                if (isAutoScreen) {
                    startAutoBrightness(context);
                }
                Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, currentScreenTimeout);
                currentScreenTimeout = -2;
            }
        } catch (Exception e) {
            LogUtil.w(e);
            if (LogUtil.DEBUG) {
                LogUtil.e(Lib_Util_System.class, " 需要权限:<uses-permission android:name=\"android.permission.WRITE_SETTINGS\"/> ");
            }
        }
    }

    /**
     * 判断是否开启了自动亮度调节
     *
     * @param aContentResolver
     * @return
     */
    public static boolean isAutoBrightness(ContentResolver aContentResolver) {
        boolean automicBrightness = false;
        try {
            automicBrightness = Settings.System.getInt(aContentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE) == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;
        } catch (SettingNotFoundException e) {
            LogUtil.w(e);
        }
        return automicBrightness;
    }

    /**
     * 停止自动亮度调节 需要<uses-permission
     * android:name=\"android.permission.WRITE_SETTINGS\"/>
     */
    public static void stopAutoBrightness(Context context) {
        Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
    }

    /**
     * 设置自动亮度调节 需要<uses-permission
     * android:name=\"android.permission.WRITE_SETTINGS\"/>
     */
    public static void startAutoBrightness(Context context) {
        Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
    }

    /**
     * 获取屏幕的亮度
     */
    public static int getScreenBrightness(Context context) {
        int nowBrightnessValue = 0;
        try {
            nowBrightnessValue = Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
        } catch (Exception e) {
            LogUtil.w(e);
        }
        return nowBrightnessValue;
    }

    /**
     * 设置当前屏幕亮度值 0--255
     */
    public static void setScreenBrightness(Context context, int paramInt) {
        if (paramInt < 10) {
            paramInt = 10;
        }
        try {
            Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, paramInt);
            // WindowManager.LayoutParams localLayoutParams = localWindow
            // .getAttributes();
            // float f = paramInt / 255.0F;
            // localLayoutParams.screenBrightness = f;
            // localWindow.setAttributes(localLayoutParams);
        } catch (Exception e) {
            LogUtil.w(e);
        }
    }

    private static Camera camera;

    /**
     * 打开闪光灯 需要 <uses-permission android:name="android.permission.CAMERA" />
     * <uses-permission android:name="android.permission.FLASHLIGHT" />
     * <uses-feature android:name="android.hardware.camera" /> <uses-feature
     * android:name="android.hardware.camera.autofocus" /> <uses-feature
     * android:name="android.hardware.camera.flash" />
     */
    public static void openFlashlight() {
        if (camera == null) {
            camera = Camera.open();
            camera.startPreview();
            Parameters parameters = camera.getParameters();
            parameters.setFlashMode(Parameters.FLASH_MODE_TORCH);
            camera.setParameters(parameters);
        }
    }

    /**
     * 关闭闪光灯
     */
    public static void closeFlashlight() {
        if (camera != null) {
            Parameters parameters = camera.getParameters();
            parameters.setFlashMode(Parameters.FLASH_MODE_OFF);
            camera.setParameters(parameters);
            camera.release();
            camera = null;
        }
    }

    /**
     * 执行cmd
     */
    public static String exec(String cmd) throws IOException {
        Runtime runtime = Runtime.getRuntime();
        // Process proc = runtime.exec("cmd.exe /c dir");
        Process proc = runtime.exec(cmd);
        String message = Lib_Util_String.toString(proc.getInputStream());
        proc.destroy();
        return message;
    }

    /**
     * 检查是否有某权限
     *
     * @param permission android.Manifest.permission.常量
     * @return 是否有此权限
     */
    public static boolean isPermission(Context context, String... permission) {

        try {
            int pCount = permission.length;
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            // 得到自己的包名
            String pkgName = pi.packageName;
            PackageInfo pkgInfo = pm.getPackageInfo(pkgName, PackageManager.GET_PERMISSIONS);// 通过包名，返回包信息
            String sharedPkgList[] = pkgInfo.requestedPermissions;// 得到权限列表
            for (int i = 0; i < sharedPkgList.length; i++) {
                String permName = sharedPkgList[i];
                /**
                 * //通过permName得到该权限的详细信息 PermissionInfo tmpPermInfo
                 * =pm.getPermissionInfo(permName, 0);
                 * //权限分为不同的群组，通过权限名，我们得到该权限属于什么类型的权限。 PermissionGroupInfo pgi =
                 * pm.getPermissionGroupInfo(tmpPermInfo.group, 0); tv.append(i
                 * + "-" + permName + "\n"); tv.append(i + "-" +
                 * pgi.loadLabel(pm).toString() + "\n"); tv.append(i + "-" +
                 * tmpPermInfo.loadLabel(pm).toString()+ "\n"); tv.append(i +
                 * "-" + tmpPermInfo.loadDescription(pm).toString()+ "\n");
                 */

                for (int j = 0; j < permission.length; j++) {
                    if (String.valueOf(permName).equalsIgnoreCase(permission[j])) {
                        pCount--;
                        if (pCount == 0) {
                            return true;
                        }
                        continue;
                    }
                }
            }
        } catch (NameNotFoundException e) {
            if (LogUtil.DEBUG) {
                LogUtil.e(Lib_Util_System.class.getSimpleName(), "包名不存在");
                LogUtil.w(e);
            }

        }
        return false;
    }

    /**
     * 判断当前应用程序是否后台运行
     */
    public static boolean isBackgroundRunable(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
                    // 后台运行
                    return true;
                } else {
                    // 前台运行
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * 判断手机是否处理睡眠
     */
    public static boolean isSleeping(Context context) {
        KeyguardManager kgMgr = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        boolean isSleeping = kgMgr.inKeyguardRestrictedInputMode();
        return isSleeping;
    }

    /**
     * 启动对应包名的应用
     *
     * @param context
     * @param packageName
     */
    public static void startAppByPackageName(Context context, String packageName) {
        PackageInfo pi = null;
        try {
            pi = context.getPackageManager().getPackageInfo(packageName, 0);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }

        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(pi.packageName);

        List<ResolveInfo> apps = context.getPackageManager().queryIntentActivities(resolveIntent, 0);

        ResolveInfo ri = apps.iterator().next();
        if (ri != null) {
            String packageName1 = ri.activityInfo.packageName;
            String className = ri.activityInfo.name;

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);

            ComponentName cn = new ComponentName(packageName1, className);

            intent.setComponent(cn);
            context.startActivity(intent);
        }

    }

    /**
     * @return 是否运行在 主线程
     */
    public static boolean isInMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    public static String getMetaDataFromApplication(Context activity, String name) {
        try {
            ApplicationInfo appInfo = activity.getPackageManager()
                    .getApplicationInfo(activity.getPackageName(),
                            PackageManager.GET_META_DATA);
            String mTag = appInfo.metaData.getString(name);
            return mTag;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
    // 设置字体
    // Typeface mFace =
    // Typeface.createFromAsset(getContext().getAssets(),"fonts/samplefont.ttf");
    // mPaint.setTypeface(mFace);
}
