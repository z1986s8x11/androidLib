package org.zsx.android.api.widget;

import org.zsx.android.api.R;
import org.zsx.android.api._BaseActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.Toast;

public class EditText_Activity extends _BaseActivity implements TextWatcher, OnKeyListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.widget_edittext);
		EditText mEditText = (EditText) findViewById(R.id.act_widget_current_view);
		mEditText.addTextChangedListener(this);
		mEditText.setOnKeyListener(this);
	}

	@Override
	public void afterTextChanged(Editable s) {
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		Toast.makeText(this, s + ",start:" + start + "before:" + before + "count:" + count, Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		if (KeyEvent.KEYCODE_ENTER == keyCode && event.getAction() == KeyEvent.ACTION_DOWN) {
			Toast.makeText(this, "点击了搜索按钮", Toast.LENGTH_SHORT).show();
			return true;
		}
		return false;
	}
}
