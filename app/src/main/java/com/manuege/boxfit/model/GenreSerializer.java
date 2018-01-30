package com.manuege.boxfit.model;

import com.manuege.boxfit.library.serializers.AbstractSerializer;
import com.manuege.boxfit.library.utils.Json;

import org.json.JSONException;
import org.json.JSONObject;

import io.objectbox.Box;
import io.objectbox.BoxStore;

/**
 * Created by Manu on 28/1/18.
 */

public class GenreSerializer extends AbstractSerializer<Genre> {

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
    protected Genre freshObject(Long id) {
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
}
