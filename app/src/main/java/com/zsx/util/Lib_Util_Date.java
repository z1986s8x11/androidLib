package com.zsx.util;

import android.annotation.SuppressLint;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Lib_Util_Date {
	/**
	 * 字符串转化为时间 yyyy-MM-dd HHmmss
	 * */
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
