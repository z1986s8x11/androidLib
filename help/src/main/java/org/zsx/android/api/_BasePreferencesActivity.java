package org.zsx.android.api;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.tools.Lib_Class_ShowCodeUtil;

public class _BasePreferencesActivity extends PreferenceActivity {
    private Lib_Class_ShowCodeUtil showCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showCode = new Lib_Class_ShowCodeUtil();
        showCode.setShowJava(this.getClass());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        showCode._onCreateOptionsMenu(this, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        showCode._onOptionsItemSelected(this, item);
        return super.onOptionsItemSelected(item);
    }

}
