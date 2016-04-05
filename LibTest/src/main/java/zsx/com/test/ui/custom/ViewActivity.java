package zsx.com.test.ui.custom;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.zsx.debug.LogUtil;

import zsx.com.test.R;
import zsx.com.test.base._BaseActivity;

/**
 * Author       zhusx
 * Email        327270607@qq.com
 * Created      2016/4/5 16:07
 */
public class ViewActivity extends _BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LogUtil.e(this, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
        findViewById(R.id.tv_remove).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View vv = findViewById(R.id.tv_customView);
                if (vv != null) {
                    ((ViewGroup) vv.getParent()).removeView(vv);
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.e(this, "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.e(this, "onResume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.e(this, "onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.e(this, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.e(this, "onDestroy");
    }
}
