package org.zsx.android.api.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnSystemUiVisibilityChangeListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import org.zsx.android.api.R;
import org.zsx.android.base._BaseActivity;

public class CustomFullView_Activity extends _BaseActivity {

	CustomView customView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/** 设置状态栏不会挡在界面上面 */
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
		setContentView(R.layout.view_fullview);
		customView = (CustomView) findViewById(R.id.act_widget_current_view);
		customView.init(this, (TextView) findViewById(R.id.global_textview1),
				(Button) findViewById(R.id.global_btn1),
				(SeekBar) findViewById(R.id.global_seekbar));
	}
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public static class CustomView extends ImageView implements
			OnSystemUiVisibilityChangeListener, View.OnClickListener {
		Activity mActivity;
		TextView mTitleView;
		Button mPlayButton;
		SeekBar mSeekView;
		boolean mPaused;
		boolean mNavVisible;
		int mLastSystemUiVis;
		Runnable mNavHider = new Runnable() {
			@Override
			public void run() {
				setNavVisibility(false);
			}
		};


		public CustomView(Context context, AttributeSet attrs) {
			super(context, attrs);
			setOnSystemUiVisibilityChangeListener(this);
		}

		public void init(Activity activity, TextView title, Button playButton,
				SeekBar seek) {
			// This called by the containing activity to supply the surrounding
			// state of the video player that it will interact with.
			mActivity = activity;
			mTitleView = title;
			mPlayButton = playButton;
			mSeekView = seek;
			mPlayButton.setOnClickListener(this);
			setPlayPaused(true);
		}

		void setPlayPaused(boolean paused) {
			mPaused = paused;
			mPlayButton.setText(paused ? "显示" : "隐藏");
			setKeepScreenOn(!paused);
			setNavVisibility(true);
		}

		@Override
		public void onSystemUiVisibilityChange(int visibility) {
			// Detect when we go out of nav-hidden mode, to clear our state
			// back to having the full UI chrome up. Only do this when
			// the state is changing and nav is no longer hidden.
			int diff = mLastSystemUiVis ^ visibility;
			mLastSystemUiVis = visibility;
			if ((diff & SYSTEM_UI_FLAG_HIDE_NAVIGATION) != 0
					&& (visibility & SYSTEM_UI_FLAG_HIDE_NAVIGATION) == 0) {
				setNavVisibility(true);
			}
		}

		@Override
		protected void onWindowVisibilityChanged(int visibility) {
			super.onWindowVisibilityChanged(visibility);

			// When we become visible or invisible, play is paused.
			setPlayPaused(true);
		}

		void setNavVisibility(boolean visible) {
			int newVis = SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
					| SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
					| SYSTEM_UI_FLAG_LAYOUT_STABLE;
			if (!visible) {
				newVis |= SYSTEM_UI_FLAG_LOW_PROFILE
						| SYSTEM_UI_FLAG_FULLSCREEN
						| SYSTEM_UI_FLAG_HIDE_NAVIGATION;
			}

			// If we are now visible, schedule a timer for us to go invisible.
			if (visible) {
				Handler h = getHandler();
				if (h != null) {
					h.removeCallbacks(mNavHider);
					if (!mPaused) {
						// If the menus are open or play is paused, we will not
						// auto-hide.
						h.postDelayed(mNavHider, 1500);
					}
				}
			}

			// Set the new desired visibility.
			setSystemUiVisibility(newVis);
			mTitleView.setVisibility(visible ? VISIBLE : INVISIBLE);
			mPlayButton.setVisibility(visible ? VISIBLE : INVISIBLE);
			mSeekView.setVisibility(visible ? VISIBLE : INVISIBLE);
		}

		@Override
		public void onClick(View v) {
			if (v == mPlayButton) {
				// Clicking on the play/pause button toggles its state.
				setPlayPaused(!mPaused);
			} else {
				// Clicking elsewhere makes the navigation visible.
				setNavVisibility(true);
			}
		}
	}
}
