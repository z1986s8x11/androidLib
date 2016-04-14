package org.zsx.android.api.widget;

import org.zsx.android.api.R;
import org.zsx.android.base._BaseActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class ImageView_Activity extends _BaseActivity implements ImageView.OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.widget_imageview);
		ImageView mImageView = (ImageView) findViewById(R.id.act_widget_current_view);
		mImageView.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Toast.makeText(this, "点击", Toast.LENGTH_SHORT).show();
	}
}
