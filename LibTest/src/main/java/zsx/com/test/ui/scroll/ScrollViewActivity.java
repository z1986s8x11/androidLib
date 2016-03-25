package zsx.com.test.ui.scroll;

import android.os.Bundle;
import android.view.View;

import zsx.com.test.R;
import zsx.com.test.base._BaseActivity;

/**
 * Author       zhusx
 * Email        327270607@qq.com
 * Created      2016/3/22 13:44
 */
public class ScrollViewActivity extends _BaseActivity {
    TestScrollView mScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrollview);
        View titleV = findViewById(R.id.tv_title);
        mScrollView = (TestScrollView) findViewById(R.id.scrollView);
        mScrollView.setTitle(titleV);
    }
}
