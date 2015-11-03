package org.zsx.android.api.widget;

import org.zsx.android.api.R;
import org.zsx.android.api._BaseActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoView_Activity extends _BaseActivity implements OnClickListener {
	private VideoView mVideoView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.widget_videoview);
		findViewById(R.id.global_btn1).setOnClickListener(this);
		mVideoView = (VideoView) findViewById(R.id.act_widget_current_view);
		MediaController controller = new MediaController(this);
		mVideoView.setMediaController(controller);

	}

	@Override
	public void onClick(View v) {
		/** 需要android.permission.INTERNET权限 */
		mVideoView.setVideoURI(Uri.parse("http://www.androidbook.com/akc/filestorage/android/documentfiles/3389/movie.mp4"));
		mVideoView.requestFocus();
		mVideoView.start();
	}
}
