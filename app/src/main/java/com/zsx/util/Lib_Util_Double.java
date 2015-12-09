package com.zsx.util;

import android.text.TextUtils;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2015/12/8.
 */
public class Lib_Util_Double {

    public static double parseDouble(String doubleString) {
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
}
