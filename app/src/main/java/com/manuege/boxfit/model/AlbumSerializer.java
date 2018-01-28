package com.manuege.boxfit.model;

import com.manuege.boxfit.library.serializers.AbstractSerializer;
import com.manuege.boxfit.library.utils.SafeJSON;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;

/**
 * Created by Manu on 28/1/18.
 */

public class AlbumSerializer extends AbstractSerializer<Album> {

    public AlbumSerializer(BoxStore boxStore) {
        super(boxStore);
    }

    @Override
    protected Box<Album> getBox() {
        return boxStore.boxFor(Album.class);
    }

    @Override
    protected void merge(SafeJSON safeJson, Album object) {
        if (safeJson.has("name")) {
            object.name = safeJson.getString("name");
        }
        if (safeJson.has("year")) {
            object.year = safeJson.getInt("year");
        }
        if (safeJson.has("artist")) {
            JSONObject jsonObject = safeJson.getJSONObject("artist");
            ArtistSerializer serializer = new ArtistSerializer(boxStore);
            Artist property = serializer.serialize(jsonObject);
            object.artist.setTarget(property);
        }
        if (safeJson.has("genre")) {
            JSONObject jsonObject = safeJson.getJSONObject("genre");
            GenreSerializer serializer = new GenreSerializer(boxStore);
            Genre property = serializer.serialize(jsonObject);
            object.genre.setTarget(property);
        }
        if (safeJson.has("tracks")) {
            JSONArray jsonArray = safeJson.getJSONArray("tracks");
            TrackSerializer serializer = new TrackSerializer(boxStore);
            List<Track> property = serializer.serialize(jsonArray);
            object.tracks.clear();
            object.tracks.addAll(property);
        }
    }

    @Override
    protected Album freshObject(Long id) {
        Album object = new Album();
        object.id = id;
        return object;
    }

    @Override
    protected Long getId(SafeJSON safeJSON) {
        return safeJSON.getLong("id");
    }

    @Override
    protected Long getId(Album object) {
        return object.id;
    }

}
