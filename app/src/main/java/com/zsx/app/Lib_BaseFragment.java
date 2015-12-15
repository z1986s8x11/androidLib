package com.zsx.app;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zsx.itf.Lib_LifeCycle;
import com.zsx.itf.Lib_OnCancelListener;
import com.zsx.itf.Lib_OnCycleListener;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class Lib_BaseFragment extends Fragment implements Lib_LifeCycle {
    public static final String _EXTRA_Serializable = Lib_BaseActivity._EXTRA_Serializable;
    public static final String _EXTRA_ListSerializable = Lib_BaseActivity._EXTRA_ListSerializable;
    public static final String _EXTRA_String = Lib_BaseActivity._EXTRA_String;
    public static final String _EXTRA_Strings = Lib_BaseActivity._EXTRA_Strings;
    public static final String _EXTRA_Integer = Lib_BaseActivity._EXTRA_Integer;
    public static final String _EXTRA_Boolean = Lib_BaseActivity._EXTRA_Boolean;
    public static final String _EXTRA_Double = Lib_BaseActivity._EXTRA_Double;
    public static final String _EXTRA_String_ID = Lib_BaseActivity._EXTRA_Strig_ID;
    private Toast toast;
    /**
     * 基于Activity生命周期回调
     */
    private Set<Lib_OnCancelListener> cancelListener = new HashSet<Lib_OnCancelListener>();
    private Set<Lib_OnCycleListener> cycleListener = new HashSet<Lib_OnCycleListener>();

    @Override
    public void _addOnCancelListener(Lib_OnCancelListener listener) {
        if (cancelListener.contains(listener)) {
            return;
        }
        cancelListener.add(listener);
    }

    @Override
    public void _removeOnCancelListener(Lib_OnCancelListener listener) {
        cancelListener.remove(listener);
    }

    @Override
    public void _addOnCycleListener(Lib_OnCycleListener listener) {
        if (cycleListener.contains(listener)) {
            return;
        }
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
    public void onDetach() {
        super.onDetach();
        for (Lib_OnCancelListener l : cancelListener) {
            l.onCancel();
        }
        cancelListener.clear();
//        try {
//            //参数是固定写法
//            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
//            childFragmentManager.setAccessible(true);
//            childFragmentManager.set(this, null);
//        } catch (NoSuchFieldException e) {
//            throw new RuntimeException(e);
//        } catch (IllegalAccessException e) {
//            throw new RuntimeException(e);
//        }
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

    /**
     * 拿到屏幕的高度
     */
    public int _getFullScreenHeight() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        return displayMetrics.heightPixels;
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
}
