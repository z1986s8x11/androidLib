package com.zsx.util;

import android.text.TextUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by Administrator on 2015/12/8.
 */
public class Lib_Util_Double {

    public static double toDouble(String doubleString) {
        if (TextUtils.isEmpty(doubleString)) {
            return 0d;
        }
        return Double.parseDouble(doubleString);
    }

    /**
     * 转化为2位小数
     */
    public static double to2Decimals(double decimals) {
        return new BigDecimal(decimals).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 格式化为固定2位小数
     */
    public static String toFix2Decimals(double doubleValue) {
        return new java.text.DecimalFormat("0.00").format(doubleValue);
    }

    /**
     * 格式化为固定2位小数
     */
    public static String toFix2Decimals(String doubleValue) {
        return toFix2Decimals(toDouble(doubleValue));
    }

    /**
     * 转化为最多2位小数
     */
    public static String to2DecimalsForMaximum(String doubleValue) {
        if (TextUtils.isEmpty(doubleValue)) {
            return null;
        }
        BigDecimal bd = new BigDecimal(doubleValue);
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        return to2DecimalsForMaximum(bd.doubleValue());
    }

    /**
     * 转化为最多2位小数
     */
    public static String to2DecimalsForMaximum(double doubleValue) {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(doubleValue);
    }
}
