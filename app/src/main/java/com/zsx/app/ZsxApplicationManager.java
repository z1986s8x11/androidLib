package com.zsx.app;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.os.StrictMode;

import com.zsx.debug.LogUtil;
import com.zsx.debug.P_UncaughtException;
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
        private boolean safety;
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

        public Builder setSafety(boolean safety) {
            this.safety = safety;
            return this;
        }

        private void init() {
            /*监听网络变化*/
            if (monitorNet) {
                receiver = new Lib_NetworkStateReceiver();
                receiver.registerNetworkStateReceiver(context);
            }
            /*必须 DEBUG 下*/
            if (LogUtil.DEBUG) {
                if (uncaughtException) {
                /* 监听全局异常 */
                    P_UncaughtException._getInstance()._init(context);
                }
                StrictMode.enableDefaults();
            }
            if (safety) {
                if (!LogUtil.DEBUG) {
                    if ((context.getApplicationInfo().flags &= ApplicationInfo.FLAG_DEBUGGABLE) != 0) {
                        //LogUtil.e(this, "程序被修改为可调试状态");
                        android.os.Process.killProcess(android.os.Process.myPid());
                    }
                    if (android.os.Debug.isDebuggerConnected()) {
                        //LogUtil.e(this, "检测到检测器");
                        android.os.Process.killProcess(android.os.Process.myPid());
                    }
                }
            }
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
//                activityCallbacks = new Application.ActivityLifecycleCallbacks() {
//                    @Override
//                    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
//                        Lib_SystemExitManager.addActivity(activity);
//                    }
//
//                    @Override
//                    public void onActivityStarted(Activity activity) {
//                    }
//
//                    @Override
//                    public void onActivityResumed(Activity activity) {
//                        if (activity instanceof Lib_LifeCycle) {
//                            Set<Lib_OnCycleListener> cycleListener = ((Lib_LifeCycle) activity).getCycleListeners();
//                            if (!_Sets.isEmpty(cycleListener)) {
//                                for (Lib_OnCycleListener l : cycleListener) {
//                                    l.onResume();
//                                }
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onActivityPaused(Activity activity) {
//                        if (activity instanceof Lib_LifeCycle) {
//                            Set<Lib_OnCycleListener> cycleListener = ((Lib_LifeCycle) activity).getCycleListeners();
//                            if (!_Sets.isEmpty(cycleListener)) {
//                                for (Lib_OnCycleListener l : cycleListener) {
//                                    l.onPause();
//                                }
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onActivityStopped(Activity activity) {
//                    }
//
//                    @Override
//                    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
//                    }
//
//                    @Override
//                    public void onActivityDestroyed(Activity activity) {
//                        if (activity instanceof Lib_LifeCycle) {
//                            Set<Lib_OnCycleListener> cycleListener = ((Lib_LifeCycle) activity).getCycleListeners();
//                            if (!_Sets.isEmpty(cycleListener)) {
//                                for (Lib_OnCycleListener l : cycleListener) {
//                                    l.onDestroy();
//                                }
//                            }
//                        }
//                        Lib_SystemExitManager.removeActivity(activity);
//                    }
//                };
//                context.registerActivityLifecycleCallbacks(activityCallbacks);
//            }
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
