package com.manuege.boxfit.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * By default, if a json value is `null` when you try to build the instance the value is set to null.
 * If you want to ignore `null values in the json, you must annotate the field with `@FromJsonIgnoreNull`.
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.FIELD)
public @interface FromJsonIgnoreNull {
}
