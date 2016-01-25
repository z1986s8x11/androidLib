package com.zsx.database;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target({ ElementType.FIELD, ElementType.TYPE })
public @interface Lib_SQL {
	public SQLFieldEnum _type();

	public SQLDefaultEnum _defaultValue() default SQLDefaultEnum.DEFAULT;

	public SQLKeyEnum _key() default SQLKeyEnum.DEFAULT;

	public boolean _isInsert() default true;

	public boolean _isCreate() default true;

	public boolean _isSelect() default true;
}
