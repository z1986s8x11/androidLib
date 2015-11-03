package org.zsx.android.api.util;

import android.Manifest.permission;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.zsx.util.Lib_Util_System;

import org.zsx.android.api.R;
import org.zsx.android.api._BaseActivity;

@SuppressLint("NewApi")
public class AccountManager_Activity extends _BaseActivity {
	private TextView mContentTV;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.util_accountmanager);
		mContentTV = (TextView) findViewById(R.id.global_textview1);
		/**
		 * 需要 <uses-permission android:name="android.permission.GET_ACCOUNTS" />
		 */
		if (Lib_Util_System.isPermisson(this, permission.GET_ACCOUNTS)) {
			Toast.makeText(
					this,
					" 需要 <uses-permission android:name='android.permission.GET_ACCOUNTS'/>",
					Toast.LENGTH_SHORT).show();
		}
		AccountManager am = AccountManager.get(this);
		Account[] accounts = am.getAccounts();
		StringBuilder sb = new StringBuilder();
		for (Account account : accounts) {
			sb.append("name:" + account.name);
			sb.append("\n");
			sb.append("type:" + account.type);
			sb.append("\n");
			sb.append("\n");
		}
		mContentTV.setText(sb.toString());
	}
}
