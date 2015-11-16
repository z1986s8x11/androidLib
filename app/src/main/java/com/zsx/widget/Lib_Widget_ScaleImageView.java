package com.zsx.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.zsx.debug.LogUtil;

public class Lib_Widget_ScaleImageView extends ImageView {
	public Lib_Widget_ScaleImageView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public Lib_Widget_ScaleImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public Lib_Widget_ScaleImageView(Context context) {
		super(context);
		init(context);
	}

	private float x_down = 0;
	private float y_down = 0;
	private PointF start = new PointF();
	private PointF mid = new PointF();
	private float oldDist = 1f;
	private float oldRotation = 0;
	private Matrix matrix = new Matrix();
	private Matrix matrix1 = new Matrix();
	private Matrix savedMatrix = new Matrix();

	private static final int NONE = 0;
	private static final int DRAG = 1;
	private static final int ZOOM = 2;
	private int mode = NONE;
	private boolean isRotation = true;
	private boolean matrixCheck = false;

	private int widthScreen;
	private int heightScreen;
	private Bitmap gintama;
	private BitmapDrawable b;

	@SuppressWarnings("deprecation")
	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		b = (getBackground() instanceof BitmapDrawable) ? (BitmapDrawable) getBackground()
				: null;
		if (b != null) {
			gintama = b.getBitmap();
			Matrix m = new Matrix();
			float scaleWidth = widthScreen * 1f / gintama.getWidth();
			float scaleHeight = 1f * heightScreen / gintama.getHeight();
			if (getScaleType() == ScaleType.FIT_XY) {
				m.postScale(scaleWidth, scaleHeight);
			} else if (getScaleType() == ScaleType.FIT_CENTER) {
				m.postScale(scaleWidth, scaleWidth);
			} else {
				m.postScale(1f, 1f);
			}
			gintama = Bitmap.createBitmap(gintama, 0, 0, gintama.getWidth(),
					gintama.getHeight(), m, true);
			setBackgroundDrawable(null);
			center(true, true);
		} else {
			if (LogUtil.DEBUG) {
				LogUtil.e(this, "getBackground() is null!!!!");
			}
		}
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		if (!gintama.isRecycled()) {
			gintama.recycle();
		}
		if (LogUtil.DEBUG) {
			LogUtil.e(this, "onDetachedFromWindow");
		}
	}

	private void init(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay()
				.getMetrics(dm);
		widthScreen = dm.widthPixels;
		heightScreen = dm.heightPixels;
		matrix = new Matrix();
	}

	protected void onDraw(Canvas canvas) {
		canvas.save();
		canvas.drawBitmap(gintama, matrix, null);
		canvas.restore();
	}

	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			mode = DRAG;
			x_down = event.getX();
			y_down = event.getY();
			savedMatrix.set(matrix);
			start.x = x_down;
			start.y = y_down;
			break;
		case MotionEvent.ACTION_POINTER_DOWN:
			mode = ZOOM;
			oldDist = spacing(event);
			if (isRotation) {
				oldRotation = rotation(event);
			}
			savedMatrix.set(matrix);
			midPoint(mid, event);
			break;
		case MotionEvent.ACTION_MOVE:
			if (mode == ZOOM) {
				matrix1.set(savedMatrix);
				float newDist = spacing(event);
				float scale = newDist / oldDist;
				matrix1.postScale(scale, scale, mid.x, mid.y);// 縮放
				if (isRotation) {
					float rotation = rotation(event) - oldRotation;
					matrix1.postRotate(rotation, mid.x, mid.y);// 旋轉
				}
				matrixCheck = matrixCheck();
				if (matrixCheck == false) {
					matrix.set(matrix1);
					invalidate();
				}
			} else if (mode == DRAG) {
				matrix1.set(savedMatrix);
				matrix1.postTranslate(event.getX() - x_down, event.getY()
						- y_down);// 平移
				matrixCheck = matrixCheck();
				matrixCheck = matrixCheck();
				if (matrixCheck == false) {
					matrix.set(matrix1);
					invalidate();
				}
			}
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_POINTER_UP:
			mode = NONE;
			break;
		}
		return true;
	}

	public void _setIsRotate(boolean isRotate) {
		this.isRotation = isRotate;
	}

	private boolean matrixCheck() {
		float[] f = new float[9];
		matrix1.getValues(f);
		// 图片4个顶点的坐标
		float x1 = f[0] * 0 + f[1] * 0 + f[2];
		float y1 = f[3] * 0 + f[4] * 0 + f[5];
		float x2 = f[0] * gintama.getWidth() + f[1] * 0 + f[2];
		float y2 = f[3] * gintama.getWidth() + f[4] * 0 + f[5];
		float x3 = f[0] * 0 + f[1] * gintama.getHeight() + f[2];
		float y3 = f[3] * 0 + f[4] * gintama.getHeight() + f[5];
		float x4 = f[0] * gintama.getWidth() + f[1] * gintama.getHeight()
				+ f[2];
		float y4 = f[3] * gintama.getWidth() + f[4] * gintama.getHeight()
				+ f[5];
		// 图片现宽度
		double width = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
		// 缩放比率判断
		if (width < widthScreen / 3 || width > widthScreen * 3) {
			return true;
		}
		// 出界判断
		if ((x1 < widthScreen / 3 && x2 < widthScreen / 3
				&& x3 < widthScreen / 3 && x4 < widthScreen / 3)
				|| (x1 > widthScreen * 2 / 3 && x2 > widthScreen * 2 / 3
						&& x3 > widthScreen * 2 / 3 && x4 > widthScreen * 2 / 3)
				|| (y1 < heightScreen / 3 && y2 < heightScreen / 3
						&& y3 < heightScreen / 3 && y4 < heightScreen / 3)
				|| (y1 > heightScreen * 2 / 3 && y2 > heightScreen * 2 / 3
						&& y3 > heightScreen * 2 / 3 && y4 > heightScreen * 2 / 3)) {
			return true;
		}
		return false;
	}

	private void center(boolean horizontal, boolean vertical) {
		Matrix m = new Matrix();
		m.set(matrix);
		int dw = gintama.getWidth();
		int dh = gintama.getHeight();
		RectF rect = new RectF(0, 0, dw, dh);
		m.mapRect(rect);
		float height = rect.height();
		float width = rect.width();
		float deltaX = 0, deltaY = 0;
		if (vertical) {
			int screenHeight = heightScreen; // 手机屏幕分辨率的高度
			if (height < screenHeight) {
				deltaY = (screenHeight - height) / 2 - rect.top;
			} else if (rect.top > 0) {
				deltaY = -rect.top;
			} else if (rect.bottom < screenHeight) {
				deltaY = getHeight() - rect.bottom;
			}
		}

		if (horizontal) {
			int screenWidth = widthScreen; // 手机屏幕分辨率的宽度
			if (width < screenWidth) {
				deltaX = (screenWidth - width) / 2 - rect.left;
			} else if (rect.left > 0) {
				deltaX = -rect.left;
			} else if (rect.right < screenWidth) {
				deltaX = screenWidth - rect.right;
			}
		}
		matrix.postTranslate(deltaX, deltaY);
	}

	// 触碰两点间距离
	private float spacing(MotionEvent event) {
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return FloatMath.sqrt(x * x + y * y);
	}

	// 取手势中心点
	private void midPoint(PointF point, MotionEvent event) {
		float x = event.getX(0) + event.getX(1);
		float y = event.getY(0) + event.getY(1);
		point.set(x / 2, y / 2);
	}

	// 取旋转角度
	private float rotation(MotionEvent event) {
		double delta_x = (event.getX(0) - event.getX(1));
		double delta_y = (event.getY(0) - event.getY(1));
		double radians = Math.atan2(delta_y, delta_x);
		return (float) Math.toDegrees(radians);
	}

	/**
	 * 将移动，缩放以及旋转后的图层保存为新图片
	 */
	public Bitmap _getNewBitmap() {
		Bitmap bitmap = Bitmap.createBitmap(widthScreen, heightScreen,
				Config.ARGB_8888); // 背景图片
		Canvas canvas = new Canvas(bitmap); // 新建画布
		canvas.drawBitmap(gintama, matrix, null); // 画图片
		canvas.save(Canvas.ALL_SAVE_FLAG); // 保存画布
		canvas.restore();
		return bitmap;
	}
}
