package zsx.com.test.ui.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import com.zsx.debug.LogUtil;

/**
 * Author       zhusx
 * Email        327270607@qq.com
 * Created      2016/4/5 16:39
 */
public class CustomView extends View {
    private Paint mPaint;
    private Rect mBound;
    private String text = "测试";

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LogUtil.e(this, "init");
        mPaint = new Paint();
        mBound = new Rect();
        mPaint.getTextBounds(text, 0, text.length(), mBound);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        LogUtil.e(this, "onDraw");
        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);
        mPaint.setColor(Color.RED);
        canvas.drawText(text, getWidth() / 2 - mBound.width(), getHeight() / 2 - mBound.height() / 2, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        LogUtil.e(this, "onMeasure");
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        // EXACTLY：一般是设置了明确的值或者是MATCH_PARENT
        // AT_MOST：表示子布局限制在一个最大值内，一般为WARP_CONTENT
        // UNSPECIFIED：表示子布局想要多大就多大，很少使用
        if (mode == MeasureSpec.EXACTLY) {
            LogUtil.e(this, "MeasureSpec.EXACTLY" + height);
        } else if (mode == MeasureSpec.AT_MOST) {
            LogUtil.e(this, "MeasureSpec.AT_MOST" + height);
        } else {
            LogUtil.e(this, "UNSPECIFIED" + height);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 这是一个回调方法,当应用从XML布局文件加载该组件并利用它来构建界面之后,该方法就会被回调
     */
    @Override
    protected void onFinishInflate() {
        LogUtil.e(this, "onFinishInflate");
        super.onFinishInflate();
    }

    /**
     * 当该组件需要分配其子组件的位置,大小时被回调
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        LogUtil.e(this, "onLayout");
    }

    /**
     * 当该组件的大小被改变时回调该方法
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        LogUtil.e(this, "onSizeChanged");
    }

    /**
     * 当某个键被按下时,触发该方法
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        LogUtil.e(this, "onKeyDown");
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 当松开某个键时触发该方法
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        LogUtil.e(this, "onKeyUp");
        return super.onKeyUp(keyCode, event);
    }

    /**
     * 当发生轨迹事件时触发该方法
     */
    @Override
    public boolean onTrackballEvent(MotionEvent event) {
        LogUtil.e(this, "onTrackballEvent");
        return super.onTrackballEvent(event);
    }

    /**
     * 当发生触摸屏事件会触发该方法
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtil.e(this, "onTouchEvent");
        return super.onTouchEvent(event);
    }

    /**
     * 当该组件得到,失去焦点时触发该方法
     */
    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        LogUtil.e(this, "onWindowFocusChanged");
        super.onWindowFocusChanged(hasWindowFocus);
    }

    /**
     * 把该组件放入某个窗口时触发
     */
    @Override
    protected void onAttachedToWindow() {
        LogUtil.e(this, "onAttachedToWindow");
        super.onAttachedToWindow();
    }

    /**
     * 当把该组件从某个窗口上下分离时触发该方法
     */
    @Override
    protected void onDetachedFromWindow() {
        LogUtil.e(this, "onDetachedFromWindow");
        super.onDetachedFromWindow();
    }

    /**
     * 把包含该组件的窗口的可见性发生改变时触发该方法
     */
    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        LogUtil.e(this, "onWindowVisibilityChanged");
        super.onWindowVisibilityChanged(visibility);
    }
}
