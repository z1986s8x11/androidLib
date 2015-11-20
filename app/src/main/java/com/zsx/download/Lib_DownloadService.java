package com.zsx.download;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import com.zsx.debug.LogUtil;
import com.zsx.exception.Lib_Exception;
import com.zsx.network.Lib_NetworkStateReceiver;
import com.zsx.util.Lib_Util_HttpURLRequest;
import com.zsx.util.Lib_Util_Network;

import org.apache.http.conn.ConnectTimeoutException;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <code>if(!Lib_DownloadService.isDownloading("download_key")
 * </br>{
 * </br> Intent in = new Intent(this,Lib_DownloadService.class);
 * </br> in.putExtra(Lib_DownloadService.EXTRA_DOWNLOAD_DATA,Serializable);
 * </br> startService(in);
 * </br>}
 * </code>
 *
 * @author zsx
 */
public class Lib_DownloadService extends Service {
    public static ExecutorService EXECUTORS = Executors.newFixedThreadPool(2);
    public static Map<String, DownloadTask> map = new ConcurrentHashMap<String, DownloadTask>();
    public static final String _EXTRA_DOWNLOAD_DATA = "download_data";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static boolean _isDownloading(String downloadKey) {
        return map.containsKey(downloadKey);
    }

    public static void _startService(Context content, String downloadKey,
                                     String downloadUrl, String downloadSavePath) {
        Intent in = new Intent(content, Lib_DownloadService.class);
        Lib_DownloadBean bean = new Lib_DownloadBean(downloadKey, downloadUrl,
                downloadSavePath);
        in.putExtra(_EXTRA_DOWNLOAD_DATA, bean);
        content.startService(in);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) {
            return super.onStartCommand(intent, flags, startId);
        }
        Object object = intent.getSerializableExtra(_EXTRA_DOWNLOAD_DATA);
        Lib_DownloadInterface data;
        if (object == null) {
            if (LogUtil.DEBUG) {
                LogUtil.e(
                        this,
                        "please:Intent.putExtra(RM_Download_DownloadService.EXTRA_DOWNLOAD_DATA,Serializable)");
            }
            return super.onStartCommand(intent, flags, startId);
        }
        if (object instanceof Lib_DownloadInterface) {
            data = (Lib_DownloadInterface) object;
            if (data.getDownloadKey() == null || data.getDownloadUrl() == null
                    || data.getSavePath() == null) {
                if (LogUtil.DEBUG) {
                    LogUtil.e(
                            this,
                            "Lib_DownloadBean.getDownloadKey() and Lib_DownloadBean.getDownloadUrl() and Lib_DownloadBean.getSavePath() must is not null");
                }
                return super.onStartCommand(intent, flags, startId);
            }
            if (!_isDownloading(data.getDownloadKey())) {
                if (Lib_NetworkStateReceiver._Current_NetWork_Status == Lib_Util_Network.NetType.NoneNet) {
                    Toast.makeText(getApplicationContext(), "没有网络连接!",
                            Toast.LENGTH_SHORT).show();
                    return super.onStartCommand(intent, flags, startId);
                }
                DownloadTask task = new DownloadTask(data);
                map.put(data.getDownloadKey(), task);
                EXECUTORS.execute(task);
                return super.onStartCommand(intent, flags, startId);
            } else {
                if (LogUtil.DEBUG) {
                    LogUtil.e(
                            this,
                            "Download key:"
                                    + String.valueOf(data.getDownloadKey())
                                    + " is downloading...");
                }
                return super.onStartCommand(intent, flags, startId);
            }
        } else {
            if (LogUtil.DEBUG) {
                LogUtil.e(
                        this,
                        "Intent.putExtra(EXTRA_DOWNLOAD_DATA,Serializable),Serializable extends Lib_DownloadBean.class");
            }
            return super.onStartCommand(intent, flags, startId);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!map.isEmpty()) {
            if (LogUtil.DEBUG) {
                LogUtil.e(this, "正在关闭下载");
            }
            for (String key : map.keySet()) {
                map.get(key).setCancel();
            }
            map.clear();
            EXECUTORS.shutdownNow();
            EXECUTORS = null;
            EXECUTORS = Executors.newFixedThreadPool(2);
        }
    }

    private class DownloadTask implements Runnable {
        private Lib_DownloadInterface data;
        private Intent intent;
        private boolean isCancel = false;

        public DownloadTask(Lib_DownloadInterface data) {
            this.data = data;
            intent = new Intent(Lib_DownloadReceiver._DOWNLOAD_ACTION);
        }

        public void setCancel() {
            this.isCancel = true;
        }

        private void startDownload() {
            if (LogUtil.DEBUG) {
                LogUtil.d(this, "key:" + data.getDownloadKey() + "\t 开始下载");
            }
            intent.putExtra(
                    Lib_DownloadReceiver.DOWNLOAD_EXTRAS_INT_STATUS_KEY,
                    Lib_DownloadReceiver.OnDownloadListener.DOWNLOAD_STATUS_START);
            intent.putExtra(Lib_DownloadReceiver.DOWNLOAD_EXTRAS_OBJECT_KEY,
                    data);
            sendBroadcast(intent);
        }

        private void loadingDownload(int progress) {
            if (LogUtil.DEBUG) {
                LogUtil.d(this, "key:" + data.getDownloadKey() + "\tloading :"
                        + progress);
            }
            intent.putExtra(
                    Lib_DownloadReceiver.DOWNLOAD_EXTRAS_INT_STATUS_KEY,
                    Lib_DownloadReceiver.OnDownloadListener.DOWNLOAD_STATUS_LOAD);
            intent.putExtra(
                    Lib_DownloadReceiver.DOWNLOAD_EXTRAS_INT_PROGRESS_KEY,
                    progress);
            intent.putExtra(Lib_DownloadReceiver.DOWNLOAD_EXTRAS_OBJECT_KEY,
                    data);
            sendBroadcast(intent);
        }

        private void errorDownload(String errorMessage) {
            if (LogUtil.DEBUG) {
                LogUtil.d(this, "key:" + data.getDownloadKey() + "\t 下载失败");
            }
            intent.putExtra(
                    Lib_DownloadReceiver.DOWNLOAD_EXTRAS_STRING_MESSAGE_KEY,
                    errorMessage);
            intent.putExtra(
                    Lib_DownloadReceiver.DOWNLOAD_EXTRAS_INT_STATUS_KEY,
                    Lib_DownloadReceiver.OnDownloadListener.DOWNLOAD_STATUS_ERROR);
            intent.putExtra(Lib_DownloadReceiver.DOWNLOAD_EXTRAS_OBJECT_KEY,
                    data);
            sendBroadcast(intent);
        }

        private void completeDownload() {
            if (LogUtil.DEBUG) {
                LogUtil.d(this, "key:" + data.getDownloadKey() + "\t 下载完成");
            }
            intent.putExtra(
                    Lib_DownloadReceiver.DOWNLOAD_EXTRAS_INT_STATUS_KEY,
                    Lib_DownloadReceiver.OnDownloadListener.DOWNLOAD_STATUS_COMPLETE);
            intent.putExtra(Lib_DownloadReceiver.DOWNLOAD_EXTRAS_OBJECT_KEY,
                    data);
            sendBroadcast(intent);
        }

        @Override
        public void run() {
            try {
                startDownload();
                Lib_Util_HttpURLRequest.downloadFile(data.getDownloadUrl(), data.getSavePath(), new Lib_Util_HttpURLRequest.OnProgressListener() {
                    @Override
                    public void onProgress(final int progress, int currentSize, int totalSize) {
                        loadingDownload(progress);
                    }

                    @Override
                    public boolean isCanceled() {
                        return isCancel;
                    }
                });
                data.doSuccess(getApplicationContext());
                completeDownload();
            } catch (Lib_Exception e) {
                e.printStackTrace();
                if (e._getErrorCode() != Lib_Exception.ERROR_CODE_CANCEL) {
                    errorDownload(e._getErrorMessage());
                } else {
//                    onDownloadCancel(key);
                }
            } catch (ConnectTimeoutException e) {
                if (LogUtil.DEBUG) {
                    LogUtil.w(e);
                }
                errorDownload("连接超时");
            } catch (SocketTimeoutException e) {
                if (LogUtil.DEBUG) {
                    LogUtil.w(e);
                }
                errorDownload("请求超时");
            } catch (IOException e) {
                if (LogUtil.DEBUG) {
                    LogUtil.w(e);
                }
                if (e.getMessage().contains("write failed: ENOSPC (No space left on device)")) {
                    errorDownload("磁盘空间不足");
                } else {
                    errorDownload("发生未知错误");
                }
            } catch (Exception e) {
                if (LogUtil.DEBUG) {
                    LogUtil.w(e);
                }
                errorDownload("发生未知错误");
            } finally {
                map.remove(data.getDownloadKey());
                if (map.isEmpty()) {
                    stopSelf();
                }
            }
        }
    }

}
