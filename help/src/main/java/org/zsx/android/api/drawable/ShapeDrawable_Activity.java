package org.zsx.android.api.drawable;

import org.zsx.android.api._BaseActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class ShapeDrawable_Activity extends _BaseActivity implements OnClickListener {
	CustomView customView;
	private int y = 50;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		customView = new CustomView(this);
		customView.setOnClickListener(this);
		setContentView(customView);
	}

	class CustomView extends View {
		private ShapeDrawable shapeDrawable;

		public CustomView(Context context) {
			super(context);
			float mDensity = context.getResources().getDisplayMetrics().density;
			OvalShape circle = new OvalShape();
			circle.resize(50f * mDensity, 50f * mDensity);
			shapeDrawable = new ShapeDrawable(circle);
			int red = (int) (Math.random() * 255);
			int green = (int) (Math.random() * 255);
			int blue = (int) (Math.random() * 255);
			int color = 0xff000000 | red << 16 | green << 8 | blue;
			Paint paint = shapeDrawable.getPaint(); // new
													// Paint(Paint.ANTI_ALIAS_FLAG);
			int darkColor = 0xff000000 | red / 4 << 16 | green / 4 << 8 | blue / 4;
			RadialGradient gradient = new RadialGradient(37.5f, 12.5f, 50f, color, darkColor, Shader.TileMode.CLAMP);
			paint.setShader(gradient);
		}

		@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);
			canvas.save();
			shapeDrawable.draw(canvas);
			canvas.translate(0, y);
			shapeDrawable.draw(canvas);
			canvas.restore();
		}
	}

	@Override
	public void onClick(View v) {
		y += 20;
		customView.invalidate();
	}
}
