package com.manuege.boxfit.api.model;

import com.manuege.boxfit.library.serializers.AbstractSerializer;
import com.manuege.boxfit.library.serializers.MainSerializer;
import com.manuege.boxfit.library.utils.Json;
import com.manuege.boxfit.library.utils.JsonArray;
import com.manuege.boxfit.model.Album;

import org.json.JSONObject;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;

/**
 * Created by Manu on 28/1/18.
 */

public class Paginated_AlbumsSerializer extends AbstractSerializer<Paginated.Albums, Void> {
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
            object.results = serializer.serialize(Album.class, json.getJSONArray("results"));
        }
    }

    @Override
    protected Paginated.Albums createFreshObject(Void id) {
        return new Paginated.Albums();
    }

    @Override
    protected Void getId(Json json) {
        return null;
    }

    @Override
    protected Void getId(Paginated.Albums object) {
        return null;
    }

    @Override
    protected JSONObject getJSONObject(Void id) {
        return new JSONObject();
    }

    @Override
    protected Void getId(JsonArray array, int index) {
        return null;
    }

    @Override
    protected Paginated.Albums getExistingObject(Void aVoid) {
        return null;
    }

    @Override
    protected List<Paginated.Albums> getExistingObjects(List<Void> voids) {
        return null;
    }
}