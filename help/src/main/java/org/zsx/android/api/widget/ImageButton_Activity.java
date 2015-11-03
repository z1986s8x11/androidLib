package org.zsx.android.api.widget;

import org.zsx.android.api.R;
import org.zsx.android.api._BaseActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class ImageButton_Activity extends _BaseActivity implements ImageButton.OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.widget_imagebutton);
		ImageButton mImageButton = (ImageButton) findViewById(R.id.act_widget_current_view);
		mImageButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Toast.makeText(this, "图片被点击", Toast.LENGTH_SHORT).show();
	}
}
