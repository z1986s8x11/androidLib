package org.zsx.android.api.util;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import org.zsx.android.api.R;
import org.zsx.android.base._BaseActivity;

public class ID_Activity extends _BaseActivity implements OnClickListener {
	private EditText mEditText;
	private TextView mTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.util_id);
		findViewById(R.id.global_btn1).setOnClickListener(this);
		mEditText = (EditText) findViewById(R.id.global_edittext1);
		mTextView = (TextView) findViewById(R.id.global_textview1);
	}

	@Override
	public void onClick(View v) {
		String id = mEditText.getText().toString();
		if (TextUtils.isEmpty(id)) {
			_showToast("请输入身份证信息");
			return;
		}
		if (id.length() == 18) {
			String lastChar = validateIDCode(id);
			if (id.substring(id.length() - 1, id.length()).equals(lastChar)) {
				mTextView.setText("身份证输入正确");
			} else {
				mTextView.setText("身份证输入错误");
			}
			return;
		}
		if (id.length() == 17) {
			String lastChar = validateIDCode(id);
			mTextView.setText("最后一位数字是:" + lastChar);
			return;
		}
		_showToast("查询身份证号码");
	}

	public String validateIDCode(String code) {
		int[] wi = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };
		String[] validate = { "0", "1", "X", "9", "8", "7", "6", "5", "4", "3",
				"2" };
		char[] c = code.toCharArray();
		int last = 0;
		for (int i = 0; i < wi.length; i++) {
			last = last + wi[i] * (c[i] - '0');
		}
		last = last % 11;
		return validate[last];
	}

}
