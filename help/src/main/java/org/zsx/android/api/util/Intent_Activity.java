package org.zsx.android.api.util;

import org.zsx.android.api.MainActivity;
import org.zsx.android.api.R;
import org.zsx.android.base._BaseActivity;
import org.zsx.android.api.activity.Activity_Activity;
import org.zsx.android.api.activity.Activity_Activity_TestDialogActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class Intent_Activity extends _BaseActivity implements OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.util_intent);
		findViewById(R.id.global_btn1).setOnClickListener(this);
		findViewById(R.id.global_btn2).setOnClickListener(this);
		findViewById(R.id.global_btn3).setOnClickListener(this);
		findViewById(R.id.global_btn4).setOnClickListener(this);
		findViewById(R.id.global_textview1).setOnClickListener(this);
		findViewById(R.id.global_textview2).setOnClickListener(this);
		findViewById(R.id.global_textview3).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent in = null;
		switch (v.getId()) {
		case R.id.global_btn1:
			in = new Intent(Intent.ACTION_DIAL);
			in.setData(Uri.parse("tel:13996687614"));
			break;
		case R.id.global_btn2:
			in = new Intent(Intent.ACTION_CALL);
			/** <uses-permission android:name="android.permission.CALL_PHONE" /> */
			in.setData(Uri.parse("tel:13996687614"));
			break;
		case R.id.global_btn3:
			in = new Intent(Intent.ACTION_VIEW);
			in.setData(Uri.parse("http://www.baidu.com"));
			break;
		case R.id.global_btn4:
			in = new Intent(Intent.ACTION_VIEW);
			/** geo:lat,long?z=zoomlevel&q=question-string */
			in.setData(Uri.parse("geo:0,0?z=4&q=business+near+city"));
			break;
		case R.id.global_textview1:
			in = new Intent(Intent.ACTION_WEB_SEARCH);
			in.setData(Uri.parse("http://www.baidu.com"));
			break;
		case R.id.global_textview2:
			Intent[] ins = new Intent[3];
			ins[0] = Intent.makeRestartActivityTask(new ComponentName(this,
					MainActivity.class));
			ins[1] = new Intent(this, Activity_Activity.class);
			ins[2] = new Intent(this,
					Activity_Activity_TestDialogActivity.class);
			ins[2].putExtra(
					Activity_Activity_TestDialogActivity._EXTRA_FLAG_KEY,
					android.R.style.Theme_Dialog);
			startActivities(ins);
			return;
		case R.id.global_textview3:
			in = new Intent(Intent.ACTION_GET_CONTENT);
			in.setType("*/*");
			startActivity(Intent.createChooser(in, "Select music"));
			break;
		default:
			return;
		}
		startActivity(in);
	}
}
