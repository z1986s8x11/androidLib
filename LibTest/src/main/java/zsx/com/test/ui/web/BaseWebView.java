package zsx.com.test.ui.web;

import android.view.Gravity;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zsx.debug.LogUtil;

/**
 * Author       zhusx
 * Email        327270607@qq.com
 * Created      2016/3/14 15:56
 */
public class BaseWebView {
    public static void init(final WebView mWebView) {
        LogUtil.DEBUG = true;
        mWebView.getSettings().setJavaScriptEnabled(true);
        final TextView textView = new TextView(mWebView.getContext());
        ViewParent parent = mWebView.getParent();
        if (parent != null) {
            ViewGroup.LayoutParams lp = mWebView.getLayoutParams();
            LinearLayout parentLayout = new LinearLayout(mWebView.getContext());
            parentLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            ViewGroup group = (ViewGroup) parent;
            int index = group.indexOfChild(mWebView);
            group.removeView(mWebView);
            group.addView(parentLayout, index, lp);
            textView.setGravity(Gravity.CENTER);
            parentLayout.addView(textView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
            parentLayout.addView(mWebView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        }
        mWebView.setWebChromeClient(new Lib_WebChromeClient() {

        });
        mWebView.setWebViewClient(new Lib_WebViewClient() {

        });
    }
}
