package com.manuege.boxfit.annotations;

import com.manuege.boxfit.transformers.Transformer;

/**
 * Created by Manu on 24/12/17.
 */

public class IdentityTransformer<T> implements Transformer<T, T> {
    @Override
    public T transform(T object) {
        return object;
    }
}
