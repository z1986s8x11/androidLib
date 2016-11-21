package org.zsx.android.api.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import org.zsx.android.base._BaseActivity;

/**
 * Author       zhusx
 * Email        327270607@qq.com
 * Created      2016/11/21 13:50
 */

public class Region_Activity extends _BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new CircleView(this));
    }

    public class CircleView extends View {
        Region circleRegion;
        Path circlePath;
        Paint p;

        public CircleView(Context context) {
            super(context);
            circlePath = new Path();
            circleRegion = new Region();
            p = new Paint();
            p.setColor(Color.RED);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            // ▼在屏幕中间添加一个圆
            circlePath.addCircle(w / 2, h / 2, 300, Path.Direction.CW);
            // ▼将剪裁边界设置为视图大小
            Region globalRegion = new Region(-w, -h, w, h);
            // ▼将 Path 添加到 Region 中
            circleRegion.setPath(circlePath, globalRegion);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    int x = (int) event.getX();
                    int y = (int) event.getY();
                    // ▼点击区域判断
                    if (circleRegion.contains(x, y)) {
                        Toast.makeText(this.getContext(), "圆被点击", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
            return true;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            // 绘制圆
            canvas.drawPath(circlePath, p);
        }
    }
}
