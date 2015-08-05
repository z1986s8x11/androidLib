package com.zsx.download;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.zsx.debug.LogUtil;

/**
 * Lib_DownloadReceiver receiver = new Lib_DownloadReceiver(this);</br>
 * IntentFilter filter = new
 * IntentFilter(Lib_DownloadReceiver._DOWNLOAD_ACTION);</br>
 * registerReceiver(receiver, filter);</br> unregisterReceiver(receiver);
 * 
 * @author zsx
 * 
 */
public class Lib_DownloadReceiver extends BroadcastReceiver {
	private OnDownloadListener listener;

	public Lib_DownloadReceiver(OnDownloadListener listener) {
		this.listener = listener;
	}

	public static final String _DOWNLOAD_ACTION = "DownloadBookReceiver";
	static final String DOWNLOAD_EXTRAS_OBJECT_KEY = "object_key";
	static final String DOWNLOAD_EXTRAS_INT_PROGRESS_KEY = "progress_key";
	static final String DOWNLOAD_EXTRAS_INT_STATUS_KEY = "status_key";
	static final String DOWNLOAD_EXTRAS_STRING_MESSAGE_KEY = "message_key";

	@Override
	public void onReceive(Context context, Intent intent) {
		Lib_DownloadInterface data;
		if (_DOWNLOAD_ACTION.equals(intent.getAction())) {
			int status = intent.getIntExtra(DOWNLOAD_EXTRAS_INT_STATUS_KEY, -1);
			switch (status) {
			case OnDownloadListener.DOWNLOAD_STATUS_START:
				data = (Lib_DownloadInterface) intent
						.getSerializableExtra(DOWNLOAD_EXTRAS_OBJECT_KEY);
				if (listener != null) {
					listener.onDownloadStart(data.getDownloadKey(), data);
				}
				break;
			case OnDownloadListener.DOWNLOAD_STATUS_LOAD:
				data = (Lib_DownloadInterface) intent
						.getSerializableExtra(DOWNLOAD_EXTRAS_OBJECT_KEY);
				int progress = intent.getIntExtra(
						DOWNLOAD_EXTRAS_INT_PROGRESS_KEY, 0);
				if (listener != null) {
					listener.onDownloadLoad(data.getDownloadKey(), data,
							progress);
				}
				break;
			case OnDownloadListener.DOWNLOAD_STATUS_ERROR:
				data = (Lib_DownloadInterface) intent
						.getSerializableExtra(DOWNLOAD_EXTRAS_OBJECT_KEY);
				String message = intent
						.getStringExtra(DOWNLOAD_EXTRAS_STRING_MESSAGE_KEY);
				if (listener != null) {
					listener.onDownloadError(data.getDownloadKey(), message,
							data);
				}
				break;
			case OnDownloadListener.DOWNLOAD_STATUS_COMPLETE:
				data = (Lib_DownloadInterface) intent
						.getSerializableExtra(DOWNLOAD_EXTRAS_OBJECT_KEY);
				if (listener != null) {
					listener.onDownloadComplete(data.getDownloadKey(), data);
				}
				break;
			default:
				if (LogUtil.DEBUG) {
					LogUtil.e(this, "download status is error, status Code :"
							+ status);
				}
				break;
			}
		}
	}

	public interface OnDownloadListener {
		int DOWNLOAD_STATUS_START = 1;
		int DOWNLOAD_STATUS_LOAD = 2;
		int DOWNLOAD_STATUS_ERROR = 3;
		int DOWNLOAD_STATUS_COMPLETE = 4;

		void onDownloadStart(String key, Lib_DownloadInterface data);

		void onDownloadLoad(String key, Lib_DownloadInterface data, int progress);

		void onDownloadComplete(String key, Lib_DownloadInterface data);

		void onDownloadError(String key, String message, Lib_DownloadInterface data);
	}
}
