package com.zsx.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zsx.itf.Lib_LifeCycle;
import com.zsx.itf.Lib_OnCancelListener;
import com.zsx.itf.Lib_OnCycleListener;
import com.zsx.manager.Lib_SystemExitManager;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class Lib_BaseFragment extends Fragment implements Lib_LifeCycle {
    private Toast toast;
    /**
     * 基于Activity生命周期回调
     */
    private Set<Lib_OnCancelListener> cancelListener = new HashSet<Lib_OnCancelListener>();
    private Set<Lib_OnCycleListener> cycleListener = new HashSet<Lib_OnCycleListener>();
    private Handler pHandler = new Handler();
    @Override
    public void _addOnCancelListener(Lib_OnCancelListener listener) {
        cancelListener.add(listener);
    }

    @Override
    public void _removeOnCancelListener(Lib_OnCancelListener listener) {
        cancelListener.remove(listener);
    }

    @Override
    public void _addOnCycleListener(Lib_OnCycleListener listener) {
        cycleListener.add(listener);
    }

    @Override
    public void _removeOnCycleListener(Lib_OnCycleListener listener) {
        cycleListener.remove(listener);
    }

    @Override
    public void onResume() {
        super.onResume();
        for (Lib_OnCycleListener l : cycleListener) {
            l.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        for (Lib_OnCycleListener l : cycleListener) {
            l.onPause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        for (Lib_OnCancelListener l : cancelListener) {
            l.onCancel();
        }
        cancelListener.clear();
    }

    public void _showToast(String message) {
        if (TextUtils.isEmpty(message)) {
            return;
        }
        if (getActivity() == null) {
            return;
        }
        if (toast == null) {
            toast = Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
        }
        toast.setText(message);
        toast.show();
    }

    public int _getFullScreenWidth() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    protected final void _removeParentView(View view) {
        if (view != null) {
            // 缓存的rootView需要判断是否已经被加过parent，
            // 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<Fragment> list = getChildFragmentManager().getFragments();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                list.get(i).onActivityResult(requestCode, resultCode, data);
            }
        }
    }
    public void _setAutoPlayForAlways(Runnable runnable, long time) {
        final DelayRunnable delayRunnable = new DelayRunnable(runnable, time);
        _addOnCancelListener(delayRunnable);
        pHandler.postDelayed(delayRunnable, time);
    }
    public void _setAutoPlayForCanPause(Runnable runnable, long time) {
        final DelayRunnable delayRunnable = new DelayRunnable(runnable, time);
        _addOnCancelListener(delayRunnable);
        _addOnCancelListener(delayRunnable);
        pHandler.postDelayed(delayRunnable, time);
    }

    private class DelayRunnable implements Runnable,Lib_OnCycleListener,Lib_OnCancelListener{
        private Runnable r;
        private long time;
        private boolean isExit;
        private boolean isPause;
        public DelayRunnable(Runnable r, long time) {
            this.r = r;
            this.time = time;
        }

        @Override
        public void run() {
            if (isExit) {
                return;
            }
            if(!isPause){
                r.run();
            }
            pHandler.postDelayed(this, time);
        }

        @Override
        public void onCancel() {
            this.isExit = true;
        }

        @Override
        public void onResume() {
            isPause=false;
        }

        @Override
        public void onPause() {
            isPause = true;
        }
    }
}
