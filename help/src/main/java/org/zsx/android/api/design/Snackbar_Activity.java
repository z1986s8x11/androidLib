package org.zsx.android.api.design;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;

import org.zsx.android.api.R;
import org.zsx.android.base._BaseActivity;

/**
 * Author       zhusx
 * Email        327270607@qq.com
 * Created      2016/4/13 15:37
 */
public class Snackbar_Activity extends _BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.widget_design_snackbar);
        findViewById(R.id.tv_text1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Snackbar snackbar = Snackbar.make(findViewById(R.id.tv_text1), "测试弹出提示", Snackbar.LENGTH_LONG);
                snackbar.setAction("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        snackbar.dismiss();
                    }
                });
                snackbar.show();
            }
        });
    }
}
