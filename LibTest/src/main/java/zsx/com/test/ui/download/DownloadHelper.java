package zsx.com.test.ui.download;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;

import com.zsx.debug.LogUtil;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Administrator on 2016/1/9.
 */
public class DownloadHelper {
    public final int requestCode = 0x583;
    public final int notifyId = 0x432;
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private Handler mHandler = new Handler(Looper.getMainLooper());
    public static ConcurrentHashMap<String, Item> map = new ConcurrentHashMap<>();
    private NotificationManager mNotificationManager;
    private Context context;

    public DownloadHelper(Context context) {
        this.context = context.getApplicationContext();
        mNotificationManager = (NotificationManager) context.getSystemService(Activity.NOTIFICATION_SERVICE);
    }

    private Notification createNotification(Context context, String text, int progress) {
        PendingIntent intent = PendingIntent.getActivity(context, requestCode, new Intent(), PendingIntent.FLAG_NO_CREATE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentTitle(context.getResources().getString(context.getApplicationInfo().labelRes))//设置通知栏标题
                .setContentText(text)//设置通知栏显示内容
                .setContentIntent(intent)//设置通知栏点击意图
                .setSmallIcon(context.getApplicationInfo().icon)//设置通知小ICON
                .setOngoing(true);//设置为ture，表示它为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
        if (progress > 0 && progress < 100) {
            builder.setProgress(100, progress, false);
        }
        if (map.size() > 1) {
            builder.setNumber(map.size());
        }
        return builder.build();
    }

    public void download(DownloadBean bean) {
        if (bean == null) {
            return;
        }
        if (map.containsKey(bean.getKey())) {
            return;
        }
        DownloadRunnable runnable = new DownloadRunnable(bean);
        Future<?> future = executor.submit(runnable);
        map.put(bean.getKey(), new Item(future, runnable));
    }

    public void cancelDownload(String key) {
        Item item = map.get(key);
        if (item != null) {
            map.remove(key);
            item.future.cancel(true);
            item.runnable.isCancel = true;
        }
    }

    public void onDownloadStart(final DownloadBean en) {
        if (mNotificationManager != null) {
            mNotificationManager.notify(notifyId, createNotification(context, "准备下载", 1));
        }
        if (listener != null) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    listener.onStart(en.getKey());
                }
            });
        }
    }

    private void onDownloadProgress(final DownloadBean en, final int progress) {
        if (mNotificationManager != null) {
            mNotificationManager.notify(notifyId, createNotification(context, String.format("正在下载: %d %%", progress), progress));
        }
        if (listener != null) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    listener.onProgress(en.getKey(), progress);
                }
            });
        }
    }

    private void onDownloadComplete(final DownloadBean en) {
        if (mNotificationManager != null) {
            mNotificationManager.cancel(notifyId);
        }
        if (listener != null) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    listener.onComplete(en.getKey());
                }
            });
        }
    }

    private void onDownloadError(final DownloadBean en, final String errorMessage) {
        if (mNotificationManager != null) {
            mNotificationManager.notify(notifyId, createNotification(context, "下载发生错误", 100));
        }
        if (listener != null) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    listener.onError(en.getKey(), errorMessage);
                }
            });
        }
    }

    private void onDownloadCancel(final String key) {
        if (mNotificationManager != null) {
            mNotificationManager.cancel(notifyId);
        }
    }

    private static class Item {
        public Future<?> future;
        public DownloadRunnable runnable;

        public Item(Future<?> future, DownloadRunnable runnable) {
            this.future = future;
            this.runnable = runnable;
        }
    }

    public class DownloadRunnable implements Runnable {
        private DownloadBean en;
        private boolean isCancel = false;

        public DownloadRunnable(DownloadBean bean) {
            this.en = bean;
        }

        @Override
        public void run() {
            try {
                onDownloadStart(en);
//                Lib_Util_HttpURLRequest.downloadFile(url, path, new Lib_Util_HttpURLRequest.OnProgressListener() {
//                    @Override
//                    public void onProgress(int progress, int currentSize, int totalSize) {
//                        onDownloadProgress(key, progress);
//                    }
//
//                    @Override
//                    public boolean isCanceled() {
//                        return isCancel;
//                    }
//                });
                for (int i = 0; i < 100; i++) {
                    Thread.sleep(200);
                    onDownloadProgress(en, i);
                }
                onDownloadComplete(en);
//            } catch (Lib_Exception e) {
//                e.printStackTrace();
//                if (e._getErrorCode() != Lib_Exception.ERROR_CODE_CANCEL) {
//                    onDownloadError(key, e._getErrorMessage());
//                } else {
//                    onDownloadCancel(key);
//                }
//            } catch (ConnectTimeoutException e) {
//                if (LogUtil.DEBUG) {
//                    LogUtil.w(e);
//                }
//                onDownloadError(key, "连接超时");
//            } catch (SocketTimeoutException e) {
//                if (LogUtil.DEBUG) {
//                    LogUtil.w(e);
//                }
//                onDownloadError(key, "请求超时");
//            } catch (IOException e) {
//                if (LogUtil.DEBUG) {
//                    LogUtil.w(e);
//                }
//                if (e.getMessage().contains("write failed: ENOSPC (No space left on device)")) {
//                    onDownloadError(key, "磁盘空间不足");
//                } else {
//                    onDownloadError(key, "发生未知错误");
//                }
            } catch (Exception e) {
                if (LogUtil.DEBUG) {
                    LogUtil.w(e);
                }
                onDownloadError(en, "发生未知错误");
            } finally {
                map.remove(en.getKey());
            }
        }
    }

    private OnDownloadListener listener;

    public interface OnDownloadListener {
        void onStart(String key);

        void onComplete(String key);

        void onError(String key, String errorMessage);

        void onProgress(String key, int progress);
    }

    public void setDownloadListener(OnDownloadListener listener) {
        this.listener = listener;
    }
}
