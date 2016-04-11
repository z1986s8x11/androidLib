package org.zsx.android.api;


import android.app.Application;

import com.zsx.app.ZsxApplicationManager;
import com.zsx.debug.LogUtil;
import com.zsx.util.Lib_Util_System;

public class _BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.DEBUG = true;
        ZsxApplicationManager.builder(this).setMonitorNet(true).setUncaughtException(true);
        LogUtil.e(this, Lib_Util_System.getMetaDataFromApplication(this, "ZHUSX"));
    }
}
