package org.zsx.android.base;

import android.preference.PreferenceActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.zsx.debug.Lib_SourceCodeHelper;

public class _BasePreferencesActivity extends PreferenceActivity {
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
