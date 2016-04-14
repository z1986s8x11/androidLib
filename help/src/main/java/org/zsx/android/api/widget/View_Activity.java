package org.zsx.android.api.widget;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import org.zsx.android.api.R;
import org.zsx.android.base._BaseActivity;

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
        mPaint.setAntiAlias(true);            // 消除锯齿
        mPaint.setColor(Color.RED);        //设置颜色，这里Android内部定义的有Color类包含了一些常见颜色定义
//        mPaint.setColorFilter();         //设置颜色过滤器，可以在绘制颜色时实现不用颜色的变换效果
        mPaint.setDither(true);             //设定是否使用图像抖动处理，会使绘制出来的图片颜色更加平滑和饱满，图像更加清晰
        mPaint.setFakeBoldText(false);    //设置伪粗体文本
        mPaint.setFilterBitmap(false);       //如果该项设置为true，则图像在动画进行中会滤掉对Bitmap图像的优化操作，加快显示速度，本设置项依赖于dither和xfermode的设置
        mPaint.setLinearText(true);        //设置线性文本
        /**
         * CornerPathEffect  可以使用圆角来代替尖锐的角从而对基本图形的形状尖锐的边角进行平滑。
         * DashPathEffect  可以使用DashPathEffect来创建一个虚线的轮廓(短横线/小圆点)，而不是使用实线。你还可以指定任意的虚/实线段的重复模式
         * DiscretePathEffect  与DashPathEffect相似，但是添加了随机性。当绘制它的时候，需要指定每一段的长度和与原始路径的偏离度
         * PathDashPathEffect  这种效果可以定义一个新的形状(路径)并将其用作原始路径的轮廓标记。
         *
         * 下面的效果可以在一个Paint中组合使用多个Path Effect:
         * SumPathEffect  顺序地在一条路径中添加两种效果，这样每一种效果都可以应用到原始路径中，而且两种结果可以结合起来。
         * ComposePathEffect  将两种效果组合起来应用，先使用第一种效果，然后在这种效果的基础上应用第二种效果。
         */
        mPaint.setPathEffect(new CornerPathEffect(10));  //设置路径效果
//		mPaint. setRasterizer(Rasterizer rasterizer);//设置光栅化
        /**
         * BlurMaskFilter   指定了一个模糊的样式和半径来处理Paint的边缘。
         * EmbossMaskFilter  指定了光源的方向和环境光强度来添加浮雕效果。
         */
        mPaint.setMaskFilter(new BlurMaskFilter(10, BlurMaskFilter.Blur.SOLID));           //设置MaskFilter，可以用不同的MaskFilter实现滤镜的效果，如滤化，立体等
        mPaint.setStrokeCap(Paint.Cap.ROUND);    // 定义线段断电形状为圆头
        mPaint.setStyle(Paint.Style.STROKE); // 设置空心
        mPaint.setStrikeThruText(true);     // 设置带有删除线的效果
        mPaint.setShadowLayer(5, 3f, 3f, Color.RED);//在图形下面设置阴影层，产生阴影效果，radius为阴影的角度，dx和dy为阴影在x轴和y轴上的距离，color为阴影的颜色
//      mPaint.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"assets/a"));//设置Typeface对象，即字体风格，包括粗体，斜体以及衬线体，非衬线体等
        /*
         * LinearGradient shader = new LinearGradient(0, 0, endX, endY, new
         * int[]{startColor, midleColor, endColor},new float[]{0 , 0.5f,
         * 1.0f}, TileMode.MIRROR);
         * 参数一为渐变起初点坐标x位置，参数二为y轴位置，参数三和四分辨对应渐变终点
         * 其中参数new int[]{startColor, midleColor,endColor}是参与渐变效果的颜色集合，
         * 其中参数new float[]{0 , 0.5f, 1.0f}是定义每个颜色处于的渐变相对位置， 这个参数可以为null，如果为null表示所有的颜色按顺序均匀的分布
         */
        /**
         *  Shader.TileMode三种模式
         *  REPEAT:沿着渐变方向循环重复
         *  CLAMP:如果在预先定义的范围外画的话，就重复边界的颜色
         *  MIRROR:与REPEAT一样都是循环重复，但这个会对称重复
         */
        mPaint.setShader(new LinearGradient(0, 0, 100, 100, new int[]{Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW}, null, Shader.TileMode.REPEAT));    //设置阴影
//		mPaint.setStrokeWidth(mCircleWidth); // 设置圆环的宽度
        mPaint.setTextAlign(Paint.Align.CENTER);  //设置文本对齐
        mPaint.setTextSkewX(0.2f); //设置斜体文字，skewX为倾斜弧度
        mPaint.setSubpixelText(true);//设置该项为true，将有助于文本在LCD屏幕上的显示效果
        mPaint.setTextScaleX(1.5f);        //设置文本缩放倍数，1.0f为原始
        mPaint.setTextSize(15f);            //设置字体大小
        /**
         * AvoidXfermode  指定了一个颜色和容差，强制Paint避免在它上面绘图(或者只在它上面绘图)。
         * PixelXorXfermode  当覆盖已有的颜色时，应用一个简单的像素XOR操作。
         * PorterDuffXfermode  这是一个非常强大的转换模式，使用它，可以使用图像合成的16条Porter-Duff规则的任意一条来控制Paint如何与已有的Canvas图像进行交互
         */
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        mPaint.setUnderlineText(true);      //设置带有下划线的文字效果
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