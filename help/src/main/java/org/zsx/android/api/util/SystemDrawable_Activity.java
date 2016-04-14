package org.zsx.android.api.util;

import java.lang.reflect.Field;

import org.zsx.android.api.R;
import org.zsx.android.base._BaseActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class SystemDrawable_Activity extends _BaseActivity implements OnItemClickListener {

	private TextView mTextView = null;

	private GridView mGridView = null;

	private int id = 0x01080000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.util_system_drawable);
		mGridView = (GridView) findViewById(R.id.global_gridview);
		mGridView.setAdapter(new ImageAdapter(this));
		mGridView.setOnItemClickListener(this);
		mTextView = (TextView) findViewById(R.id.global_textview1);
	}

	/* ImageAdapter */
	public class ImageAdapter extends BaseAdapter {

		private Context context;
		private int count;

		public ImageAdapter(Context c) {
			context = c;
			Field[] f = android.R.drawable.class.getDeclaredFields();
			count = f.length;
		}

		public int getCount() {
			// 不同手机数量不同，可以调节以浏览更多
			return count;
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView iv = new ImageView(context);
			iv.setLayoutParams(new GridView.LayoutParams(-2, -2));
			id = 0x01080000 + position;
			iv.setImageResource(id);
			iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
			return iv;
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		mTextView.setText("图标名称：" + this.getResources().getResourceEntryName(0x01080000 + position) + "\n");
		mTextView.append("图标Id：0x" + Integer.toHexString(0x10800000 + position) + "\n");
		mTextView.append("图标大小:" + view.getWidth() + "x" + view.getHeight() + "\n");
	}
}
