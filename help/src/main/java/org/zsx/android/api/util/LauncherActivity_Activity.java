package org.zsx.android.api.util;

import android.app.LauncherActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import com.tools.Lib_Class_ShowCodeUtil;

import org.zsx.android.api.MainActivity;

public class LauncherActivity_Activity extends LauncherActivity {
	private Class<?>[] classes;
	private Lib_Class_ShowCodeUtil showCode;
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

	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		showCode = new Lib_Class_ShowCodeUtil();
		_showCodeInit(showCode);
		if (showCode.getShowJava() == null) {
			showCode.setShowJava(this.getClass());
		}
		String[] names = new String[] { "系统通知" };
		classes = new Class[] { MainActivity.class };
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);
		setListAdapter(adapter);
	}

	// 需要重写的方法 根据列表项返回指定Activity对应Intent
	@Override
	protected Intent intentForPosition(int position) {
		return new Intent(this, classes[position]);
	}

	protected void _showCodeInit(Lib_Class_ShowCodeUtil showCode) {
	}
}
