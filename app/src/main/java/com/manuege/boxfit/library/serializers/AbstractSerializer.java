package com.manuege.boxfit.library.serializers;

import com.manuege.boxfit.library.utils.Json;
import com.manuege.boxfit.library.utils.JsonArray;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;

/**
 * Abstract class used as base to create objects serializers.
 * It takes a JSONObject or a JSONArray and insert them in the given `BoxStore`.
 * @param <T> The class of the objects that will be serialized.
 */
public abstract class AbstractSerializer<T> {
    protected BoxStore boxStore;

    public AbstractSerializer(BoxStore boxStore) {
        this.boxStore = boxStore;
    }

    public T serialize(Long id) {
        JSONObject jsonObject = getJSONObject(id);
        return serialize(jsonObject);
    }

    public T serialize(JSONObject jsonObject) {
        Json json = new Json(jsonObject);
        Long id = getId(json);

        T object;
        if (getBox() != null) {
            object = getBox().get(id);
            if (object == null) {
                object = freshObject(id);
                getBox().put(object);
            }
        } else {
            object = freshObject(null);
        }

        merge(json, object);
        if (getBox() != null) {
            getBox().put(object);
        }

        return object;
    }

    public List<T> serialize(JSONArray array) {
        JsonArray jsonArray = new JsonArray(array);

        // Get ids
        ArrayList<Json> jsons = new ArrayList<>();
        List<Long> ids = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            Long id ;
            Json json;
            JSONObject jsonObject;

            Object object = jsonArray.get(i);
            if (object == null) {
                continue;
            }
            if ((jsonObject = jsonArray.getJSONObject(i)) != null) {
                json = new Json(jsonObject);
                id = getId(json);
            } else if ((id = jsonArray.getLong(i)) != null) {
                jsonObject = getJSONObject(id);
                json = new Json(jsonObject);
            } else {
                continue;
            }

            jsons.add(json);
            if (id != null) {
                ids.add(id);
            }
        }

        // Store objects by its id
        HashMap<Long, T> existingObjectsById = new HashMap<>();
        if (getBox() != null) {
            List<T> existingObjects = getBox().get(ids);
            for (T existingObject : existingObjects) {
                existingObjectsById.put(getId(existingObject), existingObject);
            }
        }

        // Convert objects
        List<T> objects = new ArrayList<>();
        for (Json json : jsons) {
            Long id = getId(json);
            T object = existingObjectsById.get(id);
            if (object == null) {
                object = freshObject(id);
            }
            merge(json, object);
            objects.add(object);
        }

        if (getBox() != null) {
            getBox().put(objects);
        }

        return objects;
    }

    abstract protected Box<T> getBox();
    abstract protected void merge(Json json, T object);
    abstract protected T freshObject(Long id);
    abstract protected Long getId(Json json);
    abstract protected Long getId(T object);
    abstract protected JSONObject getJSONObject(Long id);
}
