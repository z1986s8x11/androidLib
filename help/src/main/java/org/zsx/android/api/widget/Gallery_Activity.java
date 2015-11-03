package org.zsx.android.api.widget;

import org.zsx.android.api.R;
import org.zsx.android.api._BaseActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Gallery google不推荐使用
 */
@SuppressWarnings("deprecation")
public class Gallery_Activity extends _BaseActivity implements Gallery.OnItemSelectedListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.widget_gallery);
		Gallery mGallery = (Gallery) findViewById(R.id.act_widget_current_view);
		int[] images = new int[] { android.R.drawable.ic_dialog_alert, android.R.drawable.ic_dialog_dialer, android.R.drawable.ic_dialog_info,
				android.R.drawable.ic_dialog_map, android.R.drawable.ic_dialog_email };
		mGallery.setAdapter(new zAdapter(this, images));
		mGallery.setOnItemSelectedListener(this);
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Toast.makeText(this, "第" + arg2 + "个", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {

	}

	class zAdapter extends BaseAdapter {
		private int[] images;
		private Context context;

		public zAdapter(Context context, int[] images) {
			this.images = images;
			this.context = context;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return images.length;
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView iv = new ImageView(context);
			iv.setImageResource(images[position]);
			iv.setScaleType(ImageView.ScaleType.FIT_XY);
			return iv;
		}
	}
}
