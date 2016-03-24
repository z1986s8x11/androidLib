package com.zsx.debug;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Author       zhusx
 * Email        327270607@qq.com
 * Created      2016/3/18 17:06
 */
public class P_SourceXmlCode {
    public String packageName;

    public P_SourceXmlCode(String packageName) {
        this.packageName = packageName;
    }

    public String _toHtml(BufferedReader br) throws IOException {
        StringBuffer sb = new StringBuffer();
        sb.append("<html>");
        sb.append("<head>");
        sb.append("<meta http-equiv=\"content-type\" content=\"text/html;charset=utf-8\">");
        sb.append("<script>");
        sb.append("function clickMe(type,name){window.zhusx.goReadFile(type,name);}");
        sb.append("</script>");
        sb.append("</head>");
        sb.append("<body>");
        sb.append("<pre>");
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(toXmlCode(line));
            sb.append("</br>");
        }
        sb.append("</pre>");
        sb.append("</body>");
        sb.append("</html>");
        return sb.toString();
    }

    private String toXmlCode(String line) throws UnsupportedEncodingException {
        line = line.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
                .replaceAll(">", "&gt;").replaceAll("\'", "&qpos;")
                .replaceAll("\"", "&quot;");
        line = lightXmlKey(line);
        return line;
    }

    private String lightXmlKey(String line) {
        String st = line.trim();
        if (st.startsWith("&lt;")) {
            if (!st.contains(".")) {
                line = String.format("<font color='orange'><B>%s</B></font>", line);
            } else if (st.contains("=")) {
                line = String.format("<font color='orange'><B>%s</B></font>", line);
            } else {
                line = String.format("<font color='orange' onclick=\"clickMe('java','%s')\"><B>%s</B></font>", st.substring(1, st.length() - 1), line);
            }
        } else if (!st.contains("=")) {
            String[] sts = line.split("=");
            if (sts.length == 2) {
                line = String.format("%s=<font color='blue'>%s</font>", sts[0], sts[1]);
            }
        }
        return line;
    }
}
