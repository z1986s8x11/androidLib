package zsx.com.test.base;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.zsx.app.Lib_BaseFragmentActivity;
import com.zsx.debug.Lib_SourceCodeHelper;

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

    Lib_SourceCodeHelper helper;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (helper == null) {
            helper = new Lib_SourceCodeHelper(getClass());
            helper._onCreateOptionsMenu(this, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        helper._onOptionsItemSelected(this, item);
        return super.onOptionsItemSelected(item);
    }
}
