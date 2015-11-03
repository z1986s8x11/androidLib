package org.zsx.android.api.widget;

import org.zsx.android.api.R;
import org.zsx.android.api._BaseActivity;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ContextMenu_Activity extends _BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.widget_contextmenu);
		TextView t = (TextView) findViewById(R.id.global_textview1);
		registerForContextMenu(t);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		menu.add(0, 1, 0, "菜单一");
		menu.add(0, 2, 0, "菜单二");
		menu.add(0, 5, 0, "菜单三");
		menu.setHeaderIcon(android.R.drawable.ic_menu_set_as);
		menu.setHeaderTitle("ContextMenu");
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
		return super.onContextItemSelected(item);
	}

}
