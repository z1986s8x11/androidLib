package org.zsx.android.api;


import android.app.Application;

import com.zsx.app.ZsxApplicationManager;
import com.zsx.debug.LogUtil;

public class _BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.DEBUG = true;
        ZsxApplicationManager.builder(this).setFileManagerDir("Api");
    }
}
