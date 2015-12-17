package zsx.com.test.ui.widget;

import android.os.Bundle;

import zsx.com.test.R;
import zsx.com.test.base._BaseActivity;

/**
 * Created by Administrator on 2015/12/16.
 */
public class RefreshLinearLayoutActivity extends _BaseActivity {
    RefreshLinearLayout refreshLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refreshlayout);
        refreshLinearLayout = (RefreshLinearLayout) findViewById(R.id.layout);
    }
}
