package org.zsx.android.api.widget;

import java.util.Locale;

import org.zsx.android.api.R;
import org.zsx.android.base._BaseActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class TextToSpeech_Activity extends _BaseActivity implements Button.OnClickListener {
	TextToSpeech mTextToSpeech;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.widget_texttospeech);
		Button speech = (Button) findViewById(R.id.global_btn1);
		speech.setOnClickListener(this);
		mTextToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {

			@Override
			public void onInit(int status) {
				// 如果装载TTS引擎成功
				if (status == TextToSpeech.SUCCESS) {
					int result = mTextToSpeech.setLanguage(Locale.US);
					// 如果不支持所设置的语言
					if (result != TextToSpeech.LANG_COUNTRY_AVAILABLE && result != TextToSpeech.LANG_AVAILABLE) {
						Toast.makeText(TextToSpeech_Activity.this, "TTS暂时不支持这种语言的朗读", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		// TextToSpeech.QUEUE_FLUSH 指定该模式时,TTS调用speak方法,它会中断当前语音任务
		// TextToSpeech.QUEUE_ADD 指定该模式时,TTS调用speak方法
		// ,它会把新的发音任务添加到当前发音任务之后
		String t = "My name is God";
		mTextToSpeech.speak(t, TextToSpeech.QUEUE_ADD, null);
		// 保存声音
		// mTextToSpeech.synthesizeToFile(t, null,"/mnt/sdcard/zsx.wav");
	}
}
