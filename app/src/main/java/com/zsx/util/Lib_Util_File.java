package com.zsx.util;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.os.storage.StorageManager;
import android.provider.MediaStore;

import com.zsx.debug.LogUtil;
import com.zsx.tools.Lib_UnicodeInputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author zsx
 * @date 2014-4-1
 * @description
 */
public class Lib_Util_File {
    /**
     * 返回清除Bom了的InputStream
     *
     * @param in
     * @return
     * @throws IOException
     */
    public static InputStream clearBom(InputStream in) throws IOException {
        return new Lib_UnicodeInputStream(in, "utf-8");
    }

    /**
     * 是否存在SD卡
     */
    public static boolean isExistSDCard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        } else
            return false;
    }

    /**
     * @return 拿到SD可用空间大小
     */
    public static long getSDAvaiableSize() {
        if (isExistSDCard()) {
            File SDPath = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(SDPath.getPath());
            /* 空闲的Block数量 */
            long availableBlocks = stat.getAvailableBlocks();
            /* 获取Block大小 */
            long blockSize = stat.getBlockSize();
            return availableBlocks * blockSize;
        }
        return -1;
    }

    /**
     * @return 拿到SD可用空间大小
     */
    public static long getSDSize() {
        if (isExistSDCard()) {
            File SDPath = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(SDPath.getPath());
            /* 空闲的Block数量 */
            long blocks = stat.getBlockCount();
            /* 获取Block大小 */
            long blockSize = stat.getBlockSize();
            return blocks * blockSize;
        }
        return -1;
    }

    /**
     * 压缩成JPG 保存到文件中
     */
    public static boolean saveToFile(InputStream in, String savePath) {
        boolean isSuccess = false;
        OutputStream stream = null;
        try {
            stream = new FileOutputStream(savePath);
            byte[] buffer = new byte[1024];
            int count;
            while ((count = in.read(buffer)) != -1) {
                stream.write(buffer, 0, count);
            }
            stream.close();
            isSuccess = true;
        } catch (Exception e) {
            isSuccess = false;
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return isSuccess;
    }

    /**
     * @param fileDir  文件目录
     * @param fileName 文件名
     * @return 文件路径
     */
    public static String getFilePath(String fileDir, String fileName) {
        return new File(fileDir, fileName).getAbsolutePath();
    }

    /**
     * 压缩成JPG 保存到文件中
     */
    public static boolean saveToFile(Bitmap bitmap, String savePath) {
        int quality = 100;// 压缩到文件的品质 0-100
        boolean isSuccess = true;
        File file = new File(savePath);
        if (!file.getParentFile().exists()) {
            file.mkdirs();
        }
        if (file.exists()) {
            file.delete();
        }
        OutputStream stream = null;
        try {
            stream = new FileOutputStream(savePath);
            isSuccess = bitmap.compress(Bitmap.CompressFormat.JPEG, quality,
                    stream);
        } catch (Exception e) {
            e.printStackTrace();
            if (file.exists()) {
                file.delete();
            }
            isSuccess = false;
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return isSuccess;
    }

    /**
     * 计算SD卡的剩余空间
     *
     * @return 返回-1，说明没有安装sd卡
     */
    public static long getSDFreeDiskSpace() {
        String status = Environment.getExternalStorageState();
        long freeSpace;
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long availableBlocks = stat.getAvailableBlocks();
            freeSpace = availableBlocks * blockSize;
        } else {
            return -1;
        }
        return (freeSpace);
    }

    /**
     * 计算系统的剩余空间
     */
    public static long getSystemFreeDiskSpace() {
        File root = Environment.getRootDirectory();
        long freeSpace;
        StatFs stat = new StatFs(root.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        freeSpace = availableBlocks * blockSize;
        return (freeSpace);
    }

    /**
     * 删除文件夹中所有文件
     *
     * @param file
     */
    public static void deleteFile(File file) {
        if (file == null) {
            return;
        }
        if (!file.exists()) {
            return;
        }
        if (file.isFile()) {
            file.delete();
            return;
        }
        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            for (int i = 0; i < childFiles.length; i++) {
                if (childFiles[i].isDirectory()) {
                    deleteFile(childFiles[i]);
                } else {
                    childFiles[i].delete();
                }
            }
        }
    }

    /**
     * 获取文件扩展名
     *
     * @param filePath
     * @return ""或者后缀
     */
    public static String getFileSuffix(String filePath) {
        if (filePath == null) {
            return "";
        }
        if (filePath.trim().length() == 0) {
            return "";
        }
        int point = filePath.lastIndexOf('.');
        return filePath.substring(point + 1);
    }

    @SuppressWarnings("resource")
    public static Serializable readObject(String objectFilePath) {
        File objectFile = new File(objectFilePath);
        if (!objectFile.exists()) {
            return null;
        }
        Serializable object = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(objectFilePath);
            ObjectInputStream ois = new ObjectInputStream(fis);
            object = (Serializable) ois.readObject();
        } catch (Exception e) {
            LogUtil.w(e);
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (objectFile.exists()) {
                objectFile.delete();
            }
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return object;
    }

    public static boolean writeObject(Serializable object, String savePath) {
        if (object == null) {
            return false;
        }
        File objectFile = new File(savePath);
        if (!objectFile.getParentFile().exists()) {
            objectFile.getParentFile().mkdirs();
        }
        if (objectFile.exists()) {
            objectFile.delete();
        }
        File f = new File(savePath);
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(f);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
            oos.flush();
            return true;
        } catch (IOException e) {
            LogUtil.w(e);
            if (objectFile.exists()) {
                objectFile.delete();
            }
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * 获取内置存储位置
     *
     * @param context
     * @return
     */
    public static String getBuildInPath(Context context) {
        StorageManager storageManager = (StorageManager) context
                .getSystemService(Context.STORAGE_SERVICE);
        String path = null;

        try {
            Class<?>[] paramClasses = {};
            Method getVolumePathsMethod = StorageManager.class.getMethod(
                    "getVolumePaths", paramClasses);
            getVolumePathsMethod.setAccessible(true);
            Object[] params = {};
            Object invoke = getVolumePathsMethod.invoke(storageManager, params);
            String[] invokes = (String[]) invoke;
            for (int i = 0; i < invokes.length; i++) {
                if (!Environment.getExternalStorageDirectory().getPath()
                        .equals(invokes[i])) {
                    return invokes[i];
                }
            }

        } catch (NoSuchMethodException e1) {
            e1.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return path;
    }

    public static void copyAssetToSDCard(Context context,
                                         String assetsFileName, File sdFile) {
        if (!sdFile.canWrite()) {
            throw new IllegalArgumentException("sdFile 不能写");
        }
        if (sdFile.exists()) {
            return;
        }
        InputStream ins = null;
        FileOutputStream out = null;
        try {
            ins = context.getAssets().open(assetsFileName);
            out = new FileOutputStream(sdFile);
            byte[] buffer = new byte[1024];
            int count = -1;
            while ((count = ins.read(buffer)) != -1) {
                out.write(buffer, 0, count);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ins != null) {
                try {
                    ins.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public File toFile(Activity activity, Uri uri) {
        File file = null;
        if ("file:".contains(uri.getScheme())) {
            try {
                file = new File(new URI(uri.toString()));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        } else if ("content:".contains(uri.getScheme())) {
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor actualimagecursor = activity.managedQuery(uri, proj, null, null, null);
            int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            actualimagecursor.moveToFirst();
            String img_path = actualimagecursor.getString(actual_image_column_index);
            //actualimagecursor.close(); //关闭之后再次打开会报错 (managedQuery 关联的是Activity activity 销毁cursor销毁)
            file = new File(img_path);
        }
        return file;
    }
}
