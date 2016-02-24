package com.zsx.network;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.zsx.debug.LogUtil;
import com.zsx.util._NetworkUtil;
import com.zsx.util.Lib_Util_System;

/**
 * 是一个检测网络状态改变的，需要配置 <code> <receiver
 * android:name="com.ta.util.netstate.TANetworkStateReceiver" >
 * <intent-filter> <action
 * android:name="android.net.conn.CONNECTIVITY_CHANGE" /> <action
 * android:name="lib.android.net.conn.CONNECTIVITY_CHANGE" />
 * </intent-filter> </receiver>
 * <p/>
 * 需要开启权限 <uses-permission
 * android:name="android.permission.CHANGE_NETWORK_STATE" />
 * <uses-permission
 * android:name="android.permission.CHANGE_WIFI_STATE" />
 * <uses-permission
 * android:name="android.permission.ACCESS_NETWORK_STATE" />
 * <uses-permission
 * android:name="android.permission.ACCESS_WIFI_STATE" />
 * </code>
 *
 * @author zsx
 * @date 2013-5-5 下午 22:47
 */
public class Lib_NetworkStateReceiver extends BroadcastReceiver {
    public static _NetworkUtil.NetType _Current_NetWork_Status = _NetworkUtil.NetType.Default;
    private final static String ANDROID_NET_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equalsIgnoreCase(ANDROID_NET_CHANGE_ACTION)) {
            if (LogUtil.DEBUG) {
                LogUtil.d(Lib_NetworkStateReceiver.this, "网络状态改变.");
            }
            if (!_NetworkUtil.isNetworkConnected(context)) {
                if (LogUtil.DEBUG) {
                    LogUtil.d(Lib_NetworkStateReceiver.this, "没有网络连接.");
                }
                _Current_NetWork_Status = _NetworkUtil.NetType.NoneNet;
            } else {
                _Current_NetWork_Status = _NetworkUtil.getAPNType(context);
                if (LogUtil.DEBUG) {
                    LogUtil.d(Lib_NetworkStateReceiver.this, "网络连接成功." + _Current_NetWork_Status.name());
                }
            }
        }
    }

    /**
     * 注册网络状态广播
     *
     * @param mContext
     */
    public void registerNetworkStateReceiver(Context mContext) {
        if (Lib_Util_System.isPermission(mContext, Manifest.permission.ACCESS_NETWORK_STATE)) {
            IntentFilter filter = new IntentFilter();
            filter.addAction(ANDROID_NET_CHANGE_ACTION);
            mContext.getApplicationContext().registerReceiver(this, filter);
        } else {
            if (LogUtil.DEBUG) {
                LogUtil.e(this, "需要权限:" + Manifest.permission.ACCESS_NETWORK_STATE);
            }
        }
    }

    /**
     * 注销网络状态广播
     *
     * @param mContext
     */
    public void unRegisterNetworkStateReceiver(Context mContext) {
        mContext.getApplicationContext().unregisterReceiver(this);
    }
}