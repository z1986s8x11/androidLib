package com.zsx.download;

import android.os.Handler;

import com.zsx.app.Lib_BaseApplication;
import com.zsx.debug.LogUtil;
import com.zsx.exception.Lib_Exception;
import com.zsx.network.NetworkState;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Lib_UpLoad {
	public static final ExecutorService EXCUTORS = Executors.newSingleThreadExecutor();
	public static Map<String, String> map = new ConcurrentHashMap<String, String>();
	private Handler mHandler = new Handler();
	public interface OnUpLoadListener {
		int UPLOAD_STATUS_START = 1;
		int UPLOAD_STATUS_LOAD = 2;
		int UPLOAD_STATUS_ERROR = 3;
		int UPLOAD_STATUS_COMPLETE = 4;

		void onUpLoadStart(String key);

		void onUpLoadLoad(String key, int progress);

		void onUpLoadComplete(String key, String result);

		void onUpLoadError(String key, String message);
	}
	public boolean isUpload(String key) {
		return map.containsKey(key);
	}
	public boolean upload(File uploadFile, String uploadURL, OnUpLoadListener listener) {
		if (!uploadFile.exists()) {
			LogUtil.e(this, "上传文件不存在:" + uploadFile.getPath());
			return false;
		}
		if (Lib_BaseApplication._Current_NetWork_Status == NetworkState.NetType.NoneNet) {
			return false;
		}
		if (!isUpload(uploadURL)) {
			map.put(uploadURL, uploadURL);
			EXCUTORS.execute(new uploadTask(uploadFile, uploadURL, listener));
			return true;
		}
		return false;
	}
	class uploadTask implements Runnable {
		private File file;
		private OnUpLoadListener listener;
		private String RequestURL;
		public uploadTask(File uploadFile, String uploadURL, OnUpLoadListener listener) {
			this.file = uploadFile;
			this.listener = listener;
			this.RequestURL = uploadURL;
		}

		private void onStart() {
			mHandler.post(new Runnable() {

				@Override
				public void run() {
					listener.onUpLoadStart(RequestURL);
				}
			});
		}
		private void onComplete(final String result) {
			map.remove(RequestURL);
			mHandler.post(new Runnable() {

				@Override
				public void run() {
					listener.onUpLoadComplete(RequestURL, result);
				}
			});
		}
		private void onError(final String error) {
			map.remove(RequestURL);
			mHandler.post(new Runnable() {

				@Override
				public void run() {
					listener.onUpLoadError(RequestURL, error);
				}
			});
		}
		private void onLoad(final int progress) {
			mHandler.post(new Runnable() {

				@Override
				public void run() {
					listener.onUpLoadLoad(RequestURL, progress);
				}
			});
		}

		@Override
		public void run() {

			if (listener != null) {
				onStart();
			}
			InputStream is = null;
			DataOutputStream dos = null;
			try {
				int res = 0;
				String BOUNDARY = UUID.randomUUID().toString(); // 边界标识 随机生成
				String PREFIX = "--", LINE_END = "\r\n";
				String CONTENT_TYPE = "multipart/form-data"; // 内容类型
				String Charset = "UTF-8";
				URL url = new URL(RequestURL);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setReadTimeout(20000);
				conn.setConnectTimeout(60000);
				conn.setDoInput(true); // 允许输入流
				conn.setDoOutput(true); // 允许输出流
				conn.setUseCaches(false); // 不允许使用缓存
				conn.setRequestMethod("POST"); // 请求方式
				conn.setRequestProperty("Charset", Charset); // 设置编码
				conn.setRequestProperty("connection", "keep-alive");
				conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);
				/**
				 * 当文件不为空时执行上传
				 */
				dos = new DataOutputStream(conn.getOutputStream());
				StringBuffer sb = new StringBuffer();
				sb.append(PREFIX);
				sb.append(BOUNDARY);
				sb.append(LINE_END);
				/**
				 * 这里重点注意： name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件
				 * filename是文件的名字，包含后缀名
				 */
				sb.append("Content-Disposition: form-data; name=\"file\"; filename=\"" + file.getName() + "\""
						+ LINE_END);
				sb.append("Content-Type: application/octet-stream; charset=" + Charset + LINE_END);
				sb.append(LINE_END);
				dos.write(sb.toString().getBytes());
				is = new FileInputStream(file);
				byte[] bytes = new byte[1024];
				int len = 0;
				long count = 0;
				long totalCount = file.length();
				if (totalCount == 0) {
					totalCount = 1;
				}
				int lastProgress = -1;
				while ((len = is.read(bytes)) != -1) {
					if (listener != null) {
						count += len;
						int progress = (int) ((float) count / totalCount * 100);
						if (lastProgress != progress) {
							lastProgress = progress;
							onLoad(progress);
						}
					}
					dos.write(bytes, 0, len);
				}
				is.close();
				is = null;
				dos.write(LINE_END.getBytes());
				byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END).getBytes();
				dos.write(end_data);
				dos.flush();
				/**
				 * 获取响应码 200=成功 当响应成功，获取响应的流
				 */
				res = conn.getResponseCode();
				if (res == 200) {
					is = conn.getInputStream();
					StringBuffer sb1 = new StringBuffer();
					int ss;
					while ((ss = is.read()) != -1) {
						sb1.append((char) ss);
					}
					final String result = sb1.toString();
					if (listener != null) {
						onComplete(result);
					}
				} else {
					throw new Lib_Exception("HTTP CODE:" + res);
				}
			} catch (ProtocolException e) {
				if (LogUtil.DEBUG) {
					LogUtil.e(e);
				}

				if (listener != null) {
					onError("Http协议异常");
				}
			} catch (FileNotFoundException e) {
				if (LogUtil.DEBUG) {
					LogUtil.e(e);
				}
				if (listener != null) {
					onError("文件未找到异常");
				}
			} catch (MalformedURLException e) {
				if (LogUtil.DEBUG) {
					LogUtil.e(e);
				}
				if (listener != null) {
					onError("URL地址有缺陷");
				}
			} catch (IOException e) {
				if (LogUtil.DEBUG) {
					LogUtil.e(e);
				}
				if (listener != null) {
					onError("IO异常");
				}
			} catch (Lib_Exception e) {
				if (LogUtil.DEBUG) {
					LogUtil.e(e);
				}
				if (listener != null) {
					onError(e._getErrorMessage());
				}
			} catch (Exception e) {
				if (LogUtil.DEBUG) {
					LogUtil.e(e);
				}
				if (listener != null) {
					onError("发生未知异常");
				}
			} finally {
				if (dos != null) {
					try {
						dos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (is != null) {
					try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
