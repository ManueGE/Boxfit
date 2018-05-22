package com.manuege.boxfit.annotations;

/**
 * By default, if a json value is `null` when you try to build the instance the value is set to null.
 * If you want to ignore `null values in the json, you must annotate the field with `@FromJsonIgnoreNull`.
 */
public @interface FromJsonIgnoreNull {
}
