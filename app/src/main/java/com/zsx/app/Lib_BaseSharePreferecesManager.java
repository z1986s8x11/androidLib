package com.zsx.app;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import java.util.Set;

/**
 * Author       zhusx
 * Email        327270607@qq.com
 * Created      2016/8/4 17:31
 */
public class Lib_BaseSharePreferecesManager<T extends Enum> {
    private SharedPreferences pSharedPreferences;

    protected Lib_BaseSharePreferecesManager(SharedPreferences pSharedPreferences) {
        this.pSharedPreferences = pSharedPreferences;
    }

    protected Lib_BaseSharePreferecesManager(Context context, String fileName) {
        this.pSharedPreferences = createSharedPreferences(context, fileName);
    }

    private SharedPreferences createSharedPreferences(Context context, String suffix) {
        return context.getSharedPreferences(context.getPackageName() + suffix, Context.MODE_PRIVATE);
    }

    //-----------------------泛型处理-------------------------//
    public int get(T key, int defValue) {
        return pSharedPreferences.getInt(__getEnumKey(key), defValue);
    }

    public float get(T key, float defValue) {
        return pSharedPreferences.getFloat(__getEnumKey(key), defValue);
    }

    public long get(T key, long defValue) {
        return pSharedPreferences.getLong(__getEnumKey(key), defValue);
    }

    public boolean get(T key, boolean defValue) {
        return pSharedPreferences.getBoolean(__getEnumKey(key), defValue);
    }

    public String get(T key, String defValue) {
        return pSharedPreferences.getString(__getEnumKey(key), defValue);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public Set<String> get(T key, Set<String> defValue) {
        return pSharedPreferences.getStringSet(__getEnumKey(key), defValue);
    }

    public void put(T key, int value) {
        pSharedPreferences.edit().putInt(__getEnumKey(key), value).apply();
    }

    public void put(T key, float value) {
        pSharedPreferences.edit().putFloat(__getEnumKey(key), value).apply();
    }

    public void put(T key, long value) {
        pSharedPreferences.edit().putLong(__getEnumKey(key), value).apply();
    }

    public void put(T key, boolean value) {
        pSharedPreferences.edit().putBoolean(__getEnumKey(key), value).apply();
    }

    public void put(T key, String value) {
        pSharedPreferences.edit().putString(__getEnumKey(key), value).apply();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void put(T key, Set<String> value) {
        pSharedPreferences.edit().putStringSet(__getEnumKey(key), value).apply();
    }

    protected String __getEnumKey(T key) {
        return key.name() + key.ordinal();
    }

}
