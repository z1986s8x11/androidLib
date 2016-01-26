package com.zsx.apt.database;

public enum SQLKeyEnum {
	/** 什么都不做 */
	DEFAULT(""),
	/** 不能等于Null */
	NO_NULL("NOT NULL"),
	/** 主键 */
	PRIMARY_KEY_AUTOINCREMENT("PRIMARY KEY AUTOINCREMENT"),
	/** 不能重复 */
	UNIQUE("UNIQUE");
	private String value;
	SQLKeyEnum(String value) {
		this.value = value;
	}
	public String toString() {
		return value;
	}
}