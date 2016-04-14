package org.zsx.android.api.widget;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.zsx.android.api.R;
import org.zsx.android.base._BaseActivity;

public class ListView_Activity extends _BaseActivity implements
        ListView.OnItemClickListener {
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.widget_listview);
        mListView = (ListView) findViewById(R.id.act_widget_current_view);
        // android.R.layout.simple_list_item_1 每个列表项都是一个普通的TextView
        // android.R.layout.simple_list_item_2 每个列表项都是一个普通的TextView(字体略大)
        // android.R.layout.simple_list_item_checked 每个列表项都是一个已勾选的列表项
        // android.R.layout.simple_list_item_multiple_choice 每个列表项都是带多选框的文本
        // android.R.layout.simple_list_item_single_choice 每个列表项都是带多多单选按钮的 文本
        mListView.setOnItemClickListener(this);
        /* 过滤 */
        mListView.setTextFilterEnabled(true);
        // 设置listview 的选择模式
        // mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        // 这段代码的意思是设置你的listview的item不能被获取焦点，焦点由listview里的控件获得。如果被去掉，表面上看上去没什么区别，实际上获取的焦点目标变为了listview里的item。
        // mListView.setItemsCanFocus(false);

        registerForContextMenu(mListView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterForContextMenu(mListView);
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        if (arg1 instanceof TextView) {
            TextView t = (TextView) arg1;
            Toast.makeText(this, t.getText() + ":" + arg2, Toast.LENGTH_SHORT)
                    .show();
        } else {
            Toast.makeText(this, arg2, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(1, 1, 1, "ContextMenu1");
        menu.add(2, 2, 2, "ContextMenu2");
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getGroupId()) {
            case 1:
                _showToast("ContextMenu1 position:" + menuInfo.position);
                break;
            case 2:
                _showToast("ContextMenu2 position:" + menuInfo.position);
                break;
        }
        return super.onContextItemSelected(item);
    }
}
