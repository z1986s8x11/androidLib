package zsx.com.test.ui.refresh;

import android.os.Bundle;

import com.zsx.debug.LogUtil;

import zsx.com.test.R;
import zsx.com.test.base._BaseActivity;

/**
 * Created by Administrator on 2015/12/16.
 */
public class RefreshLinearLayoutActivity extends _BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refreshlayout);
        final Lib_AutoListView autoList = (Lib_AutoListView) findViewById(R.id.listView);
        autoList._setOnRefreshListener(new Lib_AutoListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LogUtil.e(this, "===========");
                autoList.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        autoList._setRefresh(false);
                    }
                }, 5000);
            }
        });
    }
}
