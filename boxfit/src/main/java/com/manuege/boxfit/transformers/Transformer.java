package com.manuege.boxfit.transformers;

/**
 * Created by Manu on 24/12/17.
 */

/**
 * A interface that take a value of one class anf transform it into a value of another class
 * @param <O> The class of the original value
 * @param <T> The class of the transformed value
 */
public interface Transformer<O, T> {
    /**
     * Take tha value of a class `O` and transforms it into a object of the class `T`
     * @param object the object to be transformed
     * @return The transformed object
     */
    T transform(O object);

    /**
     * Take tha value of a class `T` and transforms it into a object of the class `O`
     * @param object the object to be transformed
     * @return The transformed object
     */
    O inverseTransform(T object);
}
