package com.tools;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.zsx.app.Lib_BaseFragment;
import com.zsx.tools.Lib_Subscribes;
import com.zsx.widget.slidingmenu.SlidingMenu;

import org.zsx.android.api.R;
import org.zsx.android.api._BaseActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
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
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        String fileName = getArguments().getString(_EXTRA_String);
        initData(fileName);
        return mWebView;
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

    private void initData(final String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            _showToast("文件路径有问题");
            getActivity().finish();
            return;
        }
        Lib_Subscribes.subscribe(new Lib_Subscribes.Subscriber<String>() {
            @Override
            public String doInBackground() {
                ZipFile mZipFile = null;
                BufferedReader br = null;
                StringBuffer sb = new StringBuffer();
                try {
                    mZipFile = new ZipFile(__getZipFile());
                    ZipEntry entry = mZipFile.getEntry(fileName);
                    br = new BufferedReader(new InputStreamReader(
                            mZipFile.getInputStream(entry), "utf8"));
                    String line;
                    sb.append("<html>");
                    sb.append("<head>");
                    sb.append("<meta http-equiv=\"content-type\" content=\"text/html;charset=utf-8\">");
                    sb.append("<script>");
                    sb.append("function clickMe(name){alert(name);}");
                    sb.append("</script>");
                    sb.append("</head>");
                    sb.append("<body>");
                    sb.append("<pre>");
                    while ((line = br.readLine()) != null) {
                        sb.append(toLine(line));
                    }
                    sb.append("</pre>");
                    sb.append("</body>");
                    sb.append("</html>");
                    return sb.toString();
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

    protected File __getZipFile() {
        return new File(Environment.getExternalStorageDirectory(), "help.zip");
    }

    protected String toLine(String line) throws UnsupportedEncodingException {
        StringBuffer sb = new StringBuffer();
        if (!TextUtils.isEmpty(line)) {
            line = line.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
                    .replaceAll(">", "&gt;").replaceAll("\'", "&qpos;")
                    .replaceAll("\"", "&quot;");
            sb.append(line);
        }
        sb.append("</br>");
        return sb.toString();
    }
    public String[] keyValue=new String[]{"package","import","class","public","final","static","extends","private","new","protected","return","throws"};
    protected String addTextColor(String line, String replaceText, String color) {
        return line.replaceAll(replaceText, "<font color='" + color + "' "
                + replaceText + "</font> ");
    }

    protected String addOnClick(String line, String replaceText, String color) {
        return line.replaceAll(replaceText, "<font color='" + color
                + "' onclick=\"clickMe('" + replaceText + "')\">" + replaceText
                + "</font> ");
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
}
