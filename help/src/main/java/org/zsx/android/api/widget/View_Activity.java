package org.zsx.android.api.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import org.zsx.android.api.R;
import org.zsx.android.api._BaseActivity;

public class View_Activity extends _BaseActivity {
    ViewHelper mViewHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.widget_view);
        mViewHelper = (ViewHelper) findViewById(R.id.act_widget_current_view);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mViewHelper.x = event.getX();
                mViewHelper.y = event.getY();
                mViewHelper.invalidate();
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                mViewHelper.x = event.getX();
                mViewHelper.y = event.getY();
                mViewHelper.invalidate();
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }
}

class ViewHelper extends View {
    public float x;
    public float y;
    private Paint mPaint;

    public ViewHelper(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setARGB(50, 255, 255, 255);    //设置Paint对象颜色，参数一为alpha透明通道
        mPaint.setAlpha(50);                //设置alpha不透明度，范围为0~255
        mPaint.setAntiAlias(true);        //是否抗锯齿
        mPaint.setColor(Color.RED);        //设置颜色，这里Android内部定义的有Color类包含了一些常见颜色定义
        mPaint.setFakeBoldText(false);    //设置伪粗体文本
        mPaint.setLinearText(true);        //设置线性文本
        mPaint.setStrokeCap(Paint.Cap.ROUND);    // 定义线段断电形状为圆头
        mPaint.setAntiAlias(true);            // 消除锯齿
        mPaint.setStyle(Paint.Style.STROKE); // 设置空心
//		mPaint.setStrokeWidth(mCircleWidth); // 设置圆环的宽度
//		mPaint. setPathEffect();  //设置路径效果
//		mPaint. setRasterizer(Rasterizer rasterizer);//设置光栅化
//		mPaint. setShader(Shader shader);	//设置阴影
        mPaint.setTextAlign(Paint.Align.CENTER);  //设置文本对齐
        mPaint.setTextScaleX(1.5f);        //设置文本缩放倍数，1.0f为原始
        mPaint.setTextSize(15f);            //设置字体大小
        mPaint.setColor(Color.YELLOW);
    }

    /**
     * 当该组件将要绘制它的内容时回调该方法绘制
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(x, y, 20, mPaint);
    }

    /**
     * 这是一个回调方法,当应用从XML布局文件加载该组件并利用它来构建界面之后,该方法就会被回调
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    /**
     * 调用该方法来检测View组件及它所包含的所有子组件的大小
     * <p/>
     * MeasureSpec的specMode,一共三种类型：
     * EXACTLY：一般是设置了明确的值或者是MATCH_PARENT
     * AT_MOST：表示子布局限制在一个最大值内，一般为WARP_CONTENT
     * UNSPECIFIED：表示子布局想要多大就多大，很少使用
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 当该组件需要分配其子组件的位置,大小时被回调
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    /**
     * 当该组件的大小被改变时回调该方法
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    /**
     * 当某个键被按下时,触发该方法
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 当松开某个键时触发该方法
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return super.onKeyUp(keyCode, event);
    }

    /**
     * 当发生轨迹事件时触发该方法
     */
    @Override
    public boolean onTrackballEvent(MotionEvent event) {
        return super.onTrackballEvent(event);
    }

    /**
     * 当发生触摸屏事件会触发该方法
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    /**
     * 当该组件得到,失去焦点时触发该方法
     */
    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
    }

    /**
     * 把该组件放入某个窗口时触发
     */
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    /**
     * 当把该组件从某个窗口上下分离时触发该方法
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    /**
     * 把包含该组件的窗口的可见性发生改变时触发该方法
     */
    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
    }
}