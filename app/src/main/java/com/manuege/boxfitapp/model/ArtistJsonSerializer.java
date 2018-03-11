package com.manuege.boxfitapp.model;

import com.manuege.boxfit.serializers.AbstractSerializer;
import com.manuege.boxfit.utils.Json;
import com.manuege.boxfit.utils.JsonArray;
import com.manuege.boxfitapp.transformers.ApiStringToDateTransformer;
import com.manuege.boxfitapp.transformers.ArtistTypeTransformer;
import com.manuege.boxfitapp.transformers.StringToDateTransformer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;

/**
 * Created by Manu on 28/1/18.
 */

public class ArtistJsonSerializer extends AbstractSerializer<Artist, Long> {

    public ArtistJsonSerializer(BoxStore boxStore) {
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

        if (json.has("birth")) {
            String originalValue = json.getString("birth");
            StringToDateTransformer transformer = new ApiStringToDateTransformer();
            object.birthDate = transformer.transform(originalValue);
        }

        if (json.has("type")) {
            String originalValue = json.getString("type");
            ArtistTypeTransformer transformer = new ArtistTypeTransformer();
            object.type = transformer.transform(originalValue);
        }
    }

    @Override
    protected Artist createFreshObject(Long id) {
        Artist object = new Artist();
        object.id = id;
        return object;
    }

    @Override
    protected Long getId(Json json) {
        return json.getLong("id");
    }

    @Override
    protected Long getId(Json json, String key) {
        return json.getLong(key);
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

    @Override
    protected Long getId(JsonArray array, int index) {
        return array.getLong(index);
    }

    @Override
    protected Artist getExistingObject(Long aLong) {
        return getBox().get(aLong);
    }

    @Override
    protected List<Artist> getExistingObjects(List<Long> longs) {
        return getBox().get(longs);
    }

}
