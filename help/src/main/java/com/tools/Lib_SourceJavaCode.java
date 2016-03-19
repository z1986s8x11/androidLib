package com.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Author       zhusx
 * Email        327270607@qq.com
 * Created      2016/3/18 17:06
 */
public class Lib_SourceJavaCode {
    public String packageName;

    public Lib_SourceJavaCode(String packageName) {
        this.packageName = packageName;
    }

    public String[] keyValue = new String[]{"package", "import", "class",
            "public", "final", "static", "extends", "private", "new",
            "protected", "return", "throws", "switch", "case"};
    public String[] keyValue2 = new String[]{"this", "super", "@Override", "void", "default"};

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
        line = lightJavaKey(line);
        return line;
    }

    private String lightJavaKey(String line) {
        if (line.startsWith("import ")) {
            String[] st = line.split("\\s+");
            if (st.length == 2) {
                if (st[1].startsWith(packageName) && st[1].length() > packageName.length() + 3) {
                    line = line
                            .replaceAll(
                                    st[1],
                                    String.format(
                                            "<font color='blue' onclick=\"clickMe(0,'%s')\">%s</font> ",
                                            st[1].substring(0,
                                                    st[1].length() - 1), st[1]));
                }
            }
        } else if (line.trim().startsWith("//")) {
            line = "<font color='Sienna'>" + line + "</font>";
        } else if (line.trim().startsWith("/*") || line.trim().startsWith("*")) {
            line = "<font color='Sienna'>" + line + "</font>";
        } else if (line.trim().startsWith("@")) {
            line = "<font color='gold'>" + line + "</font>";
        } else {
            for (String key : keyValue) {
                line = line.replaceAll(key + "\\s+",
                        String.format("<font color='orange'>%s</font> ", key, key));
            }
            for (String key : keyValue2) {
                line = line.replaceAll(key,
                        String.format("<font color='orange'>%s</font>", key, key));
            }
//            System.out
//                    .println(st.replaceAll("R\\.[a-z]+\\.[A-Za-z_0-9]+", "<></>"));
        }
        return line;
    }
}
