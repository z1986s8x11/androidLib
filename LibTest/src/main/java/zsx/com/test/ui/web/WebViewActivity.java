package zsx.com.test.ui.web;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import zsx.com.test.R;
import zsx.com.test.base._BaseActivity;

/**
 * Author       zhusx
 * Email        327270607@qq.com
 * Created      2016/3/1 14:25
 */
public class WebViewActivity extends _BaseActivity implements View.OnClickListener {
    public WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        mWebView = (WebView) findViewById(R.id.webView);
        ((TextView) findViewById(R.id.tv_right)).setText("加载");
        findViewById(R.id.tv_right).setOnClickListener(this);
        BaseWebView.init(mWebView);
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_right:
                mWebView.loadUrl("http://app.a.qu.cn/help/index");
                break;
        }
    }
}
