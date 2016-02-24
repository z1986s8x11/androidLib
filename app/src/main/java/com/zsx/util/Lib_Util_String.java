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
import java.text.DecimalFormat;
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
         * 移动：134(0-8)、135、136、137、138、139、147（预计用于TD上网卡）、150、151、152、157(TD)、158、159、187、188（TD专用）
		 * 联通：130、131、132、152、155、156（世界风专用）、185（未启用）、186（3g）
		 * 电信：133、153、180（未启用）、189、（1349卫通）
		 * 虚拟运营商：170 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
		 */
        String telRegex = "[1][34578]\\d{9}";// "[1]"代表第1位为数字1，"[3458]"代表第二位可以为3、4、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
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
     * [scheme:][//host:port][path][?query][#fragment]
     *
     * @param url 需要转换的url
     *            必须以http:// 开头 转换中文url为可以访问的url
     */
    public static String encodeUrl(String url) {
        return Lib_Util_HttpURLRequest.encodeUrl(url, null);
    }
}
