package com.manuege.boxfit.model;

import com.manuege.boxfit.library.serializers.AbstractSerializer;
import com.manuege.boxfit.library.utils.SafeJSON;

import io.objectbox.BoxStore;

/**
 * Created by Manu on 28/1/18.
 */

public class TrackSerializer extends AbstractSerializer<Track> {

    public TrackSerializer(BoxStore boxStore) {
        super(Track.class, boxStore);
    }

    @Override
    protected void merge(SafeJSON safeJson, Track object) {
        if (safeJson.has("name")) {
            object.name = safeJson.getString("name");
        }
    }

    @Override
    protected Track freshObject(Long id) {
        Track object = new Track();
        object.id = id;
        return object;
    }

    @Override
    protected Long getId(SafeJSON safeJSON) {
        return safeJSON.getLong("id");
    }

    @Override
    protected Long getId(Track object) {
        return object.id;
    }
}
