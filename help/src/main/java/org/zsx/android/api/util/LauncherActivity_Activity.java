package org.zsx.android.api.util;

import android.app.LauncherActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import com.zsx.debug.Lib_SourceCodeHelper;

public class LauncherActivity_Activity extends LauncherActivity {
    private Class<?>[] classes;
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

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        String[] names = new String[]{"系统通知"};
        classes = new Class[]{LauncherActivity_Activity.class};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);
        setListAdapter(adapter);
    }

    // 需要重写的方法 根据列表项返回指定Activity对应Intent
    @Override
    protected Intent intentForPosition(int position) {
        return new Intent(this, classes[position]);
    }

}
