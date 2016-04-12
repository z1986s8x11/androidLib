package zsx.com.test.ui.v7;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.zsx.util._Arrays;
import com.zsx.widget.Lib_Widget_HorizontalListView;
import com.zsx.widget.v7._BaseRecyclerAdapter;

import java.util.Arrays;

import zsx.com.test.R;
import zsx.com.test.base._BaseActivity;
import zsx.com.test.base._BaseAdapter;

/**
 * Author       zhusx
 * Email        327270607@qq.com
 * Created      2016/3/2 10:39
 */
public class RecyclerViewActivity extends _BaseActivity {
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeLayout;
    private _BaseRecyclerAdapter<String> adapter;
    private Lib_Widget_HorizontalListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);
        listView = (Lib_Widget_HorizontalListView) findViewById(R.id.listView);
        listView.setAdapter(new _BaseAdapter<String>(this, Arrays.asList("线性布局(水平)", "线性布局(垂直)", "网格布局(水平)", "网格布局(垂直)", "流式布局(水平)", "流式布局(垂直)")) {
            @Override
            public View getView(LayoutInflater inflater, String bean, int position, View convertView, ViewGroup parent) {
                TextView t = new TextView(inflater.getContext());
                t.setPadding(10, 10, 10, 10);
                t.setText(bean);
                t.setGravity(Gravity.CENTER);
                return t;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RecyclerView.LayoutManager layoutManager = null;
                switch (position) {
                    case 0:
                        layoutManager = new LinearLayoutManager(RecyclerViewActivity.this);
                        ((LinearLayoutManager) layoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);
                        break;
                    case 1:
                        layoutManager = new LinearLayoutManager(RecyclerViewActivity.this);
                        ((LinearLayoutManager) layoutManager).setOrientation(LinearLayoutManager.VERTICAL);
                        break;
                    case 2:
                        layoutManager = new GridLayoutManager(RecyclerViewActivity.this, 4);
                        ((GridLayoutManager) layoutManager).setOrientation(GridLayoutManager.HORIZONTAL);
                        ((GridLayoutManager) layoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                            @Override
                            public int getSpanSize(int position) {
                                return 1;
                            }
                        });
                        break;
                    case 3:
                        layoutManager = new GridLayoutManager(RecyclerViewActivity.this, 4);
                        ((GridLayoutManager) layoutManager).setOrientation(GridLayoutManager.VERTICAL);
                        ((GridLayoutManager) layoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                            @Override
                            public int getSpanSize(int position) {
                                return 2;
                            }
                        });
                        break;
                    case 4:
                        layoutManager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.HORIZONTAL);
                        break;
                    case 5:
                        layoutManager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
                        break;
                }
                recyclerView.setLayoutManager(layoutManager);
            }
        });
        listView.performItemClick(listView.getChildAt(0), 0, listView.getItemIdAtPosition(0));
        recyclerView.setAdapter(adapter = new _BaseRecyclerAdapter<String>(this, _Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14")) {
            @Override
            public int __getLayoutResource(int viewType) {
                return R.layout.lib_list_item_1;
            }

            @Override
            public void __bindViewHolder(_ViewHolder holder, int position, final String st) {
                holder.setText(android.R.id.text1, st);
                holder.getView(android.R.id.text1).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        _removeItemToUpdate(st);
                    }
                });
            }
        });
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeLayout.setRefreshing(false);
                        adapter.notifyDataSetChanged();
                    }
                }, 2000);
            }
        });
    }
}
