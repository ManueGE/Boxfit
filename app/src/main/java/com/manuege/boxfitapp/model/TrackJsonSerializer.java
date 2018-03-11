package com.manuege.boxfitapp.model;

import com.manuege.boxfit.serializers.AbstractSerializer;
import com.manuege.boxfit.utils.Json;
import com.manuege.boxfit.utils.JsonArray;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;

/**
 * Created by Manu on 28/1/18.
 */

public class TrackJsonSerializer extends AbstractSerializer<Track, Long> {

    public TrackJsonSerializer(BoxStore boxStore) {
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
    protected Track createFreshObject(Long id) {
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

    @Override
    protected Long getId(Json json, String key) {
        return json.getLong(key);
    }

    @Override
    protected Long getId(JsonArray array, int index) {
        return array.getLong(index);
    }

    @Override
    protected Track getExistingObject(Long aLong) {
        return getBox().get(aLong);
    }

    @Override
    protected List<Track> getExistingObjects(List<Long> longs) {
        return getBox().get(longs);
    }
}
