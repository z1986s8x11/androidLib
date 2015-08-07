package zsx.com.test.base;

import android.view.View;

import com.zsx.app.Lib_BaseFragmentActivity;

/**
 * Created by zhusx on 2015/8/7.
 */
public class _BaseFragmentActivity extends Lib_BaseFragmentActivity {
    /**
     * xml定义的OnClick事件
     */
    public void onClickReturn(View v) {
        finish();
    }
}
