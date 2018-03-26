package com.manuege.boxfit.annotations;

import com.manuege.boxfit.transformers.JSONObjectTransformer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to tell the compiler that the class is able to be converted from/to JSON
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface BoxfitClass {
    /**
     * A `Transformer` that will transform the JSONObject before being converted into an object.
     * It also will be applied to the JSONObject resultant from serializing an object.
     */
    Class<? extends JSONObjectTransformer> transformer() default JSONObjectIdentityTransformer.class;
}
