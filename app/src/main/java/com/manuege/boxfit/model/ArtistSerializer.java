package com.manuege.boxfit.model;

import com.manuege.boxfit.library.serializers.AbstractSerializer;
import com.manuege.boxfit.library.utils.SafeJSON;

import io.objectbox.BoxStore;

/**
 * Created by Manu on 28/1/18.
 */

public class ArtistSerializer extends AbstractSerializer<Artist> {

    public ArtistSerializer(BoxStore boxStore) {
        super(Artist.class, boxStore);
    }

    @Override
    protected void merge(SafeJSON safeJson, Artist object) {
        if (safeJson.has("name")) {
            object.name = safeJson.getString("name");
        }
    }

    @Override
    protected Artist freshObject(Long id) {
        Artist object = new Artist();
        object.id = id;
        return object;
    }

    @Override
    protected Long getId(SafeJSON safeJSON) {
        return safeJSON.getLong("id");
    }

    @Override
    protected Long getId(Artist object) {
        return object.id;
    }

}
