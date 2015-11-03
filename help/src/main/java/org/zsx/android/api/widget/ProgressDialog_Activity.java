package org.zsx.android.api.widget;

import java.util.Timer;
import java.util.TimerTask;

import org.zsx.android.api.R;
import org.zsx.android.api._BaseActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

@SuppressLint("HandlerLeak")
public class ProgressDialog_Activity extends _BaseActivity implements Button.OnClickListener {
	static int KEY = 0x0811;
	ProgressDialog p;
	int count = 0;
	Handler h;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.widget_progressdialog);
		Button b = (Button) findViewById(R.id.global_btn1);
		b.setOnClickListener(this);
		// setIndeterminate(booean indeterminate) 设置对话框里的进度条不显示进度值
		// setMax(int max) 设置对话框里进度条的最大值
		// setMessage(CharSequence message) 设置对话框里进度条的进度值
		// setProgress(int value) 设置对话框里的进度条的进度值
		// setProgressStyle(int style) 设置对话框里进度条的风格
		h = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if (msg.what == KEY) {
					p.setProgress(count++);
					if (count >= 100) {
						count++;
						// 关闭对话框
						p.dismiss();
						Toast.makeText(getBaseContext(), "下载完成", Toast.LENGTH_SHORT).show();
					}
				}
			}
		};
	}

	@Override
	protected Dialog onCreateDialog(int id, Bundle args) {
		switch (id) {
		case 0x0811:
			// 创建进度条对话框
			p = new ProgressDialog(this);
			p.setMax(100);
			// 设置对话框的标题
			p.setTitle("魔兽世界");
			// 设置对话框显示的内容
			p.setMessage("正在任务完成ing...");
			// 设置对话框不能用取消按钮关闭
			p.setCancelable(false);
			// 设置对话框的进度条风格 二选一
			p.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			p.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			// 设置对话框的进度条是否显示进度
			p.setIndeterminate(false);
			break;
		default:
			break;
		}
		return p;
	}

	// 该方法将在oncreateDialog方法调用之后被回调
	@SuppressWarnings("deprecation")
	@Override
	protected void onPrepareDialog(int id, Dialog dialog, Bundle args) {
		super.onPrepareDialog(id, dialog, args);
		count = 0;
		switch (id) {
		case 0x0811:
			// 对话框进度清0
			p.incrementProgressBy(-p.getProgress());
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					while (count < 100) {
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						Message m = new Message();
						m.what = KEY;
						h.sendMessage(m);
					}
				}
			}, 1000);
			break;

		default:
			break;
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) {
		showDialog(KEY);
	}
}
