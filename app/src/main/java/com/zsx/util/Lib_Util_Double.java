package com.zsx.util;

import android.text.TextUtils;

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
}
