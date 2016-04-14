package org.zsx.android.api.service;

import org.zsx.android.api.R;
import org.zsx.android.base._BaseActivity;

import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

public class SmsManager_Activity extends _BaseActivity implements OnClickListener {
	private EditText mInputET;
	private TextView mPhoneTV;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.service_smsmanager);
		findViewById(R.id.global_btn1).setOnClickListener(this);
		mInputET = (EditText) findViewById(R.id.global_edittext1);
		mPhoneTV = (TextView) findViewById(R.id.global_textview1);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.global_btn1:
			if (mInputET.getText().toString().length() != 0) {
				/**需要<uses-permission android:name="android.permission.SEND_SMS" />*/
				SmsManager.getDefault().sendTextMessage(mPhoneTV.getText().toString(), null, mInputET.getText().toString(), null, null);
			}
			mInputET.setText("");
			break;
		}
	}
}
