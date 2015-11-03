package org.zsx.android.api.util;

import org.zsx.android.api.R;
import org.zsx.android.api._BaseActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Handler_Activity extends _BaseActivity implements OnClickListener {

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				Toast.makeText(Handler_Activity.this, "sendEmptyMessage", Toast.LENGTH_SHORT).show();
				break;
			case 2:
				Bundle b = msg.getData();
				View v = (View) msg.obj;
				Toast.makeText(Handler_Activity.this, "sendMessage" + b.getString("name") + b.getInt("age"), Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.util_handler);
		Button b1 = (Button) findViewById(R.id.global_btn1);
		Button b2 = (Button) findViewById(R.id.global_btn2);
		Button b3 = (Button) findViewById(R.id.global_btn3);
		b1.setOnClickListener(this);
		b2.setOnClickListener(this);
		b3.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.global_btn1:
			mHandler.sendEmptyMessage(1);
			break;
		case R.id.global_btn2:
			Message m = new Message();
			m.what = 2;
			m.obj = v;
			Bundle data = new Bundle();
			data.putString("name", "祝思翔");
			data.putInt("age", 18);
			m.setData(data);
			mHandler.sendMessage(m);
			break;
		case R.id.global_btn3:
			// mHandler.postDelayed(r, delayMillis)
			// mHandler.removeCallbacks(r);
			break;

		default:
			break;
		}
	}
}
