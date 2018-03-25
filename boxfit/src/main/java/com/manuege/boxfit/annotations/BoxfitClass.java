package com.manuege.boxfit.annotations;

import com.manuege.boxfit.transformers.JSONObjectTransformer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface BoxfitClass {
    Class<? extends JSONObjectTransformer> transformer() default JSONObjectIdentityTransformer.class;
}
