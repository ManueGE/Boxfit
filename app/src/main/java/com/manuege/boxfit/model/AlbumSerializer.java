package com.manuege.boxfit.model;

import com.manuege.boxfit.library.serializers.AbstractSerializer;
import com.manuege.boxfit.library.utils.Json;
import com.manuege.boxfit.library.utils.JsonArray;
import com.manuege.boxfit.transformers.AlbumJSONTransformer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;

/**
 * Created by Manu on 28/1/18.
 */

public class AlbumSerializer extends AbstractSerializer<Album, Long> {

    public AlbumSerializer(BoxStore boxStore) {
        super(boxStore);
    }

    @Override
    protected Box<Album> getBox() {
        return boxStore.boxFor(Album.class);
    }

    @Override
    protected void merge(Json json, Album object) {
        if (json.has("name")) {
            object.name = json.getString("name");
        }

        if (json.has("year")) {
            object.year = json.getInt("year", 0);
        }

        if (json.has("rate")) {
            object.rate = json.getInt("rate");
        }

        if (json.has("artist")) {
            Object value = json.get("artist");
            JSONObject jsonObject;
            Long id;
            if (value == null) {
                object.artist.setTarget(null);
            }
            else if ((jsonObject = json.getJSONObject("artist")) != null) {
                ArtistSerializer serializer = new ArtistSerializer(boxStore);
                Artist property = serializer.serialize(jsonObject);
                object.artist.setTarget(property);
            } else if ((id = json.getLong("artist")) != null) {
                ArtistSerializer serializer = new ArtistSerializer(boxStore);
                Artist property = serializer.serialize(id);
                object.artist.setTarget(property);
            }
        }

        if (json.has("genre")) {
            Object value = json.get("genre");
            JSONObject jsonObject;
            Long id;
            if (value == null) {
                object.genre.setTarget(null);
            }
            else if ((jsonObject = json.getJSONObject("genre")) != null) {
                GenreSerializer serializer = new GenreSerializer(boxStore);
                Genre property = serializer.serialize(jsonObject);
                object.genre.setTarget(property);
            } else if ((id = json.getLong("genre")) != null) {
                GenreSerializer serializer = new GenreSerializer(boxStore);
                Genre property = serializer.serialize(id);
                object.genre.setTarget(property);
            }
        }

        if (json.has("tracks")) {
            JSONArray jsonArray = json.getJSONArray("tracks");
            object.tracks.clear();
            if (jsonArray != null) {
                TrackSerializer serializer = new TrackSerializer(boxStore);
                List<Track> property = serializer.serialize(jsonArray);
                object.tracks.addAll(property);
            }
        }
    }

    @Override
    protected Album createFreshObject(Long id) {
        Album object = new Album();
        object.id = id;
        return object;
    }

    @Override
    protected Long getId(Json json) {
        return json.getLong("id");
    }

    @Override
    protected Long getId(Album object) {
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
    protected Album getExistingObject(Long aLong) {
        return getBox().get(aLong);
    }

    @Override
    protected List<Album> getExistingObjects(List<Long> longs) {
        return getBox().get(longs);
    }

    @Override
    protected JSONObject convertedJSONObject(JSONObject object) {
        AlbumJSONTransformer transformer = new AlbumJSONTransformer();
        return transformer.transform(object);
    }
}
