package zsx.com.test.ui.web;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Message;
import android.view.InputEvent;
import android.view.KeyEvent;
import android.webkit.ClientCertRequest;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zsx.debug.LogUtil;

/**
 * Author       zhusx
 * Email        327270607@qq.com
 * Created      2016/3/15 13:46
 */
public class Lib_WebViewClient extends WebViewClient {

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
        LogUtil.e("onLoadResource:", url);
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
     * 获取所有资源都会调用 (请求的http->css->javascript->图片资源 等)
     * 紧跟就是调用 onLoadResource
     */
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    LogUtil.e("shouldInterceptRequest:", request.getUrl().toString());
//                } else {
        LogUtil.e("shouldInterceptRequest:", "");
//                }
        return super.shouldInterceptRequest(view, request);
    }
}
