package com.tools;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.webkit.WebView;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author zsx
 * @date 2013-12-27 11:03:26
 * @description 需要在AndroidMainifest.xml 注册
 * {@link com.tools.Lib_Class_ShowCodeResultActivity}
 */
public class Lib_Class_ShowCodeResultActivity extends Activity {
    public static final String RM_EXTRA_SHOW_CODE_FILE_KEY = "SHOW_CODE_FILE_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebView mWebView = new WebView(this);
        setContentView(mWebView);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        String fileName = getIntent().getStringExtra(RM_EXTRA_SHOW_CODE_FILE_KEY);
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
    }

    protected String toLine(String line) throws UnsupportedEncodingException {
        StringBuffer sb = new StringBuffer();
        line = line
                .replaceAll("&", "&amp;")
                        // .replaceAll("\t", "&nbsp;")
                        // .replaceAll(" ", "&nbsp;")
                .replaceAll("<", "&lt;").replaceAll(">", "&gt;")
                .replaceAll("\'", "&qpos;").replaceAll("\"", "&quot;");
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
        return line.replaceAll(replaceText + "\\s+", "<font color='" + color
                + "' onclick=\"clickMe('" + replaceText + "')\">" + replaceText
                + "</font> ");
    }

}