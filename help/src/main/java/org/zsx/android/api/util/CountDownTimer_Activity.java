package org.zsx.android.api.util;

import org.zsx.android.api.R;
import org.zsx.android.base._BaseActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class CountDownTimer_Activity extends _BaseActivity implements OnClickListener {
	private CountDownTimer timer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.util_countdowntimer);
		findViewById(R.id.global_btn1).setOnClickListener(this);
	}

	@Override
	public void onClick(final View v) {
		if (timer == null) {
			/**
			 * 第一个参数 :从开始调用start()到倒计时完成并onFinish()方法被调用的毫秒数
			 * 第二个参数:onTick(long)回调的间隔时间
			 */
			timer = new CountDownTimer(10000, 1000) {

				@Override
				public void onTick(long millisUntilFinished) {
					((Button) v).setText("倒计时" + (millisUntilFinished / 1000));
				}

				@Override
				public void onFinish() {
					((Button) v).setText("完成");
					v.setEnabled(true);
					timer = null;
				}
			};
			/** 开始倒计时 */
			timer.start();
			v.setEnabled(false);
		} else {
			/** 取消倒计时 */
			timer.cancel();
		}
	}
}
