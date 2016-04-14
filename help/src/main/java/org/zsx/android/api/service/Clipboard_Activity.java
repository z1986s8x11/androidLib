package org.zsx.android.api.service;

import org.zsx.android.api.R;
import org.zsx.android.base._BaseActivity;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class Clipboard_Activity extends _BaseActivity {
	TextView mTextView;
	ClipboardManager mClipboardManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.service_clipboard);
		mTextView = (TextView) findViewById(R.id.global_textview1);
		registerForContextMenu(mTextView);
		// API 11之前： android.text.ClipboardManager
		// API 11之后： android.content.ClipboardManager
		if (android.os.Build.VERSION.SDK_INT > 7) {
			mClipboardManager = (ClipboardManager) getSystemService(Service.CLIPBOARD_SERVICE);
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		menu.add(0, 0, 0, "复制");
		menu.add(0, 1, 0, "粘贴");
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 0:
			if (android.os.Build.VERSION.SDK_INT > 11) {
				mClipboardManager.setText(mTextView.getText());
			} else {
				Toast.makeText(this, "版本不支持", Toast.LENGTH_SHORT).show();
			}
			break;
		case 1:
			if (android.os.Build.VERSION.SDK_INT > 11) {
				mTextView.setText(mClipboardManager.getText());
			} else {
				Toast.makeText(this, "版本不支持", Toast.LENGTH_SHORT).show();
			}
			break;
		default:
			break;
		}
		return super.onContextItemSelected(item);
	}
}
