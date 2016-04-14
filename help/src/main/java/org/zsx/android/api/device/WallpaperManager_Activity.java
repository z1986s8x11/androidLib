package org.zsx.android.api.device;

import java.io.IOException;

import org.zsx.android.api.R;
import org.zsx.android.base._BaseActivity;

import android.app.WallpaperManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class WallpaperManager_Activity extends _BaseActivity {
	final static private int[] mColors = { Color.BLUE, Color.GREEN, Color.RED,
			Color.LTGRAY, Color.MAGENTA, Color.CYAN, Color.YELLOW, Color.WHITE };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// Be sure to call the super class.
		super.onCreate(savedInstanceState);
		// See res/layout/wallpaper_2.xml for this
		// view layout definition, which is being set here as
		// the content of our screen.
		setContentView(R.layout.device_wallpaper);
		final WallpaperManager wallpaperManager = WallpaperManager
				.getInstance(this);
		final Drawable wallpaperDrawable = wallpaperManager.getDrawable();
		final ImageView imageView = (ImageView) findViewById(R.id.global_imageview1);
		imageView.setDrawingCacheEnabled(true);
		imageView.setImageDrawable(wallpaperDrawable);

		Button randomize = (Button) findViewById(R.id.global_btn1);
		randomize.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				int mColor = (int) Math.floor(Math.random() * mColors.length);
				wallpaperDrawable.setColorFilter(mColors[mColor],
						PorterDuff.Mode.MULTIPLY);
				imageView.setImageDrawable(wallpaperDrawable);
				imageView.invalidate();
			}
		});

		Button setWallpaper = (Button) findViewById(R.id.global_btn2);
		setWallpaper.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				try {
					wallpaperManager.setBitmap(imageView.getDrawingCache());
					finish();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
}
