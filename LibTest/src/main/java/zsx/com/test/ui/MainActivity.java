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
import zsx.com.test.ui.debug.ExceptionActivity;
import zsx.com.test.ui.download.DownloadActivity;
import zsx.com.test.ui.network.LoadDataActivity;
import zsx.com.test.ui.parse.JavaParseActivity;
import zsx.com.test.ui.widget.AutoListViewActivity;
import zsx.com.test.ui.widget.DragListViewActivity;
import zsx.com.test.ui.widget.KeywordsFlowActivity;
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
        list.add(new Item("Debug", ExceptionActivity.class));
        list.add(new Item("Donwload", DownloadActivity.class));
        list.add(new Item("loadData", LoadDataActivity.class));
        list.add(new Item("slidingMenu", SlidingMenuActivity.class));
        list.add(new Item("viewPagerIndicator", ViewPagerIndicatorActivity.class));
        list.add(new Item("keywordsFlow", KeywordsFlowActivity.class));
        list.add(new Item("autoListView", AutoListViewActivity.class));
        list.add(new Item("dragListView", DragListViewActivity.class));
        list.add(new Item("parse", JavaParseActivity.class));
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
