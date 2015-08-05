package zsx.com.test.ui.debug;

import android.os.Bundle;
import android.view.View;

import zsx.com.test.R;
import zsx.com.test.base._BaseActivity;

/**
 * Created by zhusx on 2015/8/5.
 */
public class ExceptionActivity extends _BaseActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug_exception);
        findViewById(R.id.btn_null).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        throw new NullPointerException("我抛出的");
    }
}
