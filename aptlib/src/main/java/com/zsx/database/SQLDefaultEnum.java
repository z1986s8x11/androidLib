package com.zsx.database;

public enum SQLDefaultEnum {
	/** 什么都不做 */
	DEFAULT(""),
	/** 默认为空 */
	NULL("NULL"),
	/** 默认当前日期 */
	// CURRENT_DATE("(datetime(CURRENT_TIMESTAMP,'localtime'))"),
	/** 默认为true */
	TRUE("TRUE"),
	/** 默认为false */
	FALSE("FALSE"),
	/** -1 */
	NegativeOne("-1");
	private String value;
	SQLDefaultEnum(String value) {
		this.value = value;
	}
	public String toString() {
		return value;
	}
}