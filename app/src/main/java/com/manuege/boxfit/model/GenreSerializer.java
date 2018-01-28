package com.manuege.boxfit.model;

import com.manuege.boxfit.library.serializers.AbstractSerializer;
import com.manuege.boxfit.library.utils.SafeJSON;

import io.objectbox.BoxStore;

/**
 * Created by Manu on 28/1/18.
 */

public class GenreSerializer extends AbstractSerializer<Genre> {

    public GenreSerializer(BoxStore boxStore) {
        super(Genre.class, boxStore);
    }

    @Override
    protected void merge(SafeJSON safeJson, Genre object) {
        if (safeJson.has("name")) {
            object.name = safeJson.getString("name");
        }
    }

    @Override
    protected Genre freshObject(Long id) {
        Genre object = new Genre();
        object.id = id;
        return object;
    }

    @Override
    protected Long getId(SafeJSON safeJSON) {
        return safeJSON.getLong("id");
    }

    @Override
    protected Long getId(Genre object) {
        return object.id;
    }
}
