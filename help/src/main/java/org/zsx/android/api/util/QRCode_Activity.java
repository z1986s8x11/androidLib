package org.zsx.android.api.util;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.zsx.android.api.R;
import org.zsx.android.base._BaseActivity;

import zsx.lib.qrcode.main.Lib_QRCodeActivity;
import zsx.lib.qrcode.main.Lib_QRCodeGenerateUtil;

public class QRCode_Activity extends _BaseActivity implements OnClickListener {
	private TextView resultTV;
	private final int intentRequestCode = 0x55;
	private EditText inputET;
	private ImageView imageView;
	private Bitmap tempBitmap;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.util_qrcode);
		findViewById(R.id.global_btn1).setOnClickListener(this);
		findViewById(R.id.global_btn2).setOnClickListener(this);
		inputET = (EditText) findViewById(R.id.global_edittext1);
		resultTV = (TextView) findViewById(R.id.global_textview1);
		imageView = (ImageView) findViewById(R.id.global_imageview1);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.global_btn1 :
				Intent in = new Intent(this, Lib_QRCodeActivity.class);
				startActivityForResult(in, intentRequestCode);
				break;
			case R.id.global_btn2 :
				String input = inputET.getText().toString();
				if (TextUtils.isEmpty(input)) {
					_showToast("请输入内容");
					return;
				}
				if (tempBitmap != null) {
					if (!tempBitmap.isRecycled()) {
						tempBitmap.recycle();
					}
				}
				tempBitmap = Lib_QRCodeGenerateUtil.createQRImage(input, 200, 200);
				imageView.setImageBitmap(tempBitmap);
				break;
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
			case intentRequestCode :
				if (resultCode == Activity.RESULT_OK) {
					resultTV.setText(data.getStringExtra(Lib_QRCodeActivity._FORMAT) + "\n"
							+ data.getStringExtra(Lib_QRCodeActivity._TEXT));
				}
				break;
		}
	}
}
