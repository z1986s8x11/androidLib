package zsx.com.test.base;

import android.view.View;

import com.zsx.app.Lib_BaseActivity;

/**
 * Created by zhusx on 2015/8/5.
 */
public class _BaseActivity extends Lib_BaseActivity {
    /**
     * xml定义的OnClick事件
     */
    public void onClickReturn(View v) {
        finish();
    }
}
