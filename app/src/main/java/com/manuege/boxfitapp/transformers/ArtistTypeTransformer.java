package com.manuege.boxfitapp.transformers;

import com.manuege.boxfit.transformers.Transformer;
import com.manuege.boxfitapp.model.Artist;

import io.objectbox.converter.PropertyConverter;

/**
 * Created by Manu on 1/2/18.
 */

public class ArtistTypeTransformer implements PropertyConverter<Artist.Type, String>, Transformer<String, Artist.Type> {
    @Override
    public Artist.Type convertToEntityProperty(String databaseValue) {
        return typeFromString(databaseValue);
    }

    @Override
    public String convertToDatabaseValue(Artist.Type entityProperty) {
        return entityProperty.getValue();
    }

    @Override
    public Artist.Type transform(String object) {
        return typeFromString(object);
    }

    private Artist.Type typeFromString(String string) {
        for (Artist.Type type: Artist.Type.values()) {
            if (type.getValue().equals(string)) {
                return type;
            }
        }
        return Artist.Type.UNKNOWN;
    }
}
