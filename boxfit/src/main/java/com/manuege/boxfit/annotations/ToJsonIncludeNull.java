package com.manuege.boxfit.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * By default, if a property is `null` when you try to get a JSON, the key for the field won't be included in the JSON.
 * If you want to include the key even if the value is `null` you must annotate the field with `@ToJsonIncludeNull`.
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.FIELD)
public @interface ToJsonIncludeNull {
}
