package com.manuege.boxfit.model;

import com.manuege.boxfit.library.serializers.AbstractSerializer;
import com.manuege.boxfit.library.utils.Json;
import com.manuege.boxfit.library.utils.JsonArray;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;

/**
 * Created by Manu on 28/1/18.
 */

public class GenreSerializer extends AbstractSerializer<Genre, Long> {

    public GenreSerializer(BoxStore boxStore) {
        super(boxStore);
    }

    @Override
    protected Box<Genre> getBox() {
        return boxStore.boxFor(Genre.class);
    }

    @Override
    protected void merge(Json json, Genre object) {
        if (json.has("name")) {
            object.name = json.getString("name");
        }
    }

    @Override
    protected Genre createFreshObject(Long id) {
        Genre object = new Genre();
        object.id = id;
        return object;
    }

    @Override
    protected Long getId(Json json) {
        return json.getLong("id");
    }

    @Override
    protected Long getId(Genre object) {
        return object.id;
    }

    @Override
    protected JSONObject getJSONObject(Long id) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", id);
            return jsonObject;
        } catch (JSONException e) {
            return null;
        }
    }

    @Override
    protected Long getId(JsonArray array, int index) {
        return array.getLong(index);
    }

    @Override
    protected Genre getExistingObject(Long aLong) {
        return getBox().get(aLong);
    }

    @Override
    protected List<Genre> getExistingObjects(List<Long> longs) {
        return getBox().get(longs);
    }
}
