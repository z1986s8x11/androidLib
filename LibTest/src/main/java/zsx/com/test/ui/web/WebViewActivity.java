package zsx.com.test.ui.web;

import android.os.Bundle;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.zsx.tools.Lib_WebViewHelper;

import zsx.com.test.R;
import zsx.com.test.base._BaseActivity;

/**
 * Author       zhusx
 * Email        327270607@qq.com
 * Created      2016/3/1 14:25
 */
public class WebViewActivity extends _BaseActivity implements View.OnClickListener {
    public WebView mWebView;
    Lib_WebViewHelper webViewHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        mWebView = (WebView) findViewById(R.id.webView);
        ((TextView) findViewById(R.id.tv_right)).setText("加载");
        findViewById(R.id.tv_right).setOnClickListener(this);
//        BaseWebView.init(mWebView);
        webViewHelper = new Lib_WebViewHelper(mWebView);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//                super.onReceivedError(view, errorCode, description, failingUrl);
                webViewHelper.onReceivedError(view, errorCode, description, failingUrl);
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                _showToast(url);
                return super.onJsAlert(view, url, message, result);
            }
        });
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
