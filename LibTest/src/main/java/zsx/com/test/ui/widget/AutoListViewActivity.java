package zsx.com.test.ui.widget;

import android.os.Bundle;

import com.zsx.widget.Lib_Widget_ListView;

import zsx.com.test.R;
import zsx.com.test.base._BaseActivity;

/**
 * Created by zhusx on 2015/8/10.
 */
public class AutoListViewActivity extends _BaseActivity implements Lib_Widget_ListView.OnRefreshListener, Lib_Widget_ListView.OnLoadingMoreListener {
    Lib_Widget_ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget_autolistview);
        mListView = (Lib_Widget_ListView) findViewById(R.id.listView);
        mListView._setHeadView(this);
        mListView._setFootView(this);
    }

    @Override
    public void onRefresh(Lib_Widget_ListView listView) {

    }

    @Override
    public void loadMoreData(Lib_Widget_ListView listView, int itemCount) {

    }

    @Override
    public boolean hasMore(int itemCount) {
        return false;
    }
}
