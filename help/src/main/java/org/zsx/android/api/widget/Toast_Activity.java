package org.zsx.android.api.widget;

import org.zsx.android.api.R;
import org.zsx.android.api._BaseActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Toast_Activity extends _BaseActivity implements Button.OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.widget_toast);
		Button btn1 = (Button) findViewById(R.id.global_btn1);
		Button btn2 = (Button) findViewById(R.id.global_btn2);
		Button btn3 = (Button) findViewById(R.id.global_btn3);
		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn3.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.global_btn1:
			Toast.makeText(this, "普通的", Toast.LENGTH_SHORT).show();
			break;
		case R.id.global_btn2:
			Toast t = Toast.makeText(this, "带图片的", Toast.LENGTH_SHORT);
			ImageView iv = new ImageView(this);
			iv.setImageResource(android.R.drawable.ic_input_get);
			t.setView(iv);
			t.show();
			break;
		case R.id.global_btn3:
			Toast t2 = new Toast(this);
			t2.setGravity(Gravity.CENTER, 0, 0);
			t2.setDuration(5000);
			TextView text = new TextView(this);
			text.setText("大字");
			text.setBackgroundResource(android.R.color.darker_gray);
			text.setAlpha(50);
			text.setTextSize(50);
			t2.setView(text);
			t2.show();
			break;
		default:
			break;
		}
	}
}
