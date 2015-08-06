package com.zsx.download;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import com.zsx.debug.LogUtil;
import com.zsx.exception.Lib_Exception;
import com.zsx.util.Lib_Util_Network;
import com.zsx.network.Lib_NetworkStateReceiver;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.conn.ConnectTimeoutException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
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
    public static ExecutorService EXCUTORS = Executors.newFixedThreadPool(2);
    public static Map<String, Lib_DownloadInterface> map = new ConcurrentHashMap<String, Lib_DownloadInterface>();
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
                map.put(data.getDownloadKey(), data);
                EXCUTORS.execute(new downloadTask(data));
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
            map.clear();
            EXCUTORS.shutdownNow();
            EXCUTORS = null;
            EXCUTORS = Executors.newFixedThreadPool(2);
        }
    }

    private class downloadTask implements Runnable {
        private Lib_DownloadInterface data;
        private Intent intent;

        public downloadTask(Lib_DownloadInterface data) {
            this.data = data;
            intent = new Intent(Lib_DownloadReceiver._DOWNLOAD_ACTION);
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
            startDownload();
            InputStream input = null;
            FileOutputStream fos = null;
            HttpURLConnection conn = null;
            try {
                File file = new File(data.getSavePath());
                if (!file.getParentFile().exists()) {
                    if (!file.getParentFile().mkdirs()) {
                        if (LogUtil.DEBUG) {
                            LogUtil.e(
                                    this,
                                    "创建文件夹失败:"
                                            + file.getParentFile().getPath()
                                            + "\n检查是否加入android.permission.WRITE_EXTERNAL_STORAGE和android.permission.MOUNT_UNMOUNT_FILESYSTEMS");
                        }
                        throw new Lib_Exception("创建文件夹失败");
                    }
                }
                if (file.exists() && Math.abs(System.currentTimeMillis() - file.lastModified()) < 10 * 60 * 1000) {
                    data.doSucess(getApplicationContext());
                    completeDownload();
                    return;
                }
                File fileTemp = new File(file.getPath() + ".tmp");
                if (fileTemp.exists()) {
                    if (!fileTemp.delete()) {
                        if (LogUtil.DEBUG) {
                            LogUtil.e(this, "删除文件失败:" + fileTemp.getPath());
                        }
                        throw new Lib_Exception("删除文件失败");
                    }
                }
                fos = new FileOutputStream(fileTemp);
                int totalByte = 0;
                int progress = -1;
                conn = (HttpURLConnection) new URL(data.getDownloadUrl()).openConnection();
                // 设置超时
                conn.setConnectTimeout(60000);
                // 读取超时 一般不设置
                // conn.setReadTimeout(30000);
                conn.setRequestMethod("GET");
                // 设置方法为 GET
                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    String contentLength = conn
                            .getHeaderField("Content-Length");
                    if (contentLength == null) {
                        if (LogUtil.DEBUG) {
                            LogUtil.e(this, "Content-Length 文件大小获取失败");
                        }
                    } else {
                        totalByte = Integer.parseInt(contentLength);
                    }
                    input = conn.getInputStream();
                    int count = 0;
                    int num = 0;
                    byte[] b = new byte[1024 * 2];
                    while ((count = input.read(b)) != -1) {
                        fos.write(b, 0, count);
                        num += count;
                        int current_progress = getProgress(num, totalByte);
                        if (progress != current_progress) {
                            progress = current_progress;
                            loadingDownload(progress);
                        }
                    }
                    if (file.exists() && file.isFile()) {
                        file.delete();
                    }
                    fileTemp.renameTo(file);
                    data.doSucess(getApplicationContext());
                    completeDownload();
                } else {
                    errorDownload("HttpCode:"
                            + conn.getResponseCode());
                }
            } catch (Lib_Exception e) {
                if (LogUtil.DEBUG) {
                    LogUtil.w(e);
                }
                errorDownload(e.getMessage());
            } catch (ConnectTimeoutException e) {
                if (LogUtil.DEBUG) {
                    LogUtil.w(e);
                }
                errorDownload("链接超时");
            } catch (SocketTimeoutException e) {
                if (LogUtil.DEBUG) {
                    LogUtil.w(e);
                }
                errorDownload("请求超时");
            } catch (ClientProtocolException e) {
                if (LogUtil.DEBUG) {
                    LogUtil.w(e);
                }
                errorDownload("客户端协议异常");
            } catch (IOException e) {
                if (LogUtil.DEBUG) {
                    LogUtil.w(e);
                }
                if (e.getMessage().contains(
                        "write failed: ENOSPC (No space left on device)")) {
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
                if (conn != null) {
                    conn.disconnect();
                }
            }
        }

        public int getProgress(int current, int total) {
            if (total > 0) {
                return (int) ((float) current / total * 100);
            }
            return 0;
        }
    }

}
