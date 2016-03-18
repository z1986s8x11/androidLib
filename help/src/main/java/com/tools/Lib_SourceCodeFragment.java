package com.tools;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.zsx.app.Lib_BaseFragment;
import com.zsx.tools.Lib_Subscribes;
import com.zsx.util.Lib_Util_File;
import com.zsx.widget.slidingmenu.SlidingMenu;

import org.zsx.android.api.R;
import org.zsx.android.api._BaseActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Author       zhusx
 * Email        327270607@qq.com
 * Created      2016/3/16 17:28
 */
public class Lib_SourceCodeFragment extends Lib_BaseFragment {
    private WebView mWebView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mWebView = new WebView(inflater.getContext());
        initWebView(mWebView);
        String fileName = getArguments().getString(_EXTRA_String);
        initData(fileName);
        return mWebView;
    }

    private void initWebView(WebView mWebView) {
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }
        });
        mWebView.addJavascriptInterface(this, "zhusx");
    }

    @JavascriptInterface
    public void goReadFile(int type, String className) {
        switch (type) {
            case 0:
                //java
                initData("java/" + className.replace(".", "/") + ".java");
                break;

        }
    }

    public static void initContextView(_BaseActivity activity) {
        SlidingMenu mSlidingMenu = new SlidingMenu(activity, SlidingMenu.SLIDING_CONTENT);
        final View right = LayoutInflater.from(activity).inflate(R.layout.lib_layout_linearlayout, null, false);
        mSlidingMenu.setMenu(right);
        mSlidingMenu.setBehindWidth(activity._getFullScreenWidth() - 200);
        final String fileName = "java/" + activity.getClass().getName().replace(".", "/") + ".java";
        Lib_SourceCodeFragment fragment = new Lib_SourceCodeFragment();
        Bundle b = new Bundle();
        b.putString(_EXTRA_String, fileName);
        fragment.setArguments(b);
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.lib_content, fragment).commitAllowingStateLoss();
    }

    public String assetsName = "source.zip";

    private void initData(final String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            _showToast("文件路径有问题");
            getActivity().finish();
            return;
        }
        final String packageName = getActivity().getPackageName();
        Lib_Subscribes.subscribe(new Lib_Subscribes.Subscriber<String>() {
            @Override
            public String doInBackground() {
                File file = new File(getActivity().getExternalCacheDir(), assetsName);
                if (!file.exists()) {
                    Lib_Util_File.copyAssetToFile(getActivity(), assetsName, file);
                }
                ZipFile mZipFile = null;
                BufferedReader br = null;
                String html = "";
                try {
                    mZipFile = new ZipFile(new File(getActivity().getExternalCacheDir(), assetsName));
                    ZipEntry entry = mZipFile.getEntry(fileName);
                    if (entry != null) {
                        br = new BufferedReader(new InputStreamReader(mZipFile.getInputStream(entry), "UTF-8"));
                        switch ("java") {
                            case "java":
                                html = new Lib_SourceJavaCode(packageName)._toHtml(br);
                                break;
                        }
                    }
                    return html;
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (br != null) {
                        try {
                            br.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                    if (mZipFile != null) {
                        try {
                            mZipFile.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return null;
            }

            @Override
            public void onComplete(String html) {
                if (TextUtils.isEmpty(html)) {
                    _showToast("文件有问题.打不开");
                    getActivity().finish();
                    return;
                }
                mWebView.loadDataWithBaseURL(null, html, "html/text", "UTF-8", null);
            }
        }, this);
    }


    private String getResName(Context context, int resourceID) {
        String fileName = "res/" + context.getResources().getResourceTypeName(resourceID) + File.separator
                + context.getResources().getResourceEntryName(resourceID) + ".xml";
        return fileName;
    }

    private String getJavaName(Class<?> cls) {
        String fileName = "java/" + cls.getName().replace(".", "/") + ".java";
        return fileName;
    }

    public boolean _canGoBack() {
        return mWebView.canGoBack();
    }

    public void _goBack() {
        mWebView.goBack();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mWebView.clearHistory();
        mWebView.clearFormData();
        mWebView.clearCache(true);
    }
}
