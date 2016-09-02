package com.zsx.debug;

import android.text.TextUtils;
import android.util.Log;

import com.zsx.BuildConfig;


/**
 * @author zsx
 * @date 2013-12-18
 * @description
 */
public class LogUtil {
    public static boolean DEBUG = BuildConfig.DEBUG;
    private final static String TAG = "[Log]";

    /**
     * @param cls
     * @param message
     */
    public static void e(Object cls, String message) {
        e(cls.getClass().getSimpleName(), message);
    }

    /**
     * @param tag
     * @param message
     */
    public static void e(String tag, String message) {
        if (DEBUG) {
            if (TextUtils.isEmpty(message)) {
                Log.e(TAG + tag, String.valueOf(message));
                return;
            }
            int index = 0;
            int maxLength = 4000;
            String sub;
            while (index < message.length()) {
                // java的字符不允许指定超过总的长度end
                if (message.length() <= index + maxLength) {
                    sub = message.substring(index);
                } else {
                    sub = message.substring(index, maxLength + index);
                }
                index += maxLength;
                Log.e(TAG + tag, sub.trim());
            }
        }
    }

    /**
     * @param cls
     * @param message
     */
    public static void e(Class<?> cls, String message) {
        e(cls.getSimpleName(), message);
    }

    /**
     * @param cls
     * @param message
     */
    public static void d(Object cls, String message) {
        if (DEBUG) {
            if (message == null) {
                return;
            }
            Log.e(TAG + cls.getClass().getSimpleName(), message);
        }
    }

    /**
     * @param tag
     * @param message
     */
    public static void d(String tag, String message) {
        if (DEBUG) {
            if (message == null) {
                return;
            }
            Log.e(TAG + tag, message);
        }
    }

    /**
     * @param cls
     * @param message
     */
    public static void i(Object cls, String message) {
        if (DEBUG) {
            if (message == null) {
                return;
            }
            Log.i(TAG + cls.getClass().getSimpleName(), message);
        }
    }

    /**
     * @param tag
     * @param message
     */
    public static void i(String tag, String message) {
        if (DEBUG) {
            if (message == null) {
                return;
            }
            Log.i(TAG + tag, message);
        }
    }

    /**
     * @param tr
     */
    public static void w(Throwable tr) {
        if (DEBUG) {
            tr.printStackTrace();
        }
    }

    public static void e(Throwable tr) {
        if (DEBUG) {
            Log.e(TAG, "ERROR:", tr);
        }
    }
}
