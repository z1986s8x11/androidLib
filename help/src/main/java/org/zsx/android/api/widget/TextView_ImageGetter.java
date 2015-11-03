package org.zsx.android.api.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.Html.ImageGetter;
import android.text.Spanned;
import android.widget.TextView;

import com.zsx.manager.Lib_FileManager;
import com.zsx.util.Lib_Util_Encryption;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.zsx.android.api.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;

public class TextView_ImageGetter implements ImageGetter {
	private Context context;
	private TextView mImageTV;

	public TextView_ImageGetter(Context context, TextView imageTV) {
		this.context = context;
		this.mImageTV = imageTV;
	}

	@Override
	public Drawable getDrawable(String source) {

		if (source.contains("http://") || source.contains("https://")) {
			return getNetWorkDrawable(source);
		} else {
			return getFaceDrawable(source);
		}
	}

	private Drawable getFaceDrawable(String source) {
		Drawable faceDraw = null;
		String tempName = source;// demo传入的source是 "ic_launcher";
		try {
			Field field = R.drawable.class.getDeclaredField(tempName);
			int resourceId = Integer.parseInt(field.get(null).toString());
			faceDraw = context.getResources().getDrawable(resourceId);
			faceDraw.setBounds(0, 0, faceDraw.getIntrinsicWidth(),
					faceDraw.getIntrinsicHeight());
		} catch (Exception e) {
			CustomDrawable drawable = new CustomDrawable(context.getResources()
					.getDrawable(R.drawable.ic_launcher));
			drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
					drawable.getIntrinsicHeight());
			return drawable;
		}
		return faceDraw;
	}

	private Drawable getNetWorkDrawable(String source) {
		// 将source进行MD5加密并保存至本地
		String imageName = Lib_Util_Encryption.encodeMD5(source);
		// 获取图片后缀名
		String[] ss = source.split("\\.");
		String ext = ss[ss.length - 1];

		// 最终图片保持的地址
		String savePath = Lib_FileManager.getCachePath() + File.separator
				+ imageName + "." + ext;

		File file = new File(savePath);
		if (file.exists()) {
			// 如果文件已经存在，直接返回
			Drawable drawable = Drawable.createFromPath(savePath);
			drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
					drawable.getIntrinsicHeight());
			return drawable;
		}

		// 不存在文件时返回默认图片，并异步加载网络图片
		Resources res = context.getResources();
		CustomDrawable drawable = new CustomDrawable(
				res.getDrawable(R.drawable.ic_launcher));
		new ImageAsync(drawable).execute(savePath, source);
		return drawable;
	}

	private class ImageAsync extends AsyncTask<String, Integer, Drawable> {

		private CustomDrawable drawable;

		public ImageAsync(CustomDrawable drawable) {
			this.drawable = drawable;
		}

		@Override
		protected Drawable doInBackground(String... params) {
			String savePath = params[0];
			String url = params[1];

			InputStream in = null;
			try {
				// 获取网络图片
				HttpGet http = new HttpGet(url);
				HttpClient client = new DefaultHttpClient();
				HttpResponse response = (HttpResponse) client.execute(http);
				BufferedHttpEntity bufferedHttpEntity = new BufferedHttpEntity(
						response.getEntity());
				in = bufferedHttpEntity.getContent();

			} catch (Exception e) {
				try {
					if (in != null)
						in.close();
				} catch (Exception e2) {
				}
			}

			if (in == null)
				return drawable;

			try {
				File file = new File(savePath);
				String basePath = file.getParent();
				File basePathFile = new File(basePath);
				if (!basePathFile.exists()) {
					basePathFile.mkdirs();
				}
				file.createNewFile();
				FileOutputStream fileout = new FileOutputStream(file);
				byte[] buffer = new byte[4 * 1024];
				while (in.read(buffer) != -1) {
					fileout.write(buffer);
				}
				fileout.flush();
				fileout.close();
				Drawable mDrawable = Drawable.createFromPath(savePath);
				return mDrawable;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return drawable;
		}

		@Override
		protected void onPostExecute(Drawable result) {
			super.onPostExecute(result);
			if (result != null) {
				/** 通过这里的重新设置 TextView 的文字来更新UI */
				mImageTV.setText((Spanned)mImageTV.getTag());
			}
		}
	}

	public class CustomDrawable extends BitmapDrawable {
		private Drawable drawable;

		public CustomDrawable(Drawable defaultDraw) {
			drawable = defaultDraw;
			drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
					drawable.getIntrinsicHeight());
			setBounds(0, 0, drawable.getIntrinsicWidth(),
					drawable.getIntrinsicHeight());
		}

		@Override
		public void draw(Canvas canvas) {
			drawable.draw(canvas);
		}
	}
}
