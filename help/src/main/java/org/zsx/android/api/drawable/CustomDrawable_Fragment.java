package org.zsx.android.api.drawable;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.zsx.android.api.R;
import org.zsx.android.base._BaseFragment;

/**
 * Author       zhusx
 * Email        327270607@qq.com
 * Created      2016/8/5 13:53
 */
public class CustomDrawable_Fragment extends _BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LinearLayout linearLayout = new LinearLayout(inflater.getContext());
        ImageView imageView = new ImageView(inflater.getContext());
        imageView.setBackgroundDrawable(new CircleImageDrawable(((BitmapDrawable) getResources().getDrawable(R.drawable.iv_big_1)).getBitmap()));
        linearLayout.addView(imageView);
        return linearLayout;
    }

    /**
     * 自定义Drawable
     */
    public static class CircleImageDrawable extends Drawable {
        private Paint mPaint;
        private int mWidth;

        //private RectF rectF;
        public CircleImageDrawable(Bitmap bitmap) {
            BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setShader(bitmapShader);
            mWidth = Math.min(bitmap.getWidth(), bitmap.getHeight());
        }

        @Override
        public void draw(Canvas canvas) {
            canvas.drawCircle(mWidth / 2, mWidth / 2, mWidth / 2, mPaint);
//            canvas.drawRoundRect(rectF, rx, ry, mPaint);
        }

        //@Override
//        public void setBounds(int left, int top, int right, int bottom) {
//            super.setBounds(left, top, right, bottom);
//            rectF = new RectF(left, top, right, bottom);
//        }

        @Override
        public int getIntrinsicWidth() {
            return mWidth;
        }

        @Override
        public int getIntrinsicHeight() {
            return mWidth;
        }

        @Override
        public void setAlpha(int alpha) {
            mPaint.setAlpha(alpha);
        }

        @Override
        public void setColorFilter(ColorFilter cf) {
            mPaint.setColorFilter(cf);
        }

        @Override
        public int getOpacity() {
            return PixelFormat.TRANSLUCENT;
        }

    }
}
