package org.zsx.android.api.widget;

import java.util.Timer;
import java.util.TimerTask;

import org.zsx.android.api.R;
import org.zsx.android.base._BaseActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher.ViewFactory;

public class TextSwitch_Activity extends _BaseActivity implements ViewFactory {
	private TextSwitcher textTS;
	private Timer timer = new Timer();;
	private int count;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.widget_textswitch);
		textTS = (TextSwitcher) findViewById(R.id.act_widget_current_view);
		textTS.setFactory(this);
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				textTS.post(new Runnable() {

					@Override
					public void run() {
						textTS.setText(String.valueOf(count++));
					}
				});
			}
		}, 0, 1000);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		timer.cancel();
	}

	@Override
	public View makeView() {
		TextView t = new TextView(this);
		t.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
		t.setTextSize(36);
		return t;
	}
}
