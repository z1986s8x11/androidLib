package org.zsx.android.api.widget;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.zsx.android.api.R;
import org.zsx.android.base._BaseActivity;

public class WebView_Activity extends _BaseActivity {
    private WebView mWebView;

    /**
     * document.location = 'm://qu.cn';
     * 与
     * <a href='m://qu.cn/'>点击</a>
     *
     * 获取参数的方式
     * Uri uri = getIntent().getData();
     * String test1= uri.getQueryParameter("arg0");
     * String test2= uri.getQueryParameter("arg1");
     * 效果一样
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.widget_webview);
        mWebView = (WebView) findViewById(R.id.act_widget_current_view);
        mWebView.loadUrl("file:///android_asset/webview.html");

        //getScrollY()        方法返回的是当前可见区域的顶端距整个页面顶端的距离,也就是当前内容滚动的距离.
        //getHeight()或者getBottom()方法都返回当前WebView 这个容器的高度
        //getContentHeight 返回的是整个html 的高度,但并不等同于当前整个页面的高度,因为WebView 有缩放功能, 所以当前整个页面的高度实际上应该是原始html 的高度再乘上缩放比例. 因此,更正后的结果,准确的判断方法应该是：
        if (mWebView.getContentHeight() * mWebView.getScale() == (mWebView.getHeight() + mWebView.getScrollY())) {
            //已经处于底端
        }
        //屏蔽掉长按事件 因为webview长按时将会调用系统的复制控件
        mWebView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        //WebView保留缩放功能但隐藏缩放控件
        if (true) {
            mWebView.getSettings().setSupportZoom(true);
            mWebView.getSettings().setBuiltInZoomControls(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                mWebView.getSettings().setDisplayZoomControls(false);
            }
        }
        if (true) {
            //
            mWebView.getSettings().setLoadsImagesAutomatically(false);
            //
            mWebView.getSettings().setBlockNetworkImage(false);
        }

        mWebView.setWebViewClient(new WebViewClient() {
            //WebView 在Android4.4的手机上onPageFinished()回调会多调用一次(具体原因待追查)
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient() {
        });
        /**
         * LOAD_DEFAULT: 如果我们应用程序没有设置任何cachemode， 这个是默认的cache方式。 加载一张网页会检查是否有cache，如果有并且没有过期则使用本地cache，否则从网络上获取。
         * LOAD_CACHE_ELSE_NETWORK: 使用cache资源，即使过期了也使用，如果没有cache才从网络上获取。
         * LOAD_NO_CACHE: 不使用cache 全部从网络上获取
         * LOAD_CACHE_ONLY:  只使用cache上的内容
         */
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //.WebView页面中播放了音频,退出Activity后音频仍然在播放,需要在Activity的onDestory()中调用
        mWebView.stopLoading();
        mWebView.removeAllViews();
        mWebView.destroy();
    }

    private void clear() {
        mWebView.clearFormData();
        //清理cache
        mWebView.clearCache(true);
        //历史记录
        mWebView.clearHistory();
    }

    public void clearAllCache() {
        CookieSyncManager.createInstance(this);
        CookieSyncManager.getInstance().startSync();
        CookieManager.getInstance().removeSessionCookie();
    }
}
