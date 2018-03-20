package com.manuege.boxfit.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Manu on 19/3/18.
 */

@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.FIELD)
public @interface ToJsonIncludeNull {
}
