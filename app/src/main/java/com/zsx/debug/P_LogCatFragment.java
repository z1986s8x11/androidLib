package com.zsx.debug;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.zsx.adapter.Lib_BaseAdapter;
import com.zsx.app.Lib_BaseFragment;
import com.zsx.storage.Lib_SharedPreferences;
import com.zsx.tools.Lib_Subscribes;
import com.zsx.util.Lib_Util_Intent;
import com.zsx.util.Lib_Util_System;

import java.util.List;

/**
 * Created by zhusx on 2015/12/13.
 */
public class P_LogCatFragment extends Lib_BaseFragment {
    private int fontSize = 6;
    private Lib_BaseAdapter<String> adapter;
    private boolean isChange;
    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        LinearLayout rootView = new LinearLayout(inflater.getContext());
        rootView.setOrientation(LinearLayout.VERTICAL);
        listView = new ListView(getActivity());
        registerForContextMenu(listView);
        fontSize = Lib_SharedPreferences.getInstance(getActivity()).get("fontSize", 6);
        listView.setAdapter(adapter = new Lib_BaseAdapter<String>(getActivity()) {
            @Override
            public View getView(LayoutInflater inflater, String bean, int position, View convertView, ViewGroup parent) {
                TextView t;
                if (convertView == null) {
                    t = new TextView(inflater.getContext());
                    t.setTextSize(TypedValue.COMPLEX_UNIT_DIP, fontSize);
                } else {
                    t = (TextView) convertView;
                }
                if (isChange) {
                    t.setTextSize(TypedValue.COMPLEX_UNIT_DIP, fontSize);
                }
                t.setText(bean);
                return t;
            }
        });
        TextView emptyView = new TextView(inflater.getContext());
        emptyView.setText("正在初始化...");
        emptyView.setGravity(Gravity.CENTER);
        rootView.addView(listView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        rootView.addView(emptyView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        listView.setEmptyView(emptyView);
        Lib_Subscribes.subscribe(new Lib_Subscribes.Subscriber<List<String>>() {
            @Override
            public List<String> doInBackground() {
                return Lib_Util_System.getLogCatForLogUtil();
            }

            @Override
            public void onComplete(List<String> s) {
                if (getActivity() != null) {
                    adapter._setItemsToUpdate(s);
                }
            }
        }, this);
        setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterForContextMenu(listView);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.add(1, 811, 1, "字体+");
        menu.add(2, 822, 1, "字体-");
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 811:
                fontSize++;
                adapter.notifyDataSetChanged();
                break;
            case 822:
                fontSize--;
                adapter.notifyDataSetChanged();
                break;
        }
        isChange = true;
        Lib_SharedPreferences.getInstance(getContext()).put("fontSize", fontSize);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(1, 1, 1, "复制");
        menu.add(2, 2, 2, "发送");
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getGroupId()) {
            case 1:
                Lib_Util_System.copy(getActivity(), adapter.getItem(menuInfo.position));
                break;
            case 2:
                Lib_Util_Intent.shareMsg(getActivity(), "发送", "LogCat", adapter.getItem(menuInfo.position), null);
                break;
        }
        return super.onContextItemSelected(item);
    }
}
