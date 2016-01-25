package com.zsx.database;

import java.lang.reflect.InvocationTargetException;

/**
 * boolean 有三种状态, 0(false) 1(true) 和 null
 * 
 * @author pt-zsx
 * 
 * 
 */
public class Lib_SQLManager {
	/** 建表 */
	public static final String CreateTableKey = "CREATE TABLE IF NOT EXISTS ";
	/** 主键 */
	public static final String PrimaryKeyAutoKey = " PRIMARY KEY AUTOINCREMENT ";
	/** 字段外键关联表的某字段 级联删除 */
	public static final String ForeignKeyOnDeleteKey = " foreign key (?) REFERENCES ?(?) ON DELETE CASCADE ";
	/** 不重复 */
	public static final String UniqueKey = " UNIQUE ";
	/** 必须 DATETIME 类型 当前时间 */
	public static final String CurrentTimeKey = " (datetime(CURRENT_TIMESTAMP,'localtime'))";
	/** 字符串 */
	public static final String TextKey = " TEXT ";
	/** 整数 0 - 4294967295 */
	public static final String IntegerKey = " INTEGER ";
	/** 长整形 0 -9223372036854775807 */
	public static final String LongKey = " BIGINT ";
	/** 浮点数 */
	public static final String FloatKey = " REAL ";
	/** 日期类型 */
	public static final String DateKey = " DATETIME ";
	/** 联合主键 */
	public static final String PrimaryKey = " PRIMARY KEY(?,?) ";

	public static String createSQL(Class<?> obj) {
		String st = null;
		try {
			Class<?> cls = Class.forName(obj.getName() + "Entity");
			Object o = cls.newInstance();
			st = (String) cls.getDeclaredMethod("createSQL", (Class<?>[]) null).invoke(o, (Object[]) null);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return st;
	}

	public static String inserSQL(Object obj) {
		String st = null;
		try {
			Class<?> cls = Class.forName(obj.getClass().getName() + "Entity");
			Object o = cls.newInstance();
			st = (String) cls.getDeclaredMethod("inserSQL", (Class<?>[]) null).invoke(o, (Object[]) null);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return st;
	}

	/**
	 * 当前日期的前后多少天日期
	 */
	public static final String getDayDateKey(int dayCount) {
		if (dayCount > 0) {
			return " date('now','start of day','+? days') ";
		} else {
			return " date('now','start of day','-? days') ";
		}
	}

	/**
	 * @return 当前月份的第一天
	 */
	public static final String getFirstDayFromMonth() {
		return "date('now','start of month')";
	}

}
