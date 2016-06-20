package zsx.com.test.ui.test;

import android.os.Bundle;
import android.view.View;

import com.zsx.debug.LogUtil;
import com.zsx.widget.Lib_Widget_ExpandableLayout;

import zsx.com.test.R;
import zsx.com.test.base._BaseActivity;

/**
 * Author       zhusx
 * Email        327270607@qq.com
 * Created      2016/6/20 17:35
 */
public class ExpActivity extends _BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exp);
        findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.e(this, "===");
                ((Lib_Widget_ExpandableLayout) v)._toggle();
            }
        });
    }
}
