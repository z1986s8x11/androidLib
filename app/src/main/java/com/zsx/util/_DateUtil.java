package com.zsx.util;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class _DateUtil {
    /**
     * 字符串转化成日期 </br> y 年 M 月 d 日 H 小时 m 分钟
     *
     * @param dateStr    如 2011-11-11
     * @param dateFormat 如 yyyy-MM-dd
     * @return 日期
     */
    @SuppressLint("SimpleDateFormat")
    public static final Date toDate(String dateStr, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date d = null;
        try {
            d = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
    }

    /**
     * 获取当月的 天数
     */
    public static int getCurrentMonthDay() {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.DAY_OF_MONTH, 1);// 设置当月第一天
        a.roll(Calendar.DAY_OF_MONTH, -1);// 天数 -1 但是月份不变
        int maxDate = a.get(Calendar.DAY_OF_MONTH);
        return maxDate;
    }

    /**
     * 根据年 月 获取对应的月份 天数
     */
    public static int getDaysByYearMonth(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DAY_OF_MONTH, 1);
        a.roll(Calendar.DAY_OF_MONTH, -1);// 天数 -1 但是月份不变
        int maxDate = a.get(Calendar.DAY_OF_MONTH);
        return maxDate;
    }
}
