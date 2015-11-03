package org.zsx.android.api.widget;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.Toast;

import com.tools.Lib_Class_ShowCodeUtil;

import org.zsx.android.api.R;
import org.zsx.android.api._BaseActivity;

public class PopupMenu_Activity extends _BaseActivity implements
		OnClickListener, OnMenuItemClickListener {
	PopupMenu mPopupMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.widget_popupmenu);
		Button showPopupBtn = (Button) findViewById(R.id.global_btn1);
		showPopupBtn.setOnClickListener(this);
		mPopupMenu = new PopupMenu(this, showPopupBtn);
		getMenuInflater().inflate(R.menu.popup_menu, mPopupMenu.getMenu());
		mPopupMenu.setOnMenuItemClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.global_btn1:
			mPopupMenu.show();
			break;
		}
	}

	@Override
	public boolean onMenuItemClick(MenuItem item) {
		Toast.makeText(this, "Clicked popup menu item " + item.getTitle(),
				Toast.LENGTH_SHORT).show();
		return true;
	}

	@Override
	protected void _showCodeInit(Lib_Class_ShowCodeUtil showCode) {
		showCode.setShowXML(R.layout.widget_popupmenu, R.menu.popup_menu);
	}
}
