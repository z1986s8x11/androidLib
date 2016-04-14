package org.zsx.android.api.media;

import org.zsx.android.api.R;
import org.zsx.android.base._BaseActivity;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class AudioRecord_Activity extends _BaseActivity implements OnClickListener {
	private AudioRecord mAudioRecord;
	private int mAudioBufferSize;
	private int mAudioBufferSampleSize;
	private boolean inRecordMode = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.media_audiorecord);
		initAudioRecord();
	}

	private void initAudioRecord() {
		try {
			int sampleRate = 8000;
			int channelConfig = AudioFormat.CHANNEL_IN_MONO;
			int audioFormat = AudioFormat.ENCODING_PCM_16BIT;
			mAudioBufferSize = 2 * AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioFormat);
			mAudioBufferSampleSize = mAudioBufferSize / 2;
			mAudioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, sampleRate, channelConfig, audioFormat, mAudioBufferSize);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		int audioRecordState = mAudioRecord.getState();
		if (audioRecordState != AudioRecord.STATE_INITIALIZED) {
			Toast.makeText(this, "AudioRecord is not properly initialized", Toast.LENGTH_SHORT).show();
			finish();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		inRecordMode = true;
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				getSamples();
			}
		});
		t.start();
	}

	@Override
	protected void onPause() {
		super.onPause();
		inRecordMode = false;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mAudioRecord != null) {
			mAudioRecord.release();
		}
	}

	private void getSamples() {
		if (mAudioRecord == null) {
			return;
		}
		short[] audioBuffer = new short[mAudioBufferSampleSize];
		mAudioRecord.startRecording();
		int audioRecordingState = mAudioRecord.getRecordingState();
		if (audioRecordingState != AudioRecord.RECORDSTATE_RECORDING) {
			Toast.makeText(this, "AudioRecord is not properly initialized", Toast.LENGTH_SHORT).show();
			finish();
		}
		while (inRecordMode) {
			int samplesRead = mAudioRecord.read(audioBuffer, 0, mAudioBufferSampleSize);
		}
		mAudioRecord.stop();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.global_btn1:

			break;
		case R.id.global_btn2:

			break;
		case R.id.global_btn3:

			break;
		case R.id.global_btn4:

			break;
		}
	}
}
