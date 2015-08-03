package com.zsx.util;

import android.content.ContentResolver;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.view.View;

import java.io.FileNotFoundException;

public class Lib_Util_Bitmap {

    /**
     * 根据指定的图像路径和大小来获取缩略图 此方法有两点好处： 1.
     * 使用较小的内存空间，第一次获取的bitmap实际上为null，只是为了读取宽度和高度，
     * 第二次读取的bitmap是根据比例压缩过的图像，第三次读取的bitmap是所要的缩略图。 2.
     * 缩略图对于原图像来讲没有拉伸，这里使用了2.2版本的新工具ThumbnailUtils，使 用这个工具生成的图像不会被拉伸。
     *
     * @param imagePath 图像的路径
     * @param width     指定输出图像的宽度
     * @param height    指定输出图像的高度
     * @return 生成的缩略图
     */
    public static Bitmap getImageThumbnail(String imagePath, int width, int height) {
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        // 获取这个图片的宽和高，注意此处的bitmap为null
        bitmap = BitmapFactory.decodeFile(imagePath, options);
        options.inJustDecodeBounds = false; // 设为 false
        // 计算缩放比
        int h = options.outHeight;
        int w = options.outWidth;
        int beWidth = w / width;
        int beHeight = h / height;
        int be = 1;
        if (beWidth < beHeight) {
            be = beWidth;
        } else {
            be = beHeight;
        }
        if (be <= 0) {
            be = 1;
        }
        options.inSampleSize = be;
        // 重新读入图片，读取缩放后的bitmap，注意这次要把options.inJustDecodeBounds 设为 false
        bitmap = BitmapFactory.decodeFile(imagePath, options);
        // 利用ThumbnailUtils来创建缩略图，这里要指定要缩放哪个Bitmap对象
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }

    /**
     * 圆角图片
     *
     * @param bitmap
     * @param pixels
     * @return
     */
    public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;

    }

    public static Bitmap decodeThumbBitmap(Uri uri, ContentResolver resolver, int viewWidth, int viewHeight) throws FileNotFoundException {
        BitmapFactory.Options options = new BitmapFactory.Options();
        // 设置为true,表示解析Bitmap对象，该对象不占内存
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(resolver.openInputStream(uri), null, options);
        // 设置缩放比例
        options.inSampleSize = computeScale(options, viewWidth, viewHeight);
        // 设置为false,解析Bitmap对象加入到内存中
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(resolver.openInputStream(uri), null, options);
    }

    public static Bitmap decodeThumbBitmap(Resources res, int resID, int viewWidth, int viewHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        // 设置为true,表示解析Bitmap对象，该对象不占内存
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(res.openRawResource(resID), null, options);
        // 设置缩放比例
        options.inSampleSize = computeScale(options, viewWidth, viewHeight);
        options.inPreferredConfig = Config.RGB_565;
        // 设置为false,解析Bitmap对象加入到内存中
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(res.openRawResource(resID), null, options);
    }

    public static Bitmap decodeThumbBitmap(String filePath, int viewWidth, int viewHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        // 设置为true,表示解析Bitmap对象，该对象不占内存
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        // 设置缩放比例
        options.inSampleSize = computeScale(options, viewWidth, viewHeight);

        // 设置为false,解析Bitmap对象加入到内存中
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 根据View(主要是ImageView)的宽和高来计算Bitmap缩放比例。默认不缩放
     *
     * @param options
     * @param viewWidth
     * @param viewHeight
     */
    private static int computeScale(BitmapFactory.Options options, int viewWidth, int viewHeight) {
        int inSampleSize = 1;
        if (viewWidth == 0 || viewWidth == 0) {
            return inSampleSize;
        }
        int bitmapWidth = options.outWidth;
        int bitmapHeight = options.outHeight;

        // 假如Bitmap的宽度或高度大于我们设定图片的View的宽高，则计算缩放比例
        if (bitmapWidth > viewWidth || bitmapHeight > viewHeight) {
            int widthScale = Math.round((float) bitmapWidth / (float) viewWidth);
            int heightScale = Math.round((float) bitmapHeight / (float) viewHeight);

            // 为了保证图片不缩放变形，我们取宽高比例最小的那个
            inSampleSize = widthScale < heightScale ? widthScale : heightScale;
        }
        return inSampleSize;
    }

    /**
     * @param view 需要截图的View
     * @return 截图
     */
    public static Bitmap shootmePhoto(View view) {
        Bitmap bmp = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Config.ARGB_8888);
        view.draw(new Canvas(bmp));
        return bmp;
    }
}
