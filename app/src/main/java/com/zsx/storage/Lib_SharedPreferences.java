package com.zsx.storage;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import java.util.Set;

/**
 * Created by zhusx on 2016/1/4.
 */
public class Lib_SharedPreferences {
    private static Lib_SharedPreferences lib_preferences;
    private SharedPreferences sharedPreferences;

    private Lib_SharedPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(getStorageName(context), Context.MODE_PRIVATE);
    }

    public static Lib_SharedPreferences getInstance(Context context) {
        if (lib_preferences != null) {
            return lib_preferences;
        }
        return lib_preferences = new Lib_SharedPreferences(context.getApplicationContext());
    }

    /**
     * 保存数据是否异步提交
     */
    protected boolean __isAsync() {
        return true;
    }

    protected String __getEnumKey(Enum<?> key) {
        return key.name() + key.ordinal();
    }

    protected String getStorageName(Context context) {
        return context.getPackageName() + "_lib";
    }

    public final String get(String key, String defValue) {
        return sharedPreferences.getString(key, defValue);
    }

    public final int get(String key, int defValue) {
        return sharedPreferences.getInt(key, defValue);
    }

    public final long get(String key, long defValue) {
        return sharedPreferences.getLong(key, defValue);
    }

    public final float get(String key, float defValue) {
        return sharedPreferences.getFloat(key, defValue);
    }

    public boolean get(String key, boolean defValue) {
        return sharedPreferences.getBoolean(key, defValue);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public final Set<String> get(String key, Set<String> defValue) {
        return sharedPreferences.getStringSet(key, defValue);
    }

    public final void put(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        if (__isAsync()) {
            editor.apply();//异步提交
        } else {
            editor.commit();//同步提交
        }
    }

    public final void put(String key, int value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        if (__isAsync()) {
            editor.apply();//异步提交
        } else {
            editor.commit();//同步提交
        }
    }

    public final void put(String key, boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        if (__isAsync()) {
            editor.apply();//异步提交
        } else {
            editor.commit();//同步提交
        }
    }

    public final void put(String key, float value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(key, value);
        if (__isAsync()) {
            editor.apply();//异步提交
        } else {
            editor.commit();//同步提交
        }
    }

    public final void put(String key, long value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        if (__isAsync()) {
            editor.apply();//异步提交
        } else {
            editor.commit();//同步提交
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public final void put(String key, Set<String> value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(key, value);
        if (__isAsync()) {
            editor.apply();//异步提交
        } else {
            editor.commit();//同步提交
        }
    }

    //-----------------------泛型处理-------------------------//

    public int get(Enum<?> key, int defValue) {
        return get(__getEnumKey(key), defValue);
    }

    public float get(Enum<?> key, float defValue) {
        return get(__getEnumKey(key), defValue);
    }

    public long get(Enum<?> key, long defValue) {
        return get(__getEnumKey(key), defValue);
    }

    public boolean get(Enum<?> key, boolean defValue) {
        return get(__getEnumKey(key), defValue);
    }

    public String get(Enum<?> key, String defValue) {
        return get(__getEnumKey(key), defValue);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public Set<String> get(Enum<?> key, Set<String> defValue) {
        return get(__getEnumKey(key), defValue);
    }

    public void put(Enum<?> key, int value) {
        put(__getEnumKey(key), value);
    }

    public void put(Enum<?> key, float value) {
        put(__getEnumKey(key), value);
    }

    public void put(Enum<?> key, long value) {
        put(__getEnumKey(key), value);
    }

    public void put(Enum<?> key, boolean value) {
        put(__getEnumKey(key), value);
    }

    public void put(Enum<?> key, String value) {
        put(__getEnumKey(key), value);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void put(Enum<?> key, Set<String> value) {
        put(__getEnumKey(key), value);
    }
}
