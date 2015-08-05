package com.zsx.download;

import android.content.Context;

import java.io.File;

public final class Lib_DownloadBean implements Lib_DownloadInterface {
	private static final long serialVersionUID = -5117983034683759527L;
	private String downloadKey;
	private String savePath;
	private String downloadUrl;

	public Lib_DownloadBean(String downloadKey, String downloadUrl,
			String savePath) {
		this.downloadKey = downloadKey;
		this.downloadUrl = downloadUrl;
		this.savePath = savePath;
	}

	public Lib_DownloadBean(String downloadKey, String downloadUrl,
			String saveDir, String saveFileName) {
		this.downloadKey = downloadKey;
		this.downloadUrl = downloadUrl;
		this.savePath = new File(saveDir, saveFileName).getAbsolutePath();
	}

	/**
	 * 执行成功 之后去做的操作
	 */
	public void doSucess(Context context) {

	}

	public String getDownloadKey() {
		return downloadKey;
	}

	public String getSavePath() {
		return savePath;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("download_key:" + downloadKey);
		sb.append("download_url:" + downloadUrl);
		sb.append("savePath:" + savePath);
		return sb.toString();
	}

}
