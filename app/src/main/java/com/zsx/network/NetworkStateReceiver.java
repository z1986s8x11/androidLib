package com.zsx.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.zsx.debug.LogUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

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
public class NetworkStateReceiver extends BroadcastReceiver {
    private static Boolean networkAvailable = false;
    private static NetworkState.NetType netType = NetworkState.NetType.NoneNet;
    private static List<WeakReference<NetChangeObserver>> netChangeObserverArrayList;
    private final static String ANDROID_NET_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
    public final static String TA_ANDROID_NET_CHANGE_ACTION = "lib.android.net.conn.CONNECTIVITY_CHANGE";
    private static BroadcastReceiver receiver;

    private static BroadcastReceiver getReceiver() {
        if (receiver == null) {
            receiver = new NetworkStateReceiver();
        }
        return receiver;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        receiver = NetworkStateReceiver.this;
        if (intent.getAction().equalsIgnoreCase(ANDROID_NET_CHANGE_ACTION) || intent.getAction().equalsIgnoreCase(TA_ANDROID_NET_CHANGE_ACTION)) {
            if (LogUtil.DEBUG) {
                LogUtil.d(NetworkStateReceiver.this, "网络状态改变.");
            }
            if (!NetworkState.isNetworkConnected(context)) {
                if (LogUtil.DEBUG) {
                    LogUtil.d(NetworkStateReceiver.this, "没有网络连接.");
                }
                networkAvailable = false;
            } else {
                netType = NetworkState.getAPNType(context);
                if (LogUtil.DEBUG) {
                    LogUtil.d(NetworkStateReceiver.this, "网络连接成功." + netType.name());
                }
                networkAvailable = true;
            }
            notifyObserver();
        }
    }

    /**
     * 注册网络状态广播
     *
     * @param mContext
     */
    public static void registerNetworkStateReceiver(Context mContext) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(TA_ANDROID_NET_CHANGE_ACTION);
        filter.addAction(ANDROID_NET_CHANGE_ACTION);
        mContext.getApplicationContext().registerReceiver(getReceiver(), filter);
    }

    /**
     * 检查网络状态
     *
     * @param mContext
     */
    public static void checkNetworkState(Context mContext) {
        Intent intent = new Intent();
        intent.setAction(TA_ANDROID_NET_CHANGE_ACTION);
        mContext.sendBroadcast(intent);
    }

    /**
     * 注销网络状态广播
     *
     * @param mContext
     */
    public static void unRegisterNetworkStateReceiver(Context mContext) {
        if (receiver != null) {
            try {
                mContext.getApplicationContext().unregisterReceiver(receiver);
            } catch (Exception e) {
                LogUtil.w(e);
            }
        }

    }

    /**
     * 获取当前网络状态，true为网络连接成功，否则网络连接失败
     *
     * @return
     */
    public static Boolean isNetworkAvailable() {
        return networkAvailable;
    }

    public static NetworkState.NetType getAPNType() {
        return netType;
    }

    private void notifyObserver() {
        if (netChangeObserverArrayList == null) {
            return;
        }
        for (int i = 0; i < netChangeObserverArrayList.size(); i++) {
            WeakReference<NetChangeObserver> weakObserver = netChangeObserverArrayList.get(i);
            if (weakObserver != null) {
                NetChangeObserver observer = weakObserver.get();
                if (observer != null) {
                    if (isNetworkAvailable()) {
                        observer.onConnect(netType);
                    } else {
                        observer.onDisConnect();
                    }
                }
            }
        }

    }

    /**
     * 注册网络连接观察者
     * <p/>
     * observerKey
     */
    public static void registerObserver(NetChangeObserver observer) {
        if (netChangeObserverArrayList == null) {
            netChangeObserverArrayList = new ArrayList<WeakReference<NetChangeObserver>>();
        }
        netChangeObserverArrayList.add(new WeakReference<NetChangeObserver>(observer));
    }

    /**
     * 注销网络连接观察者
     * <p/>
     * observerKey
     */
    public static void removeRegisterObserver(NetChangeObserver observer) {
        if (netChangeObserverArrayList != null) {
            netChangeObserverArrayList.remove(observer);
        }
    }

}