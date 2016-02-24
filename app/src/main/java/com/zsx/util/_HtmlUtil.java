package com.zsx.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author       zhusx
 * Email        327270607@qq.com
 * Created      2016/2/24.13:23
 */
public class _HtmlUtil {
    private _HtmlUtil() {
    }

    /**
     * 图片自适应
     */
    public static final String IMG_META = "img { height: auto; width: auto; width:100%;";
    /**
     * 文本自动换行
     */
    public static final String AUTO_LINE = "html{word-break: break-all; word-wrap:break-word; width:100%; height:auto;overflow:hidden;}";

    /**
     * 简单实现 为文本加Html
     */
    public static String addHtmlHead(String sourceData, String css, String javaScript, String meta) {
        StringBuffer sb = new StringBuffer();
        StringBuffer headData = new StringBuffer();
        if (meta != null) {
            headData.append(meta);
        }
        if (css != null) {
            headData.append("<style>");
            headData.append(css);
            headData.append("</style>");
        }
        if (javaScript != null) {
            headData.append("<script>");
            headData.append(javaScript);
            headData.append("</script>");
        }
        int headIndex = sourceData.indexOf("<head");
        if (headIndex == -1) {
            int htmlIndex = sourceData.indexOf("<html");
            if (htmlIndex == -1) {
                sb.append("<html>");
                sb.append("<head>");
                sb.append(headData);
                sb.append("</head>");
                int bodyIndex = sourceData.indexOf("<body");
                if (bodyIndex == -1) {
                    sb.append("<body>");
                    sb.append(sourceData);
                    sb.append("</body>");
                } else {
                    sb.append(sourceData);
                }
                sb.append("</html>");
            } else {
                sb.append(sourceData);
                int htmlFix = sourceData.indexOf(">", htmlIndex) + 1;
                sb.insert(htmlFix, headData);
            }
        } else {
            sb.append(sourceData);
            int headFix = sourceData.indexOf(">", headIndex) + 1;
            sb.insert(headFix, headData);
        }
        return sb.toString();
    }

    /**
     * 替换Html所有标签
     *
     * @param html
     * @param html
     * @param replaceStr
     * @return
     */
    public static String replaceHtmlTag(String html, String replaceStr) {
        Pattern p = Pattern.compile("<(!|/)?(.|\n)*?>");
        // 获取 matcher 对象
        Matcher m = p.matcher(html);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, "");
        }
        m.appendTail(sb);
        return sb.toString();
    }

    /**
     * 替换Html 标签中的数据
     *
     * @param html
     * @param sourceStr
     * @param replaceStr
     * @return
     */
    public static String replaceHtmlTagValue(String html, String sourceStr, String replaceStr) {
        /** 匹配>数据< */
        Pattern p = Pattern.compile(">(!|/)?(.|\n)*?<");
        Matcher m = p.matcher(html);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, m.group().replace(sourceStr, replaceStr));
        }
        m.appendTail(sb);
        return sb.toString();
    }

}
