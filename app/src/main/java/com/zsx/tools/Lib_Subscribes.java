package com.zsx.tools;

import android.os.Handler;
import android.os.Looper;

import com.zsx.debug.LogUtil;

/**
 * Created by zhusx on 2015/11/25.
 */
public class Lib_Subscribes {
    public static <T> void subscribe(final Subscriber<T> subscriber) {
        final Handler mHandler = new Handler(Looper.getMainLooper());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final T t = subscriber.doInBackground();
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
                } catch (final Exception e) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            subscriber.onError(e);
                        }
                    });
                }
            }
        }).start();
    }

    public abstract class Subscriber<T> {
        public void onComplete(T t) {
        }

        public abstract T doInBackground();

        public void onError(Throwable t) {
        }
    }
}
