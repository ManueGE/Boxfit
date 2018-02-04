package com.manuege.boxfit.serializers;


import com.manuege.boxfit.utils.Json;
import com.manuege.boxfit.utils.JsonArray;

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
 * @param <Entity> The class of the objects that will be serialized.
 * @param <Id> The class of the primary key of the objects that will be serialized.
 */
public abstract class AbstractSerializer<Entity, Id> {
    protected BoxStore boxStore;

    public AbstractSerializer(BoxStore boxStore) {
        this.boxStore = boxStore;
    }

    public Entity serialize(Id id) {
        JSONObject jsonObject = getJSONObject(id);
        return serialize(jsonObject);
    }

    public Entity serialize(JSONObject jsonObject) {
        jsonObject = convertedJSONObject(jsonObject);
        Json json = new Json(jsonObject);
        Id id = getId(json);

        Entity object;
        if (getBox() != null) {
            object = getExistingObject(id);
            if (object == null) {
                object = createFreshObject(id);
                getBox().put(object);
            }
        } else {
            object = createFreshObject(null);
        }

        merge(json, object);
        if (getBox() != null) {
            getBox().put(object);
        }

        return object;
    }

    public List<Entity> serialize(JSONArray array) {
        JsonArray jsonArray = new JsonArray(array);

        // Get ids
        ArrayList<Json> jsons = new ArrayList<>();
        List<Id> ids = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            Id id ;
            Json json;
            JSONObject jsonObject;

            Object object = jsonArray.get(i);
            if (object == null) {
                continue;
            }
            if ((jsonObject = jsonArray.getJSONObject(i)) != null) {
                json = new Json(jsonObject);
                id = getId(json);
            } else if ((id = getId(jsonArray, i)) != null) {
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
        HashMap<Id, Entity> existingObjectsById = new HashMap<>();
        if (getBox() != null) {
            List<Entity> existingObjects = getExistingObjects(ids);
            for (Entity existingObject : existingObjects) {
                existingObjectsById.put(getId(existingObject), existingObject);
            }
        }

        // Convert objects
        List<Entity> objects = new ArrayList<>();
        for (Json json : jsons) {
            Id id = getId(json);
            Entity object = existingObjectsById.get(id);
            if (object == null) {
                object = createFreshObject(id);
            }
            merge(json, object);
            objects.add(object);
        }

        if (getBox() != null) {
            getBox().put(objects);
        }

        return objects;
    }

    abstract protected void merge(Json json, Entity object);
    abstract protected Box<Entity> getBox();
    abstract protected Entity createFreshObject(Id id);
    abstract protected Id getId(Json json);
    abstract protected Id getId(JsonArray array, int index);
    abstract protected Id getId(Entity object);
    abstract protected JSONObject getJSONObject(Id id);
    abstract protected Entity getExistingObject(Id id);
    abstract protected List<Entity> getExistingObjects(List<Id> ids);
    protected JSONObject convertedJSONObject(JSONObject object) {
        return object;
    }
}
