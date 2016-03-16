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
        return mWebView;
    }

    private void initData(final String fileName) {
        Lib_Subscribes.subscribe(new Lib_Subscribes.Subscriber<String>() {
            @Override
            public String doInBackground() {
                ZipFile mZipFile = null;
                BufferedReader br = null;
                try {
                    mZipFile = new ZipFile(new File(Environment.getExternalStorageDirectory(), "help.zip"));
                    StringBuffer sb = new StringBuffer();
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
                    mWebView.loadDataWithBaseURL(null, sb.toString(), "html/text", "UTF-8", null);
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
            public void onComplete(String o) {
            }
        }, this);
    }

    protected String toLine(String line) throws UnsupportedEncodingException {
        if (TextUtils.isEmpty(line)) {
            return "";
        }
        line = line.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
                .replaceAll(">", "&gt;").replaceAll("\'", "&qpos;")
                .replaceAll("\"", "&quot;");
        if (line.startsWith("import")) {
            String[] st = line.split(" ");
            line = addOnClick(line, st[1], "blue");
        }
        StringBuffer sb = new StringBuffer();
        line = addTextColor(line, "package", "red");
        line = addTextColor(line, "import", "red");
        line = addTextColor(line, "public", "red");
        line = addTextColor(line, "protected", "red");
        line = addTextColor(line, "private", "red");
        line = addTextColor(line, "static", "red");
        line = addTextColor(line, "final", "red");
        line = addTextColor(line, "extends", "red");
        line = addTextColor(line, "class", "red");
        line = addTextColor(line, "void", "red");
        sb.append(line);
        sb.append("</br>");
        return sb.toString();
    }

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
