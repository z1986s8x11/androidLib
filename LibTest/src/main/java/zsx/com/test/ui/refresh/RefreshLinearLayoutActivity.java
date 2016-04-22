package zsx.com.test.ui.refresh;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zsx.debug.LogUtil;
import com.zsx.util._Arrays;

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
    }
}
