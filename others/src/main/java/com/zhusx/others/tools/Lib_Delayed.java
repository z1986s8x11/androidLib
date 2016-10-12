package com.zhusx.others.tools;

/**
 * Created by Administrator on 2015/9/28.
 */
//public class Lib_Delayed {
//    private Lib_LifeCycle lifeCycle;
//
//    public Lib_Delayed(Lib_LifeCycle lifeCycle) {
//        this.lifeCycle = lifeCycle;
//    }
//
//    private Handler pHandler = new Handler();
//    private Map<Runnable, DelayRunnable> cancelListener = new HashMap<>();
//    private Map<Runnable, DelayRunnable> cycleListener = new HashMap<>();
//
//    public void _setAutoPlayForAlways(Runnable runnable, long time) {
//        if (cancelListener.containsKey(runnable)) {
//            return;
//        }
//        final DelayRunnable delayRunnable = new DelayRunnable(runnable, time);
//        lifeCycle._addOnCancelListener(delayRunnable);
//        pHandler.postDelayed(delayRunnable, time);
//        cancelListener.put(runnable, delayRunnable);
//    }
//
//    public void _setAutoPlayForCanPause(Runnable runnable, long time) {
//        if (cycleListener.containsKey(runnable)) {
//            return;
//        }
//        final DelayRunnable delayRunnable = new DelayRunnable(runnable, time);
//        lifeCycle._addOnCancelListener(delayRunnable);
//        cycleListener.put(runnable, delayRunnable);
//        if (cancelListener.containsKey(runnable)) {
//            return;
//        }
//        lifeCycle._addOnCycleListener(delayRunnable);
//        cancelListener.put(runnable, delayRunnable);
//        pHandler.postDelayed(delayRunnable, time);
//    }
//
//    public void _cancelAutoPlay(Runnable runnable) {
//        DelayRunnable d;
//        if (cycleListener.containsKey(runnable)) {
//            d = cycleListener.get(runnable);
//            d.onPause();
//
//        }
//        if (cancelListener.containsKey(runnable)) {
//            d = cancelListener.get(runnable);
//            d.onCancel();
//            cancelListener.remove(runnable);
//            pHandler.removeCallbacks(runnable);
//        }
//
//    }
//
//    private class DelayRunnable implements Runnable, Lib_OnCycleListener, Lib_OnCancelListener {
//        private Runnable r;
//        private long time;
//        private boolean isExit;
//        private boolean isPause;
//
//        public DelayRunnable(Runnable r, long time) {
//            this.r = r;
//            this.time = time;
//        }
//
//        @Override
//        public void run() {
//            if (isExit) {
//                return;
//            }
//            long start = System.currentTimeMillis();
//            if (!isPause) {
//                r.run();
//            }
//            long expendTime = System.currentTimeMillis() - start;
//            pHandler.postDelayed(this, time - expendTime);
//        }
//
//        @Override
//        public void onCancel() {
//            this.isExit = true;
//        }
//
//        @Override
//        public void onResume() {
//            isPause = false;
//        }
//
//        @Override
//        public void onPause() {
//            isPause = true;
//        }
//
//        @Override
//        public void onDestroy() {
//            this.isExit = true;
//        }
//    }
//}
