package com.zsx.debug;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.zsx.app.Lib_BaseFragment;
import com.zsx.tools.Lib_Subscribes;
import com.zsx.util.Lib_Util_System;

import java.util.List;

/**
 * zsxTitle      日志查看
 * Author        zhusx
 * Email         327270607@qq.com
 * Created       2016/5/26 9:53
 */
public class P_LogCatFragment extends Lib_BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        LinearLayout rootView = new LinearLayout(inflater.getContext());
        rootView.setBackgroundColor(Color.WHITE);
        rootView.setOrientation(LinearLayout.HORIZONTAL);
        final WebView mWebView = new WebView(inflater.getContext());
        Lib_Subscribes.subscribe(new Lib_Subscribes.Subscriber<String>() {
            @Override
            public String doInBackground() {
                List<String> list = Lib_Util_System.getLogCatForLogUtil();
                StringBuffer sb = new StringBuffer();
                sb.append("<html>");
                sb.append("<head>");
                sb.append("</head>");
                sb.append("<body>");
                for (int i = 0; i < list.size(); i++) {
                    sb.append(list.get(i).replaceAll("&", "&amp;").replaceAll("\"", "&quot;").replaceAll("<", "&lt;").replaceAll(">", "&gt;"));
                    sb.append("</br>");
                    sb.append("</br>");
                }
                sb.append("</body>");
                sb.append("</html>");
                return sb.toString();
            }

            @Override
            public void onComplete(String data) {
                if (getActivity() != null) {
                    mWebView.loadDataWithBaseURL(null, data, "text/html", "UTF-8", null);
                }
            }
        }, this);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            mWebView.getSettings().setDisplayZoomControls(true);
        }
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.getSettings().setUseWideViewPort(true);
        rootView.addView(mWebView);
        return rootView;
    }
}
