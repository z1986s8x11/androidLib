package org.zsx.android.api.widget;

import android.os.Bundle;
import android.webkit.WebView;

import com.tools.Lib_Class_ShowCodeUtil;

import org.zsx.android.api.R;
import org.zsx.android.api._BaseActivity;

public class WebView_Activity extends _BaseActivity {
	private WebView mWebView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.widget_webview);
		mWebView = (WebView) findViewById(R.id.act_widget_current_view);
		mWebView.loadUrl("file:///android_asset/webview.html");
	}

	@Override
	public void _showCodeInit(Lib_Class_ShowCodeUtil showCode) {
		showCode.setShowFile("webview.html");
	}

}
