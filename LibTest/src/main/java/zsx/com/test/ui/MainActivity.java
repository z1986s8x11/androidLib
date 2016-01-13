package zsx.com.test.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import zsx.com.test.R;
import zsx.com.test.base._BaseActivity;
import zsx.com.test.base._BaseAdapter;
import zsx.com.test.ui.adapter.InsertAdapterActivity;
import zsx.com.test.ui.anim.AnimActivity;
import zsx.com.test.ui.debug.ExceptionActivity;
import zsx.com.test.ui.download.DownloadActivity;
import zsx.com.test.ui.network.HttpRequestActivity;
import zsx.com.test.ui.network.HttpUrlConnectActivity;
import zsx.com.test.ui.network.LoadDataActivity;
import zsx.com.test.ui.parse.JavaParseActivity;
import zsx.com.test.ui.parse.LogcatActivity;
import zsx.com.test.ui.refresh.RefreshLinearLayoutActivity;
import zsx.com.test.ui.viewpagelooper.ViewPagerLooperActivity;
import zsx.com.test.ui.widget.AutoListViewActivity;
import zsx.com.test.ui.widget.BadgeViewActivity;
import zsx.com.test.ui.widget.DirectionViewPagerActivity;
import zsx.com.test.ui.widget.DragListViewActivity;
import zsx.com.test.ui.widget.DragViewHelperActivity;
import zsx.com.test.ui.widget.KeywordsFlowActivity;
import zsx.com.test.ui.widget.PinnedSectionActivity;
import zsx.com.test.ui.widget.ShapeTextViewActivity;
import zsx.com.test.ui.widget.SlidingMenuActivity;
import zsx.com.test.ui.widget.ViewPagerIndicatorActivity;

/**
 * Created by zhusx on 2015/8/5.
 */
public class MainActivity extends _BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView mListView = (ListView) findViewById(R.id.listView);
        List<Item> list = new ArrayList<>();
        list.add(new Item("InsertAdapter", InsertAdapterActivity.class));
        list.add(new Item("refreshLinearLayout", RefreshLinearLayoutActivity.class));
        list.add(new Item("ShapeTextView", ShapeTextViewActivity.class));
        list.add(new Item("Debug", ExceptionActivity.class));
        list.add(new Item("Donwload", DownloadActivity.class));
        list.add(new Item("loadData", LoadDataActivity.class));
        list.add(new Item("slidingMenu", SlidingMenuActivity.class));
        list.add(new Item("viewPagerIndicator", ViewPagerIndicatorActivity.class));
        list.add(new Item("dragViewHelper", DragViewHelperActivity.class));
        list.add(new Item("keywordsFlow", KeywordsFlowActivity.class));
        list.add(new Item("autoListView", AutoListViewActivity.class));
        list.add(new Item("dragListView", DragListViewActivity.class));
        list.add(new Item("parse", JavaParseActivity.class));
        list.add(new Item("httpUrlTest", HttpUrlConnectActivity.class));
        list.add(new Item("PinnedSectionListView", PinnedSectionActivity.class));
        list.add(new Item("ViewPagerLooper", ViewPagerLooperActivity.class));
        list.add(new Item("DirectionViewPager", DirectionViewPagerActivity.class));
        list.add(new Item("httpRequest", HttpRequestActivity.class));
        list.add(new Item("badgeView", BadgeViewActivity.class));
        list.add(new Item("anim", AnimActivity.class));
        list.add(new Item("logcat", LogcatActivity.class));
        mListView.setAdapter(new _BaseAdapter<Item>(this, list) {
            @Override
            public View getView(LayoutInflater inflater, final Item bean, final int position, View convertView, ViewGroup parent) {
                View[] vs = _getViewHolder(inflater, convertView, parent, R.layout.lib_list_item_1);
                _toTextView(vs[0]).setText(bean.name);
                return vs[0];
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(parent.getContext(), ((Item) parent.getItemAtPosition(position)).cls));
            }
        });
    }

    private class Item {
        public String name;
        public Class<?> cls;

        public Item(String name, Class<?> cls) {
            this.name = name;
            this.cls = cls;
        }
    }
}
