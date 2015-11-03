package org.zsx.android.api.media;

import java.io.File;
import java.io.IOException;

import org.zsx.android.api.R;
import org.zsx.android.api._BaseActivity;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 录音
 * 
 * @author zsx
 * 
 */
public class MediaRecorder_Activity extends _BaseActivity implements OnClickListener {
	private MediaRecorder mMediaRecorder;
	private MediaPlayer mMediaPlayer;
	private String outPutFilePath = Environment.getExternalStorageDirectory() + File.separator + "zsx" + File.separator + "zsx.3gpp";
	private View startRecorder;
	private View stopRecorder;
	private View startPlayer;
	private View stopPlayer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.media_mediarecorder);
		startRecorder = findViewById(R.id.global_btn1);
		startRecorder.setOnClickListener(this);
		stopRecorder = findViewById(R.id.global_btn2);
		stopRecorder.setOnClickListener(this);
		stopRecorder.setEnabled(false);
		startPlayer = findViewById(R.id.global_btn3);
		startPlayer.setOnClickListener(this);
		stopPlayer = findViewById(R.id.global_btn4);
		stopPlayer.setOnClickListener(this);
		stopPlayer.setEnabled(false);
	}

	@Override
	public void onClick(View v) {
		File outFile = new File(outPutFilePath);
		switch (v.getId()) {
		case R.id.global_btn1:
			stopRecorder.setEnabled(true);
			startRecorder.setEnabled(false);
			if (!outFile.getParentFile().exists()) {
				outFile.getParentFile().mkdirs();
			}
			if (outFile.exists()) {
				outFile.delete();
			}
			/**
			 * 需要 <uses-permission
			 * android:name="android.permission.RECORD_AUDIO" />
			 */
			mMediaRecorder = new MediaRecorder();
			mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
			mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
			mMediaRecorder.setOutputFile(outPutFilePath);
			mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
			try {
				mMediaRecorder.prepare();
			} catch (IOException e) {
				e.printStackTrace();
			}
			mMediaRecorder.start();
			break;
		case R.id.global_btn2:
			startRecorder.setEnabled(true);
			stopRecorder.setEnabled(false);
			if (mMediaRecorder != null) {
				mMediaRecorder.stop();
			}
			break;
		case R.id.global_btn3:
			stopPlayer.setEnabled(true);
			startPlayer.setEnabled(false);
			if (mMediaPlayer == null) {
				mMediaPlayer = new MediaPlayer();
			}
			if (!outFile.exists()) {
				return;
			}

			try {
				mMediaPlayer.setDataSource(outPutFilePath);
				mMediaPlayer.prepare();
				mMediaPlayer.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case R.id.global_btn4:
			stopPlayer.setEnabled(false);
			startPlayer.setEnabled(true);
			if (mMediaPlayer != null) {
				if (mMediaPlayer.isPlaying()) {
					mMediaPlayer.stop();
				}
			}
			break;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mMediaRecorder != null) {
			mMediaRecorder.release();
			mMediaRecorder = null;
		}
		if (mMediaPlayer != null) {
			mMediaPlayer.release();
			mMediaPlayer = null;
		}
	}
}
