package com.zsx.manager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.preference.PreferenceManager;

import com.zsx.debug.LogUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
        File rootPathFile = null;
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
        cachePath = cacheFile.getPath();
        if (!cacheFile.exists() || !cacheFile.isDirectory()) {
            if (!cacheFile.mkdir()) {
                if (LogUtil.DEBUG) {
                    LogUtil.e(
                            "",
                            "file.mkdir() is Error,Path:"
                                    + cacheFile.getPath()
                                    + ",please check  <uses-permission android:name=android.permission.WRITE_EXTERNAL_STORAGE />");
                }
            }
        }
        File logFile = new File(rootPathFile, "log");
        logPath = logFile.getPath();
        if (!logFile.exists() || !logFile.isDirectory()) {
            if (!logFile.mkdir()) {
                if (LogUtil.DEBUG) {
                    LogUtil.e(
                            "",
                            "file.mkdir() is Error,Path:"
                                    + logFile.getPath()
                                    + ",please check  <uses-permission android:name=android.permission.WRITE_EXTERNAL_STORAGE />");
                }
            }
        }
        File cacheBitmapFile = new File(cachePath, "bitmap");
        if (!cacheBitmapFile.exists() || !cacheBitmapFile.isDirectory()) {
            if (!cacheBitmapFile.mkdir()) {
                if (LogUtil.DEBUG) {
                    LogUtil.e(
                            "",
                            "file.mkdir() is Error,Path:"
                                    + cacheBitmapFile.getPath()
                                    + ",please check  <uses-permission android:name=android.permission.WRITE_EXTERNAL_STORAGE />");
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
     * 复制Assets文件到Data/包名/databases/下
     *
     * @param context
     * @param assetsName Assets文件下DB文件名
     */
    @SuppressLint("SdCardPath")
    public static void copyAssetsFileToDataDB(Context context, String assetsName) {
        boolean isFirst = PreferenceManager
                .getDefaultSharedPreferences(context).getBoolean(
                        "lib_assets_to_data_first", false);
        if (!isFirst) {
            PreferenceManager.getDefaultSharedPreferences(context).edit()
                    .putBoolean("lib_assets_to_data_first", true).commit();
            String databasepath = "/data/data/%s/databases";
            InputStream input = null;
            FileOutputStream out = null;
            try {
                input = context.getAssets().open(assetsName);
                File f = new File(String.format(databasepath,
                        context.getApplicationInfo().packageName));
                if (!f.exists()) {
                    if (!f.mkdir()) {
                        if (LogUtil.DEBUG) {
                            LogUtil.e(Lib_FileManager.class, "mkdir is error:"
                                    + f.getPath());
                        }
                    }
                }
                File targetFile = new File(String.format(databasepath,
                        context.getApplicationInfo().packageName), assetsName);
                if (targetFile.exists()) {
                    if (LogUtil.DEBUG) {
                        LogUtil.e(Lib_FileManager.class,
                                "dbname:" + targetFile.getPath()
                                        + " is exists!!!");
                    }
                }
                out = new FileOutputStream(targetFile);
                int count = 0;
                byte[] b = new byte[1024 * 4];
                while ((count = input.read(b)) != -1) {
                    out.write(b, 0, count);
                }
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (input != null) {
                    try {
                        input.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 清除日志
     *
     * @param cacheMaxFileCount 只保留最近产生的多少条
     */
    private final static void clearLogFile(int cacheMaxFileCount) {
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
