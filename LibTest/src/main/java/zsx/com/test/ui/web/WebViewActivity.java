package zsx.com.test.ui.web;

import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Message;
import android.view.InputEvent;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.ClientCertRequest;
import android.webkit.ConsoleMessage;
import android.webkit.GeolocationPermissions;
import android.webkit.HttpAuthHandler;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.PermissionRequest;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.zsx.debug.LogUtil;

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
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                LogUtil.e("onProgressChanged:", "" + newProgress);
            }

            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                LogUtil.e("onConsoleMessage:", "" + consoleMessage.message());
                return super.onConsoleMessage(consoleMessage);
            }

            @Override
            public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
                LogUtil.e("onCreateWindow:", "isDialog" + String.valueOf(isDialog) + "isUserGesture" + String.valueOf(isUserGesture));
                return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg);
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                LogUtil.e("onJsAlert:", "message:" + message);
                return super.onJsAlert(view, url, message, result);
            }

            @Override
            public boolean onJsBeforeUnload(WebView view, String url, String message, JsResult result) {
                LogUtil.e("onJsBeforeUnload:", "message:" + message);
                return super.onJsBeforeUnload(view, url, message, result);
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                LogUtil.e("onJsConfirm:", "message:" + message);
                return super.onJsConfirm(view, url, message, result);
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                LogUtil.e("onJsPrompt:", "message:" + message);
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }

            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                LogUtil.e("onShowFileChooser:", "fileChooserParams:" + fileChooserParams.toString());
                return super.onShowFileChooser(webView, filePathCallback, fileChooserParams);
            }

            @Override
            public void onCloseWindow(WebView window) {
                super.onCloseWindow(window);
                LogUtil.e("onCloseWindow:", "");
            }

            @Override
            public void onGeolocationPermissionsHidePrompt() {
                super.onGeolocationPermissionsHidePrompt();
                LogUtil.e("onGeolocationPermissionsHidePrompt:", "");
            }

            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                super.onGeolocationPermissionsShowPrompt(origin, callback);
                LogUtil.e("onGeolocationPermissionsShowPrompt:", "origin" + origin);
            }

            @Override
            public void onHideCustomView() {
                super.onHideCustomView();
                LogUtil.e("onHideCustomView:", "");
            }

            @Override
            public void onPermissionRequest(PermissionRequest request) {
                super.onPermissionRequest(request);
                LogUtil.e("onPermissionRequest:", request.toString());
            }

            @Override
            public void onPermissionRequestCanceled(PermissionRequest request) {
                super.onPermissionRequestCanceled(request);
                LogUtil.e("onPermissionRequestCanceled:", request.toString());
            }

            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) {
                super.onReceivedIcon(view, icon);
                LogUtil.e("onReceivedIcon:", "");
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                LogUtil.e("onReceivedTitle:", title);
            }

            @Override
            public void onReceivedTouchIconUrl(WebView view, String url, boolean precomposed) {
                super.onReceivedTouchIconUrl(view, url, precomposed);
                LogUtil.e("onReceivedTouchIconUrl:", url.toString() + ":" + String.valueOf(precomposed));
            }

            @Override
            public void onRequestFocus(WebView view) {
                super.onRequestFocus(view);
                LogUtil.e("onRequestFocus:", "");
            }

            @Override
            public void onShowCustomView(View view, CustomViewCallback callback) {
                super.onShowCustomView(view, callback);
                LogUtil.e("onShowCustomView:", "");
            }
        });
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedClientCertRequest(WebView view, ClientCertRequest request) {
                super.onReceivedClientCertRequest(view, request);
                LogUtil.e("onReceivedClientCertRequest:", request.toString());
            }

            @Override
            public void onFormResubmission(WebView view, Message dontResend, Message resend) {
                super.onFormResubmission(view, dontResend, resend);
                LogUtil.e("onFormResubmission:", dontResend.toString() + ":" + resend.toString());
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
//                LogUtil.e("onLoadResource:", url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                LogUtil.e("onPageFinished:", url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                LogUtil.e("onPageStarted:", url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                LogUtil.e("onReceivedError:", description);
            }

            @Override
            public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
                super.onReceivedHttpAuthRequest(view, handler, host, realm);
                LogUtil.e("onReceivedHttpAuthRequest:", realm);
            }

            @Override
            public void onReceivedLoginRequest(WebView view, String realm, String account, String args) {
                super.onReceivedLoginRequest(view, realm, account, args);
                LogUtil.e("onReceivedLoginRequest:", realm);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                LogUtil.e("onReceivedSslError:", error.toString());
            }

            @Override
            public void onScaleChanged(WebView view, float oldScale, float newScale) {
                super.onScaleChanged(view, oldScale, newScale);
                LogUtil.e("onScaleChanged:", oldScale + ":" + newScale);
            }

            @Override
            public void onUnhandledInputEvent(WebView view, InputEvent event) {
                super.onUnhandledInputEvent(view, event);
                LogUtil.e("onUnhandledInputEvent:", event.toString());
            }

            @Override
            public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
                LogUtil.e("shouldOverrideKeyEvent:", event.toString());
                return super.shouldOverrideKeyEvent(view, event);
            }

            /**
             *  获取所有资源都会调用 (请求的http->css->javascript->图片资源 等)
             *  紧跟就是调用 onLoadResource
             */
            @Override
                public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    LogUtil.e("shouldInterceptRequest:", request.getUrl().toString());
//                } else {
//                    LogUtil.e("shouldInterceptRequest:", "");
//                }
                return super.shouldInterceptRequest(view, request);
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
