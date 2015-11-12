package zsx.com.test;

import android.app.Application;

import com.zsx.app.ZsxApplicationManager;
import com.zsx.debug.LogUtil;
import com.zsx.util.Lib_Util_File;

/**
 * Created by zhusx on 2015/8/5.
 */
public class TestApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.DEBUG = true;
        if (LogUtil.DEBUG) {
            LogUtil.e(this, "onCreate");
        }
        ZsxApplicationManager.builder(this).setMonitorNet(true).setUncaughtException(true).build();
        Lib_Util_File.createFileDir(this,"Lib");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        if (LogUtil.DEBUG) {
            LogUtil.e(this, "onTerminate");
        }
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
