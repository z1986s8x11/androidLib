package com.zsx.debug;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zsx.app.Lib_BaseFragment;
import com.zsx.itf.Lib_OnCycleListener;
import com.zsx.tools.Lib_Subscribes;
import com.zsx.util.Lib_Util_System;

/**
 * Created by zhusx on 2015/12/13.
 */
public class P_LogCatFragment extends Lib_BaseFragment {
    TextView infoTV;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout rootView = new LinearLayout(inflater.getContext());
        rootView.setOrientation(LinearLayout.VERTICAL);
        _addOnCycleListener(new Lib_OnCycleListener() {
            @Override
            public void onResume() {
                registerForContextMenu(infoTV);
            }

            @Override
            public void onPause() {
                unregisterForContextMenu(infoTV);
            }
        });
        infoTV = new TextView(inflater.getContext());
        infoTV.setMovementMethod(ScrollingMovementMethod.getInstance());
        infoTV.setClickable(true);
        infoTV.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 8);
        rootView.addView(infoTV);
        Lib_Subscribes.subscribe(new Lib_Subscribes.Subscriber<String>() {
            @Override
            public String doInBackground() {
                return Lib_Util_System.getLogCatForLogUtil();
            }

            @Override
            public void onComplete(String s) {
                if (getActivity() != null) {
                    infoTV.setText(s);
                }
            }
        });
        return rootView;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(1, 1, 1, "复制");
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getGroupId()) {
            case 1:
                if (item.getItemId() == 1) {
                    Lib_Util_System.copy(getActivity(), infoTV.getText().toString());
                }
                break;
        }
        return super.onContextItemSelected(item);
    }
}
