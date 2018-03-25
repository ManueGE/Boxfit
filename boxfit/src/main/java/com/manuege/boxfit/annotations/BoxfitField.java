package com.manuege.boxfit.annotations;

import com.manuege.boxfit.constants.Constants;
import com.manuege.boxfit.transformers.Transformer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Manu on 24/12/17.
 */

@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.FIELD)
public @interface BoxfitField {
    String value() default Constants.SERIALIZABLE_NULL_KEY_PATH;
    Class<? extends Transformer> transformer() default IdentityTransformer.class;
}
