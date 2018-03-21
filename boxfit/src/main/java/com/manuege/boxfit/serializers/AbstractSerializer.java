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

    public Entity serializeRelationship(Json json, String key, BoxStore boxStore) {
        Object value = json.get(key);
        JSONObject jsonObject;
        Id id;
        if (value == null) {
            return null;
        }
        else if ((jsonObject = json.getJSONObject(key)) != null) {
            return fromJson(jsonObject, boxStore);
        } else if ((id = getId(json, key)) != null) {
            return serialize(id, boxStore);
        }
        return null;
    }

    private Entity serialize(Id id, BoxStore boxStore) {
        JSONObject jsonObject = getJSONObject(id);
        return fromJson(jsonObject, boxStore);
    }

    public Entity fromJson(JSONObject jsonObject, BoxStore boxStore) {
        Box<Entity> box = getBox(boxStore);
        jsonObject = getTransformedJSONObject(jsonObject);
        Json json = new Json(jsonObject);
        Id id = getId(json);

        Entity object;

        if (box != null) {
            object = getExistingObject(id, boxStore);
            if (object == null) {
                object = createFreshObject(id);
                box.put(object);
            }
        } else {
            object = createFreshObject(null);
        }

        merge(json, object, boxStore);
        if (box != null) {
            box.put(object);
        }

        return object;
    }

    public List<Entity> fromJson(JSONArray array, BoxStore boxStore) {
        Box<Entity> box = getBox(boxStore);
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
        if (box != null) {
            List<Entity> existingObjects = getExistingObjects(ids, boxStore);
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
                if (box != null) {
                    box.put(object);
                }
            }
            merge(json, object, boxStore);
            objects.add(object);
        }

        if (box != null) {
            box.put(objects);
        }

        return objects;
    }

    abstract protected void merge(Json json, Entity object, BoxStore boxStore);
    abstract protected Box<Entity> getBox(BoxStore boxStore);
    abstract protected Entity createFreshObject(Id id);
    abstract protected Id getId(Json json);
    abstract protected Id getId(Json json, String key);
    abstract protected Id getId(JsonArray array, int index);
    abstract protected Id getId(Entity object);
    abstract protected JSONObject getJSONObject(Id id);
    abstract protected Entity getExistingObject(Id id, BoxStore boxStore);
    abstract protected List<Entity> getExistingObjects(List<Id> ids, BoxStore boxStore);
    protected JSONObject getTransformedJSONObject(JSONObject object) {
        return object;
    }

    abstract public JSONObject toJson(Entity object);
    public JSONArray toJson(List<Entity> objects) {
        JSONArray array = new JSONArray();
        for (Entity object: objects) {
            array.put(toJson(object));
        }
        return array;
    }
}
