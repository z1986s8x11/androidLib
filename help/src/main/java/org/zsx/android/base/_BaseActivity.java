package org.zsx.android.base;

import android.view.Menu;
import android.view.MenuItem;

import com.zsx.app.Lib_BaseFragmentActivity;
import com.zsx.debug.Lib_SourceCodeHelper;


public abstract class _BaseActivity extends Lib_BaseFragmentActivity {
    private Lib_SourceCodeHelper helper;


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        helper._onOptionsItemSelected(this, item);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        helper = new Lib_SourceCodeHelper(this.getClass());
        helper._onCreateOptionsMenu(this, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
