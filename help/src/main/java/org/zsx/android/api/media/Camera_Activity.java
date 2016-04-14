package org.zsx.android.api.media;

import java.io.File;

import org.zsx.android.api.R;
import org.zsx.android.base._BaseActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Camera_Activity extends _BaseActivity implements Button.OnClickListener {
	private Bitmap mBitmap;
	private ImageView image;
	private String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/zsx/zsx.png";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.media_camera);
		Button b = (Button) findViewById(R.id.global_btn1);
		image = (ImageView) findViewById(R.id.global_imageview1);
		b.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent in = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/zsx");
			if (!f.exists()) {
				f.mkdir();
			}
			in.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(path)));
		}
		startActivityForResult(in, 2);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case 2:
			if (resultCode == Activity.RESULT_OK) {
				if (mBitmap != null) {
					mBitmap.recycle();
				}
				// mBitmap = (Bitmap) data.getExtras().get("data");
				// ByteArrayOutputStream os = new ByteArrayOutputStream();
				// mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
				// image.setImageBitmap(BitmapFactory.decodeByteArray(
				// os.toByteArray(), 0, os.toByteArray().length));
				if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
					int maxH = 200;
					BitmapFactory.Options options = new BitmapFactory.Options();
					options.inJustDecodeBounds = true;
					// 获取这个图片的宽和高
					mBitmap = BitmapFactory.decodeFile(path, options); // 此时返回bm为空
					// 计算缩放比
					int be = (int) (options.outHeight / (float) maxH);
					int ys = options.outHeight % maxH;// 求余数
					float fe = ys / (float) maxH;
					if (fe >= 0.5)
						be = be + 1;
					if (be <= 0)
						be = 1;
					options.inSampleSize = be;
					options.inJustDecodeBounds = false;
					mBitmap = BitmapFactory.decodeFile(path, options);
					image.setImageBitmap(mBitmap);
				}
			}
			break;
		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
