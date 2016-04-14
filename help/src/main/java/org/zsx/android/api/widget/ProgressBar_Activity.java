package org.zsx.android.api.widget;

import android.os.Bundle;
import android.view.Window;
import android.widget.ProgressBar;

import org.zsx.android.api.R;
import org.zsx.android.base._BaseActivity;

public class ProgressBar_Activity extends _BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置窗口特征:启用先是进度条的进度条 必须设置在setContentView方法以前
		requestWindowFeature(Window.FEATURE_PROGRESS);
		// 设置窗口特征:启用不显示进度条的进度条
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.widget_progressbar);
		// 显示不带进度的进度条
		setProgressBarIndeterminate(true);
		// 显示带进度的进度条
		setProgressBarVisibility(true);
		// 设置进度条的进度
		setProgress(30);
		ProgressBar mProgressBar = (ProgressBar) findViewById(R.id.act_widget_current_view);
	}
}
