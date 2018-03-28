package com.manuege.boxfit.annotations;

import com.manuege.boxfit.transformers.Transformer;

/**
 * A transformer that returns the same object.
 */
public class IdentityTransformer<T> implements Transformer<T, T> {
    @Override
    public T transform(T object) {
        return object;
    }

    @Override
    public T inverseTransform(T object) {
        return object;
    }
}
