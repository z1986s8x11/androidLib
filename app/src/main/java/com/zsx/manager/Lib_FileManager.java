package com.zsx.manager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Environment;

import com.zsx.debug.LogUtil;
import com.zsx.util.Lib_Util_System;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Comparator;

public final class Lib_FileManager {
    private static String rootPath;
    private static String cachePath;
    private static String logPath;
    private static String bitmapCachePath;

    public static void init(String rootDirName, Context context) {
        if (rootDirName == null) {
            return;
        }
        if (rootDirName.trim().length() == 0) {
            return;
        }
        if (!Lib_Util_System.isPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            if (LogUtil.DEBUG) {
                LogUtil.e(Lib_FileManager.class, "需要权限:" + Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            return;
        }
        File rootPathFile;
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            rootPathFile = new File(Environment.getExternalStorageDirectory()
                    .getPath(), rootDirName);
        } else {
            rootPathFile = new File(context.getDir(rootDirName,
                    Context.MODE_PRIVATE).toString());
        }
        if (!rootPathFile.exists() || !rootPathFile.isDirectory()) {
            rootPathFile.mkdirs();
        }
        rootPath = rootPathFile.getPath();
        File cacheFile = new File(rootPathFile, "cache");
        if (!cacheFile.exists() || !cacheFile.isDirectory()) {
            if (!cacheFile.mkdir()) {
                if (LogUtil.DEBUG) {
                    LogUtil.e(Lib_FileManager.class, "mkdir() is Error,Path:" + cacheFile.getPath());
                }
            }
        }
        cachePath = cacheFile.getPath();
        File logFile = new File(rootPathFile, "log");
        if (!logFile.exists() || !logFile.isDirectory()) {
            if (!logFile.mkdir()) {
                if (LogUtil.DEBUG) {
                    LogUtil.e(Lib_FileManager.class, "mkdir() is Error,Path:" + logFile.getPath());
                }
            }
        }
        logPath = logFile.getPath();
        File cacheBitmapFile = new File(cachePath, "bitmap");
        if (!cacheBitmapFile.exists() || !cacheBitmapFile.isDirectory()) {
            if (!cacheBitmapFile.mkdir()) {
                if (LogUtil.DEBUG) {
                    LogUtil.e(Lib_FileManager.class, "mkdir() is Error,Path:" + cacheBitmapFile.getPath());
                }
            }
        }
        bitmapCachePath = cacheBitmapFile.getPath();
        clearLogFile(10);
    }

    /**
     * 拿到项目根路径
     *
     * @return
     */
    public final static String getRootPath() {
        return rootPath;
    }

    /**
     * 拿到项目缓存路径
     *
     * @return
     */
    public final static String getCachePath() {
        return cachePath;
    }

    /**
     * 图片缓存路径
     *
     * @return
     */
    public final static String getBitmapCachePath() {
        return bitmapCachePath;
    }

    /**
     * 拿到项目日志路径
     *
     * @return
     */
    public final static String getLogPath() {
        return logPath;
    }

    /**
     * 清除缓存目录文件
     */
    public final static void clearCacheFolder() {
        clearLogFile(0);
        clearCacheFolder(new File(cachePath), 0);
    }

    /**
     * 清除缓存目录
     *
     * @param dir     目录
     * @param curTime 当前系统时间
     * @return
     */
    public static int clearCacheFolder(File dir, long curTime) {
        if (dir == null) {
            return 0;
        }
        if (!dir.exists()) {
            return 0;
        }
        int deletedFiles = 0;
        if (dir.isDirectory()) {
            try {
                for (File child : dir.listFiles()) {
                    if (child.isDirectory()) {
                        deletedFiles += clearCacheFolder(child, curTime);
                    }
                    if (child.lastModified() < curTime) {
                        if (child.delete()) {
                            deletedFiles++;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return deletedFiles;
    }

    /**
     * 拿到缓存目录大小
     *
     * @return
     */
    public static long getCacheFolderSize() {
        return getFileSize(new File(cachePath));
    }

    /**
     * 文件或者文件夹的大小
     */
    public static long getFileSize(File f) {
        if (!f.exists()) {
            return -1;
        }
        if (f.isFile()) {
            return f.length();
        }
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileSize(flist[i]);
            } else {
                size = size + flist[i].length();
            }
        }
        return size;
    }

    /**
     * 复制Assets文件到Data/包名/files/下
     * <p>
     * 使用数据库用 SQLiteDatabase.openOrCreateDatabase(new File(context.getFilesDir(), dbName).getPath(), null);
     */
    @SuppressLint("SdCardPath")
    public static void copyAssetsFileToDataDB(final Context context, String assetsName, String dbName) {
        File file = new File(context.getFilesDir(), dbName);
        if (file.exists() && file.length() > 0) {
            return;
        }
        new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... params) {
                // 拷贝资产目录下的数据库 到系统的data/data/包名/files/目录
                AssetManager am = context.getAssets();
                try {
                    InputStream is = am.open(params[0]);
                    File file = new File(context.getFilesDir(), params[1]);
                    FileOutputStream fos = new FileOutputStream(file);
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = is.read(buffer)) != -1) {
                        fos.write(buffer, 0, len);
                    }
                    fos.close();
                    is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute(assetsName, dbName);
    }

    /**
     * 清除日志
     *
     * @param cacheMaxFileCount 只保留最近产生的多少条
     */
    private static void clearLogFile(int cacheMaxFileCount) {
        File logFile = new File(logPath);
        File[] fs = logFile.listFiles();
        if (fs == null) {
            return;
        }
        if (fs.length > cacheMaxFileCount) {
            Arrays.sort(fs, new Comparator<File>() {
                public int compare(File f1, File f2) {
                    long diff = f1.lastModified() - f2.lastModified();
                    if (diff > 0)
                        return -1;
                    else if (diff == 0)
                        return 0;
                    else
                        return 1;
                }
            });
            for (int i = Math.max(0, cacheMaxFileCount); i < fs.length; i++) {
                File deleteLogFile = new File(fs[i].getPath());
                deleteLogFile.delete();
            }
        }
    }
}
