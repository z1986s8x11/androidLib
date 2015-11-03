package org.zsx.android.api.read;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.zsx.util.Lib_Util_String;

import org.zsx.android.api._BaseActivity;

import java.io.File;

/**
 * txt log阅读类
 * 
 * @Author zsx
 * @date 2015-5-5
 */
public class TxtReaderActivity extends _BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TextView t = new TextView(this);
		t.setMovementMethod(ScrollingMovementMethod.getInstance());
		setContentView(t);
		String str = Lib_Util_String.toString(new File(getIntent().getData().getPath()));
		t.setText(str);
	}
}
