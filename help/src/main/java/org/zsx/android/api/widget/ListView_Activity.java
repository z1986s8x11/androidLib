package org.zsx.android.api.widget;

import org.zsx.android.api.R;
import org.zsx.android.api._BaseActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;
import android.widget.Toast;

public class ListView_Activity extends _BaseActivity implements
		ListView.OnItemClickListener, OnQueryTextListener {
	ListView mListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.widget_listview);
		SearchView searchSV = (SearchView) findViewById(R.id.searchView);
		searchSV.setOnQueryTextListener(this);
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
	public boolean onQueryTextSubmit(String query) {
		return true;
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		if (TextUtils.isEmpty(newText)) {
			mListView.clearTextFilter();
		} else {
			mListView.setFilterText(newText);
		}
		return true;
	}
}
