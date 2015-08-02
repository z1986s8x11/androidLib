package com.zsx.manager;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;

import com.zsx.debug.LogUtil;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Lib_SystemExitManager {
    private static List<SoftReference<Activity>> list;

    public static void addActivity(Activity activity) {
        if (list == null) {
            list = new ArrayList<SoftReference<Activity>>();
        }
        SoftReference<Activity> sr = new SoftReference<Activity>(activity);
        list.add(sr);
    }

    public static int size() {
        return list.size();
    }

    public static synchronized void removeActivity(Activity activity) {
        if (list == null) {
            return;
        }
        for (int i = list.size() - 1; i >= 0; i--) {
            if (list.get(i) == null) {
                continue;
            }
            if (list.get(i).get() == activity) {
                list.remove(i);
                return;
            }
        }
    }

    public static Activity getLastActivity() {
        if (list.isEmpty()) {
            return null;
        }
        return list.get(list.size() - 1).get();
    }

    public static synchronized void exitSystem() {
        exitSystem(true);
    }

    public static synchronized void exitSystem(Boolean isKillProcess) {
        if (list == null) {
            return;
        }
        for (int i = list.size() - 1; i >= 0; i--) {
            SoftReference<Activity> sr = list.get(i);
            if (sr != null) {
                if (sr.get() != null) {
                    sr.get().finish();
                }
            }
        }
        // 在有后台Service需要运行的时候 不能完全杀死进程，否则Service也将被kill
        if (isKillProcess) {
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        }
        System.gc();
    }

    public static boolean isTopActivity(Context context) {
        if (context == null) {
            return false;
        }
        boolean isTop = false;
        try {
            ActivityManager am = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
            if (context.getClass().getName().equals(cn.getClassName())) {
                isTop = true;
            }
        } catch (Exception e) {
            LogUtil.e(Lib_SystemExitManager.class,
                    "requires android.permission.GET_TASKS");
            isTop = false;
        }
        return isTop;
    }
}
