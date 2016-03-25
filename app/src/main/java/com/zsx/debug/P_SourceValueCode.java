package com.zsx.debug;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Author       zhusx
 * Email        327270607@qq.com
 * Created      2016/3/25 14:00
 */
public class P_SourceValueCode {
    public String packageName;

    public P_SourceValueCode(String packageName) {
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
            sb.append(toJavaCode(line));
            sb.append("</br>");
        }
        sb.append("</pre>");
        sb.append("</body>");
        sb.append("</html>");
        return sb.toString();
    }

    private String toJavaCode(String line) throws UnsupportedEncodingException {
        line = line.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
                .replaceAll(">", "&gt;").replaceAll("\'", "&qpos;")
                .replaceAll("\"", "&quot;");
        return line;
    }
}
