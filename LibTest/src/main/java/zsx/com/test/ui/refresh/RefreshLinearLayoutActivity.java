package zsx.com.test.ui.refresh;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zsx.debug.LogUtil;

import zsx.com.test.R;
import zsx.com.test.base._BaseActivity;
import zsx.com.test.base._BaseAdapter;
import zsx.com.test.ui.network.LoadData;

/**
 * Created by Administrator on 2015/12/16.
 */
public class RefreshLinearLayoutActivity extends _BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refreshlayout);
        final AutoListView autoList = (AutoListView) findViewById(R.id.listView);
        autoList.setEmptyView(findViewById(R.id.tv_empty));
        autoList._setAdapter(new _BaseAdapter<String>(this) {
            @Override
            public View getView(LayoutInflater inflater, final String bean, int position, View convertView, ViewGroup parent) {
                View[] vs = _getViewHolder(inflater, convertView, parent, android.R.layout.simple_list_item_1, android.R.id.text1);
                _toTextView(vs[1]).setText(bean);
                vs[1].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        _showToast(bean);
                    }
                });
                return vs[0];
            }
        });
        final LoadData<DataEntity> loadData = new LoadData<>(LoadData.Api.TEST, this);
        autoList._setLoadData(loadData, new Lib_SwipeListView.OnReadDataListener() {
            @Override
            public void readData(boolean isRefresh, int position) {
                LogUtil.e(this, "refresh" + String.valueOf(isRefresh) + ":" + position);
                if (isRefresh) {
                    loadData._refreshData();
                } else {
                    loadData._loadData();
                }
            }
        }, true, true);
    }
}
