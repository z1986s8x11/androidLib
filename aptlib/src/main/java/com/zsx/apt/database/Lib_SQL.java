package com.zsx.apt.database;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface Lib_SQL {
    SQLFieldEnum _type();

    SQLDefaultEnum _defaultValue() default SQLDefaultEnum.DEFAULT;

    SQLKeyEnum _key() default SQLKeyEnum.DEFAULT;

    boolean _isInsert() default true;

    boolean _isCreate() default true;

    boolean _isSelect() default true;
}
