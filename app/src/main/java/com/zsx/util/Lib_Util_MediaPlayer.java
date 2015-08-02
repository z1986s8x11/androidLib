package com.zsx.util;

import java.io.File;
import java.io.IOException;

import zsx.lib.base.log.LogUtil;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

public class Lib_Util_MediaPlayer {
	private MediaPlayer mMediaPlayer;
	private String lastPath;

	public Lib_Util_MediaPlayer() {
		mMediaPlayer = new MediaPlayer();
	}

	public void start(String path) {
		if (path == null) {
			if (LogUtil.DEBUG) {
				LogUtil.e(this, "音频文件路径:null");
			}
			return;
		}
		File f = new File(path);
		if (!f.exists()) {
			if (LogUtil.DEBUG) {
				LogUtil.e(this, "音频文件不存在:" + path);
			}
			return;
		}
		if (path.equals(lastPath)) {
			if (mMediaPlayer.isPlaying()) {
				mMediaPlayer.pause();
				return;
			}
			mMediaPlayer.start();
			return;
		}
		lastPath = path;
		mMediaPlayer.reset();
		try {
			mMediaPlayer.setDataSource(path);
			mMediaPlayer.prepare();
			mMediaPlayer.start();
		} catch (IllegalArgumentException e) {
			if (LogUtil.DEBUG) {
				LogUtil.w(e);
			}
		} catch (SecurityException e) {
			if (LogUtil.DEBUG) {
				LogUtil.w(e);
			}
		} catch (IllegalStateException e) {
			if (LogUtil.DEBUG) {
				LogUtil.w(e);
			}
		} catch (IOException e) {
			if (LogUtil.DEBUG) {
				LogUtil.w(e);
			}
		}
	}

	public void onDestry() {
		if (mMediaPlayer != null) {
			mMediaPlayer.release();
		}
	}

	public int getTime() {
		return mMediaPlayer.getDuration();
	}

	public void setOnCompletionListener(OnCompletionListener listener) {
		mMediaPlayer.setOnCompletionListener(listener);
	}

}
