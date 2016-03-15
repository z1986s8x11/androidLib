package zsx.com.test.ui.web;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Message;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.GeolocationPermissions;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.PermissionRequest;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.zsx.debug.LogUtil;

/**
 * Author       zhusx
 * Email        327270607@qq.com
 * Created      2016/3/15 13:45
 */
public class Lib_WebChromeClient extends WebChromeClient {
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
}
