package org.zsx.android.api.graphice;

import org.zsx.android.base._BaseActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.os.Bundle;
import android.view.View;

public class Arcs_Activity extends _BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new ArcsView(this));
	}

	class ArcsView extends View {
		Paint p = new Paint();
		RectF oval = new RectF(0, 0, 150, 150);

		public ArcsView(Context context) {
			super(context);
			p.setColor(Color.YELLOW);
			p.setStyle(Style.FILL);
		}

		@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);
			canvas.drawArc(oval, 90f, 270f, false, p);
		}
	}
}
