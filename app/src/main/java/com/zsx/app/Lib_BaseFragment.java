package com.zsx.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

public abstract class Lib_BaseFragment extends Fragment {
    public void _showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    public int _getFullScreenWidth() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    protected View mFragmentView;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (mFragmentView != null) {
            // 缓存的rootView需要判断是否已经被加过parent，
            // 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
            ViewGroup parent = (ViewGroup) mFragmentView.getParent();
            if (parent != null) {
                parent.removeView(mFragmentView);
            }
            return mFragmentView;
        }
        return mFragmentView = __onCreateView(inflater, container,
                savedInstanceState);
    }

    protected abstract View __onCreateView(LayoutInflater inflater,
                                           ViewGroup container, Bundle savedInstanceState);

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
