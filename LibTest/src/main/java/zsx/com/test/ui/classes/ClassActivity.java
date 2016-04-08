package zsx.com.test.ui.classes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.zsx.debug.LogUtil;
import com.zsx.tools.Lib_WindowsHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;

import dalvik.system.DexFile;
import zsx.com.test.R;
import zsx.com.test.base._BaseActivity;

/**
 * Author       zhusx
 * Email        327270607@qq.com
 * Created      2016/3/24 10:54
 */
//DexClassLoader
//PathClassLoader
public class ClassActivity extends _BaseActivity implements View.OnClickListener {
    private static String[] getClassesFromPackage(Context context) {
        ArrayList<String> classes = new ArrayList<String>();
        String packageName = context.getPackageName();
        try {
            LogUtil.e("====", context.getPackageCodePath());
            DexFile df = new DexFile(context.getPackageCodePath());
            Enumeration<String> entries = df.entries();
            while (entries.hasMoreElements()) {
                String className = entries.nextElement();
                if (className.contains(packageName)) {
                    if (!className.contains("$")) {
                        LogUtil.e("====", className);
                        classes.add(className);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classes.toArray(new String[]{});
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);
        findViewById(R.id.tv_title).setOnClickListener(this);
    }

    private TextView t;
    Lib_WindowsHelper helper;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_title:
                if (helper == null) {
                    helper = new Lib_WindowsHelper(v.getContext());
                }
                if (t == null) {
                    t = new TextView(getBaseContext());
                    t.setTextColor(Color.BLACK);
                    t.setPadding(20, 20, 20, 20);
                    t.setBackgroundColor(Color.WHITE);
                    t.setText("通知来了");
                }
                if (t.getParent() == null) {
                    helper._show(t);
                    t.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (t.getParent() != null) {
                                helper._hide(t);
                            }
                        }
                    }, 3000);
                }
//                Lib_Subscribes.subscribe(new Lib_Subscribes.Subscriber<String>() {
//                    @Override
//                    public String doInBackground() {
//                        Arrays.toString(getClassesFromPackage(ClassActivity.this));
//                        return "";
//                    }
//                });
                break;
        }
    }
}

class CustomView extends View {

    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);//设置为空心
        paint.setStrokeWidth(3);
        canvas.drawCircle(40, 40, 30, paint);
        canvas.drawRect(10, 90, 70, 150, paint);
        canvas.drawRect(10, 170, 70, 200, paint);
        canvas.drawOval(new RectF(10, 220, 70, 250), paint);
        Path path = new Path();//三角形
        path.moveTo(10, 330);
        path.lineTo(70, 330);
        path.lineTo(40, 270);
        path.close();
        canvas.drawPath(path, paint);
        Path path1 = new Path();//梯形
        path1.moveTo(10, 410);//绘画基点
        path1.lineTo(70, 410);
        path1.lineTo(55, 350);
        path1.lineTo(25, 350);
        path1.close();//把开始的点和最后的点连接在一起，构成一个封闭图形
        canvas.drawPath(path1, paint);
        ///////////////////////////////////////第二列
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);//设置实心
        canvas.drawCircle(120, 40, 30, paint);
        canvas.drawRect(90, 90, 150, 150, paint);
        canvas.drawRect(90, 170, 150, 200, paint);
        RectF re2 = new RectF(90, 220, 150, 250);
        canvas.drawOval(re2, paint);
        Path path2 = new Path();
        path2.moveTo(90, 330);
        path2.lineTo(150, 330);
        path2.lineTo(120, 270);
        path2.close();
        canvas.drawPath(path2, paint);
        Path path3 = new Path();
        path3.moveTo(90, 410);
        path3.lineTo(150, 410);
        path3.lineTo(135, 350);
        path3.lineTo(105, 350);
        path3.close();
        canvas.drawPath(path3, paint);
        ////////////////////////////////////////////////////第三列
        Shader mShader = new LinearGradient(0, 0, 100, 100, new int[]{Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW}, null, Shader.TileMode.REPEAT);
        paint.setShader(mShader);// 用Shader中定义定义的颜色来话
        canvas.drawCircle(200, 40, 30, paint);
        canvas.drawRect(170, 90, 230, 150, paint);
        canvas.drawRect(170, 170, 230, 200, paint);
        RectF re3 = new RectF(170, 220, 230, 250);
        canvas.drawOval(re3, paint);
        Path path4 = new Path();
        path4.moveTo(170, 330);
        path4.lineTo(230, 330);
        path4.lineTo(200, 270);
        path4.close();
        canvas.drawPath(path4, paint);
        Path path5 = new Path();
        path5.moveTo(170, 410);
        path5.lineTo(230, 410);
        path5.lineTo(215, 350);
        path5.lineTo(185, 350);
        path5.close();
        canvas.drawPath(path5, paint);
        //////////////////////////////////第4列
        paint.setTextSize(24);
        canvas.drawText("圆形", 240, 50, paint);
        canvas.drawText("正方形", 240, 120, paint);
        canvas.drawText("长方形", 240, 190, paint);
        canvas.drawText("椭圆形", 240, 250, paint);
        canvas.drawText("三角形", 240, 320, paint);
        canvas.drawText("梯形", 240, 390, paint);
    }
}