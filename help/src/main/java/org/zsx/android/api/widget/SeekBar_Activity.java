package org.zsx.android.api.widget;

import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.Toast;

import com.zsx.debug.LogUtil;

import org.zsx.android.api.R;
import org.zsx.android.api._BaseActivity;

public class SeekBar_Activity extends _BaseActivity implements
		SeekBar.OnSeekBarChangeListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.widget_seekbar);
		SeekBar mSeekBar = (SeekBar) findViewById(R.id.act_widget_current_view);
		mSeekBar.setOnSeekBarChangeListener(this);
	}

	/**
	 * 进度值发生变化的时候触发
	 */
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		Toast.makeText(this, "progress:" + progress, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 按下的时候触发
	 */
	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		if (LogUtil.DEBUG) {
			LogUtil.i(this, "onStartTrackingTouch:\t" + seekBar.getProgress());
		}
	}

	/**
	 * 松开的时候触发
	 */
	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		if (LogUtil.DEBUG) {
			LogUtil.i(this, "onStopTrackingTouch:\t" + seekBar.getProgress());
		}
	}
}
