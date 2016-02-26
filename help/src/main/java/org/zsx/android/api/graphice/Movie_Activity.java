package org.zsx.android.api.graphice;

import org.zsx.android.api.R;
import org.zsx.android.api._BaseActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

public class Movie_Activity extends _BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new MovieView(this));
	}

	class MovieView extends View {
		private Movie movie;
		private long startTime;

		public MovieView(Context context) {
			super(context);
			movie = Movie.decodeStream(context.getResources().openRawResource(
					R.drawable.animated_gif));
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
				if (!isHardwareAccelerated()) {
                    // 必须配置 :android:hardwareAccelerated="false" 或者setLayerType
                    setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                }
			}
		}

		@Override
		protected void onDraw(Canvas canvas) {
			long now = android.os.SystemClock.uptimeMillis();
			if (startTime == 0) {
				startTime = now;
			}
			if (movie != null) {
				int dur = movie.duration();
				if (dur == 0) {
					dur = 1000;
				}
				int relTime = (int) ((now - startTime) % dur);
				movie.setTime(relTime);
				movie.draw(canvas, 0, 0);
				invalidate();
			}
		}
	}
}
