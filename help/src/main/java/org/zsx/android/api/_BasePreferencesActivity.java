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
		_showCodeInit(showCode);
		if (showCode.getShowJava() == null) {
			showCode.setShowJava(this.getClass());
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void addPreferencesFromResource(int layoutResID) {
		super.addPreferencesFromResource(layoutResID);
		if (showCode.getShowXml() == null) {
			showCode.setShowXML(layoutResID);
		}
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

	protected void _showCodeInit(Lib_Class_ShowCodeUtil showCode) {
	}
}
