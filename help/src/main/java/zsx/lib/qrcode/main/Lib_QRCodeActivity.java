package zsx.lib.qrcode.main;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.zsx.debug.LogUtil;

import java.io.IOException;
import java.util.Vector;

import zsx.lib.qrcode.camera.CameraManager;
import zsx.lib.qrcode.decoding.CaptureActivityHandler;
import zsx.lib.qrcode.decoding.InactivityTimer;
import zsx.lib.qrcode.view.ViewfinderView;

/**
 * 二维码
 * 
 * @author zsx
 */
public class Lib_QRCodeActivity extends Activity implements Callback {
	private CaptureActivityHandler handler;
	private ViewfinderView viewfinderView;
	private boolean hasSurface;
	private Vector<BarcodeFormat> decodeFormats;
	private String characterSet;
	private InactivityTimer inactivityTimer;
	// private MediaPlayer mediaPlayer;
	// private boolean playBeep;
	// private static final float BEEP_VOLUME = 0.10f;
	private boolean vibrate;
	public static final String _TEXT = "text";
	public static final String _FORMAT = "format";
	private SurfaceView surfaceView;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 隐藏标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		// MOTO XT800 必须设置这里 否则软键盘会自动弹出来
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		CameraManager.init(getApplication());
		FrameLayout main = new FrameLayout(this);
		surfaceView = new SurfaceView(this);
		main.addView(surfaceView, new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		viewfinderView = new ViewfinderView(this);
		viewfinderView.setBackgroundResource(android.R.color.transparent);
		main.addView(viewfinderView, new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		hasSurface = false;
		inactivityTimer = new InactivityTimer(this);
		setContentView(main, new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		// orientation|keyboardHidden
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onResume() {
		super.onResume();
		// SurfaceView surfaceView = (SurfaceView)
		// findViewById(R.id.preview_view);
		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		if (hasSurface) {
			initCamera(surfaceHolder);
		} else {
			surfaceHolder.addCallback(this);
			surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}
		decodeFormats = null;
		characterSet = null;

		// playBeep = true;
		// AudioManager audioService = (AudioManager)
		// getSystemService(AUDIO_SERVICE);
		// if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL)
		// {
		// playBeep = false;
		// }
		// initBeepSound();
		vibrate = true;
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		CameraManager.get().closeDriver();
	}

	@Override
	protected void onDestroy() {
		inactivityTimer.shutdown();
		super.onDestroy();
	}

	private void initCamera(SurfaceHolder surfaceHolder) {
		try {
			CameraManager.get().openDriver(surfaceHolder);
		} catch (IOException ioe) {
			return;
		} catch (RuntimeException e) {
			return;
		}
		if (handler == null) {
			handler = new CaptureActivityHandler(this, decodeFormats,
					characterSet);
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (!hasSurface) {
			hasSurface = true;
			initCamera(holder);
		}

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;

	}

	public ViewfinderView getViewfinderView() {
		return viewfinderView;
	}

	public Handler getHandler() {
		return handler;
	}

	public void drawViewfinder() {
		viewfinderView.drawViewfinder();

	}

	public void handleDecode(Result obj, Bitmap barcode) {
		inactivityTimer.onActivity();
		// 显示扫描出来的二维码
		// viewfinderView.drawResultBitmap(barcode);
		playBeepSoundAndVibrate();
		// txtResult.setText(obj.getBarcodeFormat().toString() + ":"
		// + obj.getText());
		Intent data = new Intent();
		data.putExtra(_FORMAT, obj.getBarcodeFormat().toString());
		data.putExtra(_TEXT, obj.getText());
		setResult(Activity.RESULT_OK, data);
		finish();
	}

	// private void initBeepSound() {
	// if (playBeep && mediaPlayer == null) {
	// // The volume on STREAM_SYSTEM is not adjustable, and users found it
	// // too loud,
	// // so we now play on the music stream.
	// setVolumeControlStream(AudioManager.STREAM_MUSIC);
	// mediaPlayer = new MediaPlayer();
	// mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
	// mediaPlayer.setOnCompletionListener(beepListener);
	//
	// AssetFileDescriptor file = getResources().openRawResourceFd(android.R.);
	// try {
	// mediaPlayer.setDataSource(file.getFileDescriptor(),
	// file.getStartOffset(), file.getLength());
	// file.close();
	// mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
	// mediaPlayer.prepare();
	// } catch (IOException e) {
	// mediaPlayer = null;
	// }
	// }
	// }

	private static final long VIBRATE_DURATION = 200L;

	private void playBeepSoundAndVibrate() {
		// if (playBeep && mediaPlayer != null) {
		// mediaPlayer.start();
		// }
		if (vibrate) {
			Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
			try {
				vibrator.vibrate(VIBRATE_DURATION);
			} catch (SecurityException e) {
				LogUtil.w(e);
				if (LogUtil.DEBUG) {
					LogUtil.e(this,
							"需要添加 android:name='android.permission.CAMERA'");
				}
			}
		}
	}

	/**
	 * When the beep has finished playing, rewind to queue up another one.
	 */
	// private final OnCompletionListener beepListener = new
	// OnCompletionListener() {
	// public void onCompletion(MediaPlayer mediaPlayer) {
	// mediaPlayer.seekTo(0);
	// }
	// };
}
