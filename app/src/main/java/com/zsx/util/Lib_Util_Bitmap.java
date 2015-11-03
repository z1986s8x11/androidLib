package com.zsx.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Lib_Util_Bitmap {
    /**
     * 压缩图片
     */
    public static Bitmap decodeBitmap(String filePath, int sampleSizeWidth, int sampleSizeHeight, int compress) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        options.inSampleSize = calculateInSampleSize(options, sampleSizeWidth, sampleSizeHeight);
        options.inJustDecodeBounds = false;
        Bitmap bm = BitmapFactory.decodeFile(filePath, options);
        if (bm == null) {
            return null;
        }
        int degree = readPictureDegree(filePath);
        bm = rotateBitmap(bm, degree);
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, compress, baos);
        } finally {
            try {
                if (baos != null)
                    baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bm;
    }

    /**
     * @param view 需要截图的View
     * @return 截图
     */
    public static Bitmap shootmePhoto(View view) {
        Bitmap bmp = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        view.draw(new Canvas(bmp));
        return bmp;
    }

    /**
     * 获取图片旋转
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 旋转Bitmap
     */
    public static Bitmap rotateBitmap(Bitmap bitmap, int rotate) {
        if (bitmap == null)
            return null;
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix mtx = new Matrix();
        mtx.postRotate(rotate);
        return Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        double outMax = Math.max(options.outHeight, options.outWidth);
        double outMin = Math.min(options.outHeight, options.outWidth);
        double reqMax = Math.max(reqHeight, reqWidth);
        double reqMin = Math.min(reqHeight, reqWidth);
        int inSampleSize = 1;
        if (outMax > reqMax || outMin > reqMin) {
            int maxRatio = Math.round((float) outMax / (float) reqMax);
            int minRatio = Math.round((float) outMin / (float) reqMin);
            inSampleSize = maxRatio < minRatio ? minRatio : maxRatio;
        }
        return inSampleSize;
    }
}
