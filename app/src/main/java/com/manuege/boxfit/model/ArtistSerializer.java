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

public class ArtistSerializer extends AbstractSerializer<Artist> {

    public ArtistSerializer(BoxStore boxStore) {
        super(boxStore);
    }

    @Override
    protected Box<Artist> getBox() {
        return boxStore.boxFor(Artist.class);
    }

    @Override
    protected void merge(Json json, Artist object) {
        if (json.has("name")) {
            object.name = json.getString("name");
        }
    }

    @Override
    protected Artist freshObject(Long id) {
        Artist object = new Artist();
        object.id = id;
        return object;
    }

    @Override
    protected Long getId(Json json) {
        return json.getLong("id");
    }

    @Override
    protected Long getId(Artist object) {
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
