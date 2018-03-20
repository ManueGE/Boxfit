package com.manuege.boxfitapp.transformers;

import com.manuege.boxfit.transformers.Transformer;
import com.manuege.boxfitapp.model.Artist;

import io.objectbox.converter.PropertyConverter;

/**
 * Created by Manu on 1/2/18.
 */

public class ArtistTypeTransformer implements PropertyConverter<Artist.Type, String>, Transformer<Artist.Type, String> {
    @Override
    public Artist.Type convertToEntityProperty(String databaseValue) {
        for (Artist.Type type: Artist.Type.values()) {
            if (type.getValue().equals(databaseValue)) {
                return type;
            }
        }
        return Artist.Type.UNKNOWN;
    }

    @Override
    public String convertToDatabaseValue(Artist.Type entityProperty) {
        return entityProperty.getValue();
    }

    @Override
    public Artist.Type transform(String object) {
        return convertToEntityProperty(object);
    }

    @Override
    public String inverseTransform(Artist.Type object) {
        return convertToDatabaseValue(object);
    }
}
