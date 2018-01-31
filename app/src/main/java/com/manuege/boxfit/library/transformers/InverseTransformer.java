package com.manuege.boxfit.library.transformers;

/**
 * Created by Manu on 24/12/17.
 */

/**
 * A interface that take a value of one class anf transform it into a value of another class.
 * Can also make the inverse transform to get an value of type `O` from an object of class `T`
 * @param <O> The class of the original value
 * @param <T> The class of the transformed value
 */
public interface InverseTransformer<O, T> extends Transformer<O, T> {
    /**
     * Take tha value of a class `T` and transforms it into a object of the class `O`
     * @param object the object to be transformed
     * @return The transformed object
     */
    O inverseTransform(T object);
}
