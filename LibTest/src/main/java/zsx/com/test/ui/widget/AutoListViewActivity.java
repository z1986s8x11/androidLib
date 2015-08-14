package zsx.com.test.ui.widget;

import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;

import com.zsx.widget.Lib_Widget_ListView;

import java.util.ArrayList;
import java.util.List;

import zsx.com.test.R;
import zsx.com.test.base._BaseActivity;

/**
 * Created by zhusx on 2015/8/10.
 */
public class AutoListViewActivity extends _BaseActivity implements Lib_Widget_ListView.OnRefreshListener, Lib_Widget_ListView.OnLoadingMoreListener {
    Lib_Widget_ListView mListView;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            /**
             * 状态栏 透明
             */
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            /**
             * 全屏
             */
            //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.activity_widget_autolistview);
        List<String> list = new ArrayList<>();
        for (i = 0; i < 10; i++) {
            list.add(String.valueOf(i));
        }
        mListView = (Lib_Widget_ListView) findViewById(R.id.listView);
        mListView._setHeadView(this);
        mListView._setFootView(this);
        mListView.setAdapter(adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list));
    }

    @Override
    public void onRefresh(final Lib_Widget_ListView listView) {
        listView.postDelayed(new Runnable() {
            @Override
            public void run() {
                listView._setLoadingComplete();
            }
        }, 3000);
    }

    int i;

    @Override
    public void loadMoreData(final Lib_Widget_ListView listView, int itemCount) {
        listView.postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.add(String.valueOf(i++));
                listView._setLoadingComplete();
            }
        }, 3000);
    }

    @Override
    public boolean hasMore(int itemCount) {
        return true;
    }
}
