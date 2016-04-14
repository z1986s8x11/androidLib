package org.zsx.android.api.util;

import org.zsx.android.api.R;
import org.zsx.android.base._BaseActivity;

import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;

public class Environment_Activity extends _BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.util_environment);
		TextView mContentTV = (TextView) findViewById(R.id.global_textview1);
		StringBuilder sb = new StringBuilder();
		sb.append("Environment的 方法:");
		sb.append("\n");
		sb.append("SD卡是否挂载 Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED):" + String.valueOf(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)));
		sb.append("\n");
		sb.append("Environment.getDataDirectory()=" + Environment.getDataDirectory());
		sb.append("\n");
		sb.append("Environment.getDownloadCacheDirectory() =" + Environment.getDownloadCacheDirectory());
		sb.append("\n");
		sb.append("Environment.getExternalStoragePublicDirectory(“test”) =" + Environment.getExternalStoragePublicDirectory("test"));
		sb.append("\n");
		sb.append("Environment.getRootDirectory() =" + Environment.getRootDirectory());
		sb.append("\n");
		sb.append("Activity的 方法:");
		sb.append("\n");
		sb.append("getPackageCodePath() =" + getPackageCodePath());
		sb.append("\n");
		sb.append("getPackageResourcePath =" + getPackageResourcePath());
		sb.append("\n");
		sb.append("getCacheDir() =" + getCacheDir());
		sb.append("\n");
		sb.append("getDatabasePath(“test“) =" + getDatabasePath("test"));
		sb.append("\n");
		sb.append("getDir(“test”, Context.MODE_PRIVATE) =" + getDir("test", MODE_PRIVATE));
		sb.append("\n");
		sb.append("getExternalCacheDir() =" + getExternalCacheDir());
		sb.append("\n");
		sb.append("getExternalFilesDir(“test”) =" + getExternalFilesDir("test"));
		sb.append("\n");
		sb.append("getExternalFilesDir(null) =" + getExternalFilesDir(null));
		sb.append("\n");
		sb.append("getFilesDir() =" + getFilesDir());
		mContentTV.setText(sb.toString());
	}
}
