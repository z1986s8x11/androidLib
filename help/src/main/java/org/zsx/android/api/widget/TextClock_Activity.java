package org.zsx.android.api.widget;

import org.zsx.android.api.R;
import org.zsx.android.base._BaseActivity;

import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

public class TextClock_Activity extends _BaseActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN_MR1){
			setContentView(R.layout.widget_textclock);
		}else{
			Toast.makeText(this, "需要 Android 4.2", Toast.LENGTH_SHORT).show();
			finish();
		}
	}
}
