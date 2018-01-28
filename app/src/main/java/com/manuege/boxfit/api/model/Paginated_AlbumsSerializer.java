package com.manuege.boxfit.api.model;

import com.manuege.boxfit.library.serializers.AbstractSerializer;
import com.manuege.boxfit.library.serializers.MainSerializer;
import com.manuege.boxfit.library.utils.SafeJSON;
import com.manuege.boxfit.model.Album;

import io.objectbox.Box;
import io.objectbox.BoxStore;

/**
 * Created by Manu on 28/1/18.
 */

public class Paginated_AlbumsSerializer extends AbstractSerializer<Paginated.Albums> {
    public Paginated_AlbumsSerializer(BoxStore boxStore) {
        super(boxStore);
    }

    @Override
    protected Box<Paginated.Albums> getBox() {
        return null;
    }

    @Override
    protected void merge(SafeJSON safeJson, Paginated.Albums object) {
        if (safeJson.has("count")) {
            object.count = safeJson.getInt("count");
        }
        if (safeJson.has("previous")) {
            object.previous = safeJson.getInt("previous");
        }
        if (safeJson.has("next")) {
            object.next = safeJson.getInt("next");
        }
        if (safeJson.has("results")) {
            MainSerializer serializer = new MainSerializer(boxStore);
            object.results = new MainSerializer(boxStore).serialize(Album.class, safeJson.getJSONArray("results"));
        }
    }

    @Override
    protected Paginated.Albums freshObject(Long id) {
        return new Paginated.Albums();
    }

    @Override
    protected Long getId(SafeJSON safeJSON) {
        return null;
    }

    @Override
    protected Long getId(Paginated.Albums object) {
        return null;
    }
}
