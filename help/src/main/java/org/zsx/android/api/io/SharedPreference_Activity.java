package org.zsx.android.api.io;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.zsx.android.api.R;
import org.zsx.android.api._BaseActivity;

import java.util.Date;

@SuppressLint("CommitPrefEdits")
public class SharedPreference_Activity extends _BaseActivity implements Button.OnClickListener {
	private SharedPreferences mSharedPreferences;
	private Editor mEditor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.io_sharedpreference);
		// MODE_PRIVATE 只能被本应用程序读写
		// MODE_WORLD_READABLE被其他应用程序读,但不能写
		// MODE_WORLD_WRITEABLE被其他应用程序读写
		// 如果需要调用其他应用程序的SharedPreference
		// 先拿到对应context :
		// createPackageContext("对应报名",Context.CONTEXT_IGNORE_SECURITY)
		// mSharedPreferences = getSharedPreferences("zsx", MODE_PRIVATE);
		mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		mEditor = mSharedPreferences.edit();
		Button read = (Button) findViewById(R.id.global_btn1);
		read.setOnClickListener(this);
		Button write = (Button) findViewById(R.id.global_btn2);
		write.setOnClickListener(this);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.global_btn1:
			if (mSharedPreferences.contains("save_date_key")) {
				Toast.makeText(this, "还没保存时间", Toast.LENGTH_SHORT).show();
			} else {
				String st = mSharedPreferences.getString("save_date_key", "没得时间");
				Toast.makeText(this, st, Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.global_btn2:
			mEditor.putString("save_date_key", new Date().toLocaleString());
			mEditor.commit();
			Toast.makeText(this, "保存的时间为:" + new Date().toLocaleString(), Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
	}
}
