package org.zsx.android.api.media;

import org.zsx.android.api.R;
import org.zsx.android.base._BaseActivity;

import android.media.AsyncPlayer;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class AsyncPlayer_Activity extends _BaseActivity implements OnClickListener {
	private AsyncPlayer mAsyncPlayer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.media_asyncplayer);
		findViewById(R.id.global_btn1).setOnClickListener(this);
		String tag = getClass().getSimpleName();
		mAsyncPlayer = new AsyncPlayer(tag);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.global_btn1:
			boolean isLooping = false;
			Uri uri = Uri
					.parse("http://58.17.218.66:81/1Q2W3E4R5T6Y7U8I9O0P1Z2X3C4V5B/zhangmenshiting.baidu.com/data2/music/62455549/339768001391958061128.mp3?xcode=902203a4833f06603579c85123d135be32fc4f3d81fce8d4");
			mAsyncPlayer.play(this, uri, isLooping, AudioManager.STREAM_MUSIC);
			break;

		default:
			break;
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		mAsyncPlayer.stop();
	}
}
