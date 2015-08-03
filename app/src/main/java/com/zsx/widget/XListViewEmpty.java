package com.zsx.widget;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class XListViewEmpty extends LinearLayout {
	private ProgressBar barPB;
	private TextView messageTV;
	private final String errorMessage = "加载失败,点击重新加载";
	private final String initMessage = "点击重新加载";
	private final String loadingMessage = "正在加载...";
	private final String noData = "亲~这里没有数据";

	public XListViewEmpty(Context context) {
		super(context);
		setGravity(Gravity.CENTER);
		setOrientation(LinearLayout.HORIZONTAL);
		barPB = new ProgressBar(context, null,
				android.R.attr.progressBarStyleSmall);
		messageTV = new TextView(context);
		addView(barPB);
		addView(messageTV);
		_setDefault();
	}

	public void _setNoData() {
		setEnabled(false);
		barPB.setVisibility(View.GONE);
		messageTV.setText(noData);
	}

	public void _setDefault() {
		setEnabled(true);
		barPB.setVisibility(View.GONE);
		messageTV.setText(initMessage);
	}

	public void _setLoading() {
		setEnabled(false);
		barPB.setVisibility(View.VISIBLE);
		messageTV.setText(loadingMessage);
	}

	public void _setError() {
		setEnabled(true);
		barPB.setVisibility(View.GONE);
		messageTV.setText(errorMessage);
	}
}
