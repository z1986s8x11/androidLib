package org.zsx.android.api.media;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.zsx.network.Lib_NetworkStateReceiver;
import com.zsx.util._NetworkUtil;

import org.zsx.android.api.R;
import org.zsx.android.api._BaseActivity;

import java.io.IOException;

public class MediaPlayer_Activity extends _BaseActivity implements OnClickListener {
    private MediaPlayer mMediaPlayer;
    private int playbackPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.media_mediaplayer);
        findViewById(R.id.global_btn1).setOnClickListener(this);
        findViewById(R.id.global_btn2).setOnClickListener(this);
        findViewById(R.id.global_btn3).setOnClickListener(this);
        findViewById(R.id.global_btn4).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.global_btn1:
                if (mMediaPlayer == null) {
                    mMediaPlayer = new MediaPlayer();
                    // mMediaPlayer = MediaPlayer.create(this, R.raw.xxx);
                }
                try {
                    if (Lib_NetworkStateReceiver._Current_NetWork_Status == _NetworkUtil.NetType.Wifi) {
                        /** 支持File Path 和 Http URL */
                        mMediaPlayer
                                .setDataSource("http://58.17.218.66:81/1Q2W3E4R5T6Y7U8I9O0P1Z2X3C4V5B/zhangmenshiting.baidu.com/data2/music/62455549/339768001391958061128.mp3?xcode=902203a4833f06603579c85123d135be32fc4f3d81fce8d4");
                        mMediaPlayer.prepare();
                        mMediaPlayer.start();
                    }
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (SecurityException e) {
                    e.printStackTrace();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.global_btn2:
                if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
                    playbackPosition = mMediaPlayer.getCurrentPosition();
                    mMediaPlayer.pause();
                }
                break;
            case R.id.global_btn3:
                if (mMediaPlayer != null && !mMediaPlayer.isPlaying()) {
                    mMediaPlayer.seekTo(playbackPosition);
                    mMediaPlayer.start();
                }
                break;
            case R.id.global_btn4:
                if (mMediaPlayer != null) {
                    mMediaPlayer.stop();
                    playbackPosition = 0;
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
        }
    }
}
