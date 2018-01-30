package com.manuege.boxfit.api.model;

import com.manuege.boxfit.library.serializers.AbstractSerializer;
import com.manuege.boxfit.library.serializers.MainSerializer;
import com.manuege.boxfit.library.utils.Json;
import com.manuege.boxfit.model.Album;

import org.json.JSONObject;

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
    protected void merge(Json json, Paginated.Albums object) {
        if (json.has("count")) {
            object.count = json.getInt("count");
        }
        if (json.has("previous")) {
            object.previous = json.getInt("previous");
        }
        if (json.has("next")) {
            object.next = json.getInt("next");
        }
        if (json.has("results")) {
            MainSerializer serializer = new MainSerializer(boxStore);
            object.results = new MainSerializer(boxStore).serialize(Album.class, json.getJSONArray("results"));
        }
    }

    @Override
    protected Paginated.Albums freshObject(Long id) {
        return new Paginated.Albums();
    }

    @Override
    protected Long getId(Json json) {
        return null;
    }

    @Override
    protected Long getId(Paginated.Albums object) {
        return null;
    }

    @Override
    protected JSONObject getJSONObject(Long id) {
        return new JSONObject();
    }
}
