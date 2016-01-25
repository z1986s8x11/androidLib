package com.zsx.database;

public enum SQLFieldEnum {
	/** 整形 */
	Integer("INTEGER"),
	/** 字符串 */
	String("TEXT"),
	/** 布尔类型 */
	Boolean("BOOLEAN"),
	/** 包含了 年、月、日、时、分、秒、千分之一秒 */
	// DateTime("DATETIME"),
	/** 包含了 年份、月份、日期 */
	// Date("DATE"),
	/** 包含了 小时、分钟、秒 */
	// Time("TIME"),
	/** 长整形 */
	Long("BIGINT"),
	/** 浮点类型 */
	Float("REAL");
	private String value;
	SQLFieldEnum(String value) {
		this.value = value;
	}
	public String toString() {
		return value;
	}
}
