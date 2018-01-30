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

public class TrackSerializer extends AbstractSerializer<Track> {

    public TrackSerializer(BoxStore boxStore) {
        super(boxStore);
    }

    @Override
    protected Box<Track> getBox() {
        return boxStore.boxFor(Track.class);
    }

    @Override
    protected void merge(Json json, Track object) {
        if (json.has("name")) {
            object.name = json.getString("name");
        }
    }

    @Override
    protected Track freshObject(Long id) {
        Track object = new Track();
        object.id = id;
        return object;
    }

    @Override
    protected Long getId(Json json) {
        return json.getLong("id");
    }

    @Override
    protected Long getId(Track object) {
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
