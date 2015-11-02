package com.zsx.util;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.zsx.debug.LogUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressLint("SimpleDateFormat")
public class Lib_Util_String {
    public static String toString(File file) {
        if (!file.exists()) {
            return null;
        }
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            StringBuffer sb = new StringBuffer();
            String str;
            while ((str = br.readLine()) != null) {
                sb.append(str);
                sb.append("\n");
            }
            return sb.toString();
        } catch (FileNotFoundException e) {
            LogUtil.w(e);
        } catch (IOException e) {
            LogUtil.w(e);
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Stream转换为String
     */
    public static String toString(InputStream in) {
        if (in == null) {
            return null;
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        try {
            StringBuffer sb = new StringBuffer();
            String str;
            while ((str = br.readLine()) != null) {
                sb.append(str);
                sb.append("\n");
            }
            return sb.toString();
        } catch (IOException e) {
            LogUtil.w(e);
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 转换当前byteSize大小 保留两位
     *
     * @param byteSize
     * @return
     */
    public static String toFileSize(long byteSize) {
        DecimalFormat fmt = new DecimalFormat("0.#");
        double f1 = byteSize;
        if (f1 < 1024) {
            return fmt.format(byteSize) + " byte";
        }
        f1 = f1 / 1024.0f;
        if (f1 <= 1024) {
            return fmt.format(f1) + " K";
        }
        f1 = f1 / 1024.0f;
        if (f1 <= 1024) {
            return fmt.format(f1) + " MB";
        }
        f1 = f1 / 1024.0f;
        if (f1 <= 1024) {
            return fmt.format(f1) + " G";
        }
        return fmt.format(f1) + " G";
    }

    /**
     * 转化为2位小数
     */
    public static String to2Decimals(Object doubleValue) {
        return new java.text.DecimalFormat("#.00").format(doubleValue);
    }

    /**
     * 转化为2位小数
     */
    public static double to2Decimals(double decimals) {
        return new BigDecimal(decimals).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 字符串转化成日期 </br> y 年 M 月 d 日 H 小时 m 分钟
     *
     * @param dateStr   如 2011-11-11
     * @param formatStr 如 yyyy-MM-dd
     * @return 日期
     */
    public static Date toDate(String dateStr, String formatStr) {
        SimpleDateFormat dd = new SimpleDateFormat(formatStr);
        Date date = null;
        try {
            date = dd.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 验证是否有汉字
     *
     * @param str
     * @return
     */
    public static boolean isChineseChar(CharSequence str) {
        boolean temp = false;
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            temp = true;
        }
        return temp;
    }

    /**
     * 验证是否全数字
     *
     * @param str
     * @return
     */
    public static boolean isNumber(String str) {
        Pattern p = Pattern.compile("^\\d+$");
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 判断是不是一个合法的电子邮件地址
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (email == null || email.trim().length() == 0)
            return false;
        Pattern emailer = Pattern
                .compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
        return emailer.matcher(email).matches();
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobilePhone(String mobiles) {
        /*
         * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		 * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通） 虚拟运营商
		 * 170 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
		 */
        String telRegex = "[1][3578]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles))
            return false;
        else
            return mobiles.matches(telRegex);
    }

    /**
     * 中文转 unicode
     *
     * @param noUnicodeStr 需要转换的字符或者汉字
     * @return
     */
    public static String encodeUnicode(String noUnicodeStr) {
        char[] utfBytes = noUnicodeStr.toCharArray();
        StringBuffer buffer = new StringBuffer();
        for (int byteIndex = 0; byteIndex < utfBytes.length; byteIndex++) {
            String hexB = Integer.toHexString(utfBytes[byteIndex]);
            if (hexB.length() <= 2) {
                hexB = "00" + hexB;
            }
            buffer.append("\\u" + hexB);
        }
        return buffer.substring(0);
    }

    /**
     * unicode 转换成 中文
     *
     * @param theString
     * @return
     */
    public static String decodeUnicode(String theString) {
        char aChar;
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len; ) {
            aChar = theString.charAt(x++);
            if (aChar == '\\') {
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = theString.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Malformed      encoding.");
                        }
                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't') {
                        aChar = '\t';
                    } else if (aChar == 'r') {
                        aChar = '\r';
                    } else if (aChar == 'n') {
                        aChar = '\n';
                    } else if (aChar == 'f') {
                        aChar = '\f';
                    }
                    outBuffer.append(aChar);
                }
            } else {
                outBuffer.append(aChar);
            }
        }
        return outBuffer.toString();
    }

    /**
     * @param url 需要转换的url
     *            必须以http:// 开头 转换中文url为可以访问的url
     */
    public static String encodeUrl(String url) {
        try {
            URI uri = URI.create(url);
            String host = uri.getHost();
            if (host == null) {
                return url;
            }
            if (host.length() == url.length()) {
                return url;
            }
            StringBuffer urlSb = new StringBuffer();
            urlSb.append("http://");
            urlSb.append(host);
            if (uri.getPort() != -1) {
                urlSb.append(":");
                urlSb.append(uri.getPort());
            }
            String urlPath = uri.getPath();
            String[] paths = urlPath.split("/");
            if (paths.length > 0) {
                for (int i = 0; i < paths.length; i++) {
                    urlSb.append(URLEncoder.encode(paths[i], "utf-8"));
                    if (i != paths.length - 1) {
                        urlSb.append("/");
                    }
                }
            }
            String paramArray = uri.getQuery();
            if (paramArray == null) {
                return urlSb.toString();
            }
            if (paramArray.length() == 0) {
                return urlSb.toString();
            }
            urlSb.append("?");
            String[] keyValues = paramArray.split("&");
            for (int i = 0; i < keyValues.length; i++) {
                String[] keyValue = keyValues[i].split("=");
                if (keyValue.length == 1) {
                    urlSb.append(URLEncoder.encode(keyValue[0], "utf-8"));
                    urlSb.append("=");
                } else {
                    urlSb.append(URLEncoder.encode(keyValue[0], "utf-8"));
                    urlSb.append("=");
                    urlSb.append(URLEncoder.encode(keyValue[1], "utf-8"));
                }
                if (i != keyValues.length - 1) {
                    urlSb.append("&");
                }
            }
            return urlSb.toString();
        } catch (Exception e) {
            if (LogUtil.DEBUG) {
                LogUtil.e(e);
            }
        }
        return url;
    }

    /**
     * 图片自适应
     */
    public static final String imgMeta = "img { height: auto; width: auto; width:100%;";
    /**
     * 文本自动换行
     */
    public static final String autoLine = "html{word-break: break-all; word-wrap:break-word; width:100%; height:auto;overflow:hidden;}";

    /**
     * 简单实现 为文本加Html
     */
    public static String addHtmlHead(String sourceData, String css,
                                     String javaScript, String meta) {
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
    public static String replaceHtmlTagValue(String html, String sourceStr,
                                             String replaceStr) {
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
