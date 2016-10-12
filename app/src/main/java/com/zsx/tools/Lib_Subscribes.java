package com.zsx.tools;

import android.os.Handler;
import android.os.Looper;

import com.zsx.debug.LogUtil;
import com.zsx.itf.Lib_LifeCycle;
import com.zsx.itf.Lib_OnCycleListener;
import com.zsx.itf.Lib_Runnable;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zhusx on 2015/11/25.
 */
public class Lib_Subscribes {
    private static Set<Subscriber> subscribes = new LinkedHashSet<>();
    private static ExecutorService executor = Executors.newSingleThreadExecutor();

    public static <T> void subscribe(final Subscriber<T> subscriber) {
        subscribe(subscriber, null);
    }

    public static <T> void subscribe(final Subscriber<T> subscriber, final Lib_LifeCycle lifeCycle) {
        if (subscribes.contains(subscriber)) {
            return;
        }
        subscribes.add(subscriber);
        final CancelRunnable<T> runnable = new CancelRunnable<T>(subscriber);
        if (lifeCycle != null) {
            lifeCycle._addOnCycleListener(new Lib_OnCycleListener() {
                @Override
                public void onResume() {
                }

                @Override
                public void onPause() {
                }

                @Override
                public void onDestroy() {
                    runnable._setCancel();
                }
            });
        }
        executor.execute(runnable);
    }

    public static abstract class Subscriber<T> {
        public void onComplete(T t) {
        }

        public abstract T doInBackground();

        public void onError(Throwable t) {
        }
    }

    private static class CancelRunnable<T> extends Lib_Runnable {
        final Handler mHandler = new Handler(Looper.getMainLooper());
        private Subscriber<T> subscriber;

        public CancelRunnable(Subscriber<T> subscriber) {
            this.subscriber = subscriber;
        }

        @Override
        public void run() {
            try {
                if (!_isCancel()) {
                    final T t = subscriber.doInBackground();
                    if (!_isCancel()) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    subscriber.onComplete(t);
                                } catch (Exception e) {
                                    if (LogUtil.DEBUG) {
                                        LogUtil.e(e);
                                    }
                                }
                            }
                        });
                    }
                }
            } catch (final Exception e) {
                if (!_isCancel()) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            subscriber.onError(e);
                        }
                    });
                }
            } finally {
                subscribes.remove(subscriber);
            }
        }
    }
}
