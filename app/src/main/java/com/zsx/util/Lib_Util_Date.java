package com.zsx.util;

import android.annotation.SuppressLint;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Lib_Util_Date {
	/**
	 * 字符串转化成日期 </br> y 年 M 月 d 日 H 小时 m 分钟
	 *
	 * @param dateStr   如 2011-11-11
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
}
