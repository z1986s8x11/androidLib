package com.zsx.app;

import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;

import com.zsx.debug.Lib_UncaughtException;
import com.zsx.debug.LogUtil;
import com.zsx.manager.Lib_FileManager;
import com.zsx.network.Lib_NetworkStateReceiver;


/**
 * Created by zhusx on 2015/8/5.
 */
public class ZsxApplicationManager {
    private ZsxApplicationManager() {
    }

    public static ZsxApplicationManager.Builder builder(Application app) {
        return new Builder(app);
    }

    public static class Builder {
        private Application context;
        private boolean monitorNet;
        private boolean uncaughtException;
        private String fileManagerDir;
        private Application.ActivityLifecycleCallbacks activityCallbacks;
        private Lib_NetworkStateReceiver receiver;

        private Builder(Application app) {
            this.context = app;
        }

        public ZsxApplicationManager build() {
            init();
            return new ZsxApplicationManager();
        }

        /**
         * 设置是否监听网络变化
         */
        public Builder setMonitorNet(boolean monitorNet) {
            this.monitorNet = monitorNet;
            return this;
        }


        /**
         * 设置是否启用捕获全局异常
         */
        public Builder setUncaughtException(boolean uncaught) {
            this.uncaughtException = uncaught;
            return this;
        }


        /**
         * 设置存储数据的文件夹目录名称
         */
        public Builder setFileManagerDir(String dir) {
            this.fileManagerDir = dir;
            return this;
        }

        private void init() {
            if (fileManagerDir != null) {
                if (!TextUtils.isEmpty(fileManagerDir.trim())) {
                    /* 初始化文件系统 (创建目录) */
                    Lib_FileManager.init(fileManagerDir, context);
                }
            }
            /*监听网络变化*/
            if (monitorNet) {
                receiver = new Lib_NetworkStateReceiver();
                receiver.registerNetworkStateReceiver(context);
            }
            /*必须 DEBUG 下*/
            if (LogUtil.DEBUG) {
                if (uncaughtException) {
                /* 监听全局异常 */
                    Lib_UncaughtException._getInstance()._init(context);
                }
                StrictMode.enableDefaults();
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                activityCallbacks = new Application.ActivityLifecycleCallbacks() {
                    @Override
                    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                        LogUtil.e(activity, "onActivityCreated");
                    }

                    @Override
                    public void onActivityStarted(Activity activity) {
                        LogUtil.e(activity, "onActivityStarted");
                    }

                    @Override
                    public void onActivityResumed(Activity activity) {
                        LogUtil.e(activity, "onActivityResumed");
                    }

                    @Override
                    public void onActivityPaused(Activity activity) {
                        LogUtil.e(activity, "onActivityPaused");
                    }

                    @Override
                    public void onActivityStopped(Activity activity) {
                        LogUtil.e(activity, "onActivityStopped");
                    }

                    @Override
                    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                        LogUtil.e(activity, "onActivitySaveInstanceState");
                    }

                    @Override
                    public void onActivityDestroyed(Activity activity) {
                        LogUtil.e(activity, "onActivityDestroyed");
                    }
                };
                context.registerActivityLifecycleCallbacks(activityCallbacks);
            }
        }

        public void onTerminate() {
            if (receiver != null) {
                receiver.unRegisterNetworkStateReceiver(context);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                if (activityCallbacks != null) {
                    context.unregisterActivityLifecycleCallbacks(activityCallbacks);
                }
            }
            receiver = null;
            context = null;
            activityCallbacks = null;
        }
    }
}
