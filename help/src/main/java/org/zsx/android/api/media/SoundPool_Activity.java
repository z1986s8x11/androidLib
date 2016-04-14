package org.zsx.android.api.media;

import android.app.Service;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.Bundle;
import android.os.Handler;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;

import org.zsx.android.api.R;
import org.zsx.android.base._BaseActivity;

public class SoundPool_Activity extends _BaseActivity implements OnCheckedChangeListener, OnLoadCompleteListener {
	private SoundPool mSoundPool;
	int music_sid1;
	int music_sid2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.media_soundpool);
		ToggleButton toggleButton = (ToggleButton) findViewById(R.id.global_btn1);
		toggleButton.setOnCheckedChangeListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		int maxStreams = 2; // 最大同时播放数
		int srcQuality = 0;
		int priority = 1;
		mSoundPool = new SoundPool(maxStreams, AudioManager.STREAM_MUSIC, srcQuality);
		mSoundPool.setOnLoadCompleteListener(this);
		music_sid1 = mSoundPool.load(this, R.raw.music_short1, priority);
		music_sid2 = mSoundPool.load(this, R.raw.music_short2, priority);
	}

	@Override
	protected void onPause() {
		super.onPause();
		mSoundPool.release();
		mSoundPool = null;
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isChecked) {
			mSoundPool.autoResume();
		} else {
			mSoundPool.autoPause();
		}
	}

	@Override
	public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
		if (status != 0) {
			return;
		}
		AudioManager aMgr = (AudioManager) getSystemService(Service.AUDIO_SERVICE);
		float currentVolume = aMgr.getStreamVolume(AudioManager.STREAM_MUSIC) / aMgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		if (sampleId == music_sid1) {
			gotoSound(music_sid1, 2000, currentVolume);
		} else if (sampleId == music_sid2) {
			gotoSound(music_sid2, 3000, currentVolume);
		}
	}

	private void gotoSound(final int sid, long delay, final float volume) {
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				if (mSoundPool == null) {
					return;
				}
				int loop = 1;
				float rate = 1.0f;
				mSoundPool.play(sid, volume, volume, 1, loop, rate);
			}
		}, delay);
	}
}
