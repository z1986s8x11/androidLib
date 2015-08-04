package com.zsx.app;

import android.app.Application;
import android.os.StrictMode;

import com.zsx.debug.GlobalException;
import com.zsx.debug.LogUtil;
import com.zsx.manager.Lib_FileManager;
import com.zsx.network.NetChangeObserver;
import com.zsx.network.NetworkState;
import com.zsx.network.NetworkStateReceiver;

/**
 * Application 基类
 * <p/>
 * <p/>
 * Created by zhusx on 2015/7/31.
 */
public abstract class Lib_BaseApplication extends Application {
    private HttpObject httpState;
    /**
     * 当前链接状态
     */
    public static NetworkState.NetType _Current_NetWork_Status = NetworkState.NetType.Default;

    @Override
    public void onCreate() {
        super.onCreate();
        /** 初始化文件系统 (创建目录) */
        Lib_FileManager.init(_getProjectName(), getApplicationContext());
        /** 注册网络改变监听 */
        NetworkStateReceiver.registerNetworkStateReceiver(this);
        /** 注册接受网络改变观察者 */
        httpState = new HttpObject();
        NetworkStateReceiver.registerObserver(httpState);
        if (LogUtil.DEBUG) {
            StrictMode.enableDefaults();
            /** 监听全局异常 */
            GlobalException._getInstance()._init(this);
        }
    }

    /***
     * @return 存储数据的文件夹名称
     */
    protected abstract String _getProjectName();

    @Override
    public void onTerminate() {
        NetworkStateReceiver.removeRegisterObserver(httpState);
        NetworkStateReceiver.unRegisterNetworkStateReceiver(this);
    }

    private class HttpObject implements NetChangeObserver {

        @Override
        public void onConnect(NetworkState.NetType type) {
            _Current_NetWork_Status = type;
        }

        @Override
        public void onDisConnect() {
            _Current_NetWork_Status = NetworkState.NetType.NoneNet;
        }
    }

}
