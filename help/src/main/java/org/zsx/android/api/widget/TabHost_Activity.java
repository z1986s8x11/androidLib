package org.zsx.android.api.widget;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;

import com.zsx.debug.Lib_SourceCodeHelper;

import org.zsx.android.api.R;

@SuppressWarnings("deprecation")
public class TabHost_Activity extends TabActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TabHost host = getTabHost();
        /**
         * 设置使用TabHost布局 **
         */
        LayoutInflater.from(this).inflate(R.layout.widget_tabhost,
                host.getTabContentView(), true);
        host.addTab(host.newTabSpec("第一页").setIndicator("已接电话")
                .setContent(R.id.global_linearlayout1));
        host.addTab(host
                .newTabSpec("第二页")
                .setIndicator(
                        "魔兽世界",
                        getResources().getDrawable(
                                android.R.drawable.ic_menu_add))
                .setContent(R.id.global_linearlayout2));
        // tabHost 还可以装载另一个activity
        host.addTab(host.newTabSpec("判断用").setIndicator("显示用")
                .setContent(new Intent(this, TextView_Activity.class)));
    }

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
