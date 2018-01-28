package com.manuege.boxfit.library.serializers;

import com.manuege.boxfit.library.utils.SafeJSON;

import org.json.JSONArray;
import org.json.JSONException;
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

    public T serialize(JSONObject jsonObject) {
        SafeJSON safeJson = new SafeJSON(jsonObject);
        Long id = getId(safeJson);

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

        merge(safeJson, object);
        if (getBox() != null) {
            getBox().put(object);
        }

        return object;
    }

    public List<T> serialize(JSONArray jsonArray) {

        // Get ids
        ArrayList<SafeJSON> safeJSONs = new ArrayList<>();
        List<Long> ids = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject object = jsonArray.getJSONObject(i);
                SafeJSON safeJson = new SafeJSON(object);
                safeJSONs.add(safeJson);
                Long id = getId(safeJson);
                if (id != null) {
                    ids.add(id);
                }
            } catch (JSONException ignore) {
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
        for (SafeJSON safeJSON : safeJSONs) {
            Long id = getId(safeJSON);
            T object = existingObjectsById.get(id);
            if (object == null) {
                object = freshObject(id);
            }
            merge(safeJSON, object);
            objects.add(object);
        }

        if (getBox() != null) {
            getBox().put(objects);
        }

        return objects;
    }

    abstract protected Box<T> getBox();
    abstract protected void merge(SafeJSON safeJson, T object);
    abstract protected T freshObject(Long id);
    abstract protected Long getId(SafeJSON safeJSON);
    abstract protected Long getId(T object);
}
