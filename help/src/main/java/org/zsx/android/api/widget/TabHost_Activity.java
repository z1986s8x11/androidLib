package org.zsx.android.api.widget;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;

import com.tools.Lib_Class_ShowCodeUtil;

import org.zsx.android.api.R;

@SuppressWarnings("deprecation")
public class TabHost_Activity extends TabActivity {

	private Lib_Class_ShowCodeUtil showCode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		showCode = new Lib_Class_ShowCodeUtil();
		showCodeInit(showCode);
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

	/****************** 显示代码相关 ***************************/
	public void showCodeInit(Lib_Class_ShowCodeUtil showCode) {
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
