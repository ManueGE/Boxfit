package com.manuege.boxfit.annotations;

import com.manuege.boxfit.constants.Constants;
import com.manuege.boxfit.transformers.Transformer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation to tell the compiler that a filed must be took in account when performing a JSON serialization.
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.FIELD)
public @interface BoxfitField {
    /**
     * The key where the field is a JSONObject. It allows dot notation to get a value from nested JSONObjects
     */
    String value() default Constants.SERIALIZABLE_NULL_KEY_PATH;

    /**
     * A transformer to process the value from the JSONObject.
     */
    Class<? extends Transformer> transformer() default IdentityTransformer.class;
}
