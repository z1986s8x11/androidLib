package zsx.com.test;

import android.app.Application;

import com.zsx.app.ZsxApplicationManager;
import com.zsx.debug.LogUtil;

/**
 * Created by zhusx on 2015/8/5.
 */
public class TestApplication extends Application {
    ZsxApplicationManager zsx;

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.DEBUG = true;
        if (LogUtil.DEBUG) {
            LogUtil.e(this, "onCreate");
        }
        zsx = new ZsxApplicationManager(this).setMonitorNet(true).setFileManagerDir("Lib").setUncaughtException(true);
        zsx.init();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        if (LogUtil.DEBUG) {
            LogUtil.e(this, "onTerminate");
        }
        zsx.onTerminate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (LogUtil.DEBUG) {
            LogUtil.e(this, "onLowMemory");
        }
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (LogUtil.DEBUG) {
            LogUtil.e(this, "onTrimMemory");
        }
    }
}
