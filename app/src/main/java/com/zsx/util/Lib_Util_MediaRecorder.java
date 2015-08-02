package com.zsx.util;

import android.media.MediaRecorder;

import com.zsx.debug.LogUtil;

import java.io.File;
import java.io.IOException;

public class Lib_Util_MediaRecorder {
	static final private double EMA_FILTER = 0.6;

	private MediaRecorder mRecorder = null;
	private double mEMA = 0.0;
	private long startTime;

	public void start(String path) throws IllegalStateException, IOException {
		if (mRecorder != null) {
			mRecorder.stop();
			mRecorder.release();
			mRecorder = null;
		}
		File file = new File(path);
		if (file.exists()) {
			file.delete();
		} else {
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
		}
		mRecorder = new MediaRecorder();
		mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		mRecorder.setOutputFile(path);
		try {
			mRecorder.prepare();
			mRecorder.start();
		} catch (IllegalStateException e) {
			if (LogUtil.DEBUG) {
				LogUtil.w(e);
			}
		} catch (IOException e) {
			if (LogUtil.DEBUG) {
				LogUtil.w(e);
			}
		}
		startTime = System.currentTimeMillis();
		mEMA = 0.0;
	}

	public int stop() {
		if (mRecorder != null) {
			mRecorder.stop();
			mRecorder.release();
			mRecorder = null;
			return (int) ((System.currentTimeMillis() - startTime) / 1000);
		}
		return -1;
	}
	
	public double getAmplitude() {
		if (mRecorder != null)
			return (mRecorder.getMaxAmplitude() / 2700.0);
		else
			return 0;

	}

	public double getAmplitudeEMA() {
		double amp = getAmplitude();
		mEMA = EMA_FILTER * amp + (1.0 - EMA_FILTER) * mEMA;
		return mEMA;
	}
}
