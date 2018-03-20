package com.manuege.boxfit.helpers;

import com.manuege.boxfit.transformers.Transformer;

import java.util.HashMap;

/**
 * Created by Manu on 20/3/18.
 */

public class TransformersCache {
    private static HashMap<String, Transformer> transformerHashMap = new HashMap<>();

    public static <T extends Transformer> T getTransformer(String name, Class<T> clazz) {
        try {
            if (!transformerHashMap.containsKey(name)) {
                transformerHashMap.put(name, clazz.newInstance());
            }
            return (T) transformerHashMap.get(name);

        } catch (Exception e) {
            throw new RuntimeException(String.format("%s transformer must have a public empty initializer", name));
        }
    }
}
