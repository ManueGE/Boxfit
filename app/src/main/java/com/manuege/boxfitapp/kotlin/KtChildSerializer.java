package com.manuege.boxfitapp.kotlin;

import com.manuege.boxfit.serializers.AbstractSerializer;
import com.manuege.boxfit.utils.Json;
import com.manuege.boxfit.utils.JsonArray;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;

public final class KtChildSerializer extends AbstractSerializer<KtChild, Long> {
    private static KtChildSerializer instance;

    private KtChildSerializer() {
    }

    public static KtChildSerializer getInstance() {
        if (instance == null) {
            instance = new KtChildSerializer();
        }
        return instance;
    }

    protected void merge(Json json, KtChild object, BoxStore boxStore) {
        KtChildSerializerBridge.Companion.getInstance().merge(json, object, boxStore);
    }

    protected Box<KtChild> getBox(BoxStore boxStore) {
        return boxStore.boxFor(KtChild.class);
    }

    protected KtChild createFreshObject(Long id) {
        return KtChildSerializerBridge.Companion.getInstance().createFreshObject(id);
    }

    protected Long getId(Json json) {
        return json.getLong("id");
    }

    protected Long getId(Json json, String key) {
        return json.getLong(key);
    }

    protected Long getId(JsonArray array, int index) {
        return array.getLong(index);
    }

    protected Long getId(KtChild object) {
        return KtChildSerializerBridge.Companion.getInstance().getId(object);
    }

    protected JSONObject getJSONObject(Long id) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
        }
        catch(JSONException ignored) {
        }
        return jsonObject;
    }

    protected KtChild getExistingObject(Long id, BoxStore boxStore) {
        if (id == null) {
            return null;
        }
        return getBox(boxStore).get(id);
    }

    protected List<KtChild> getExistingObjects(List<Long> ids, BoxStore boxStore) {
        return getBox(boxStore).get(ids);
    }

    public JSONObject toJson(KtChild object) {
        return KtChildSerializerBridge.Companion.getInstance().toJson(object);
    }
}