package zsx.com.test.ui.widget;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zsx.widget.drag.DragSortListView;

import java.util.Arrays;

import zsx.com.test.R;
import zsx.com.test.base._BaseActivity;
import zsx.com.test.base._BaseAdapter;

/**
 * Created by zhusx on 2015/8/11.
 */
public class DragListViewActivity extends _BaseActivity {
    DragSortListView mListView;
    _BaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget_draglistview);
        mListView = (DragSortListView) findViewById(R.id.listView);
        mListView.setAdapter(adapter = new _BaseAdapter<String>(this) {
            @Override
            public View getView(LayoutInflater inflater, String bean, int position, View convertView, ViewGroup parent) {
                View[] vs = _getViewHolder(inflater, convertView, parent, R.layout.lib_list_item_1, android.R.id.text1);
                _toTextView(vs[1]).setText(bean);
                return vs[0];
            }
        });
        adapter._addItemToUpdate(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8"));
        mListView.setDropListener(onDrop);
//        mListView.setFloatViewManager(new SimpleFloatViewManager(mListView));
    }

    //监听器在手机拖动停下的时候触发
    private DragSortListView.DropListener onDrop =
            new DragSortListView.DropListener() {
                @Override
                public void drop(int from, int to) {//from to 分别表示 被拖动控件原位置 和目标位置
                    if (from != to) {
                        String item = (String) adapter.getItem(from);//得到listview的适配器
                        adapter._removeItem(from);//在适配器中”原位置“的数据。
                        adapter._addItemToUpdate(to, item);//在目标位置中插入被拖动的控件。
                    }
                }
            };
    //删除监听器，点击左边差号就触发。删除item操作。
    private DragSortListView.RemoveListener onRemove =
            new DragSortListView.RemoveListener() {
                @Override
                public void remove(int which) {
                    adapter._removeItemToUpdate(which);
                }
            };
}
