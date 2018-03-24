package com.manuege.boxfitapp.transformers;

import com.manuege.boxfit.transformers.Transformer;
import com.manuege.boxfitapp.model.Parent;

import io.objectbox.converter.PropertyConverter;

/**
 * Created by Manu on 24/3/18.
 */

public class EnumToIntTransformer implements PropertyConverter<Parent.Enum, Integer>, Transformer<Parent.Enum, Integer> {
    @Override
    public Parent.Enum transform(Integer object) {
        for (Parent.Enum type: Parent.Enum.values()) {
            if (type.getValue().equals(object)) {
                return type;
            }
        }
        return Parent.Enum.NONE;
    }

    @Override
    public Integer inverseTransform(Parent.Enum object) {
        if (object == null) {
            return Parent.Enum.NONE.getValue();
        }
        return object.getValue();
    }

    @Override
    public Parent.Enum convertToEntityProperty(Integer databaseValue) {
        return transform(databaseValue);
    }

    @Override
    public Integer convertToDatabaseValue(Parent.Enum entityProperty) {
        return inverseTransform(entityProperty);
    }
}
