package com.manuege.boxfit.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Manu on 28/1/18.
 */

public class Json {
    private JSONObject jsonObject;

    public Json(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public boolean has(String key) {
        return jsonObject.has(key);
    }

    private boolean isNull(String key) {
        return jsonObject.isNull(key);
    }

    // String
    public String getString(String key) {
        if (isNull(key)) {
            return null;
        }
        try {
            return jsonObject.getString(key);
        } catch (JSONException e) {
            return null;
        }
    }

    // Integer
    public Integer getInt(String key) {
        if (isNull(key)) {
            return null;
        }
        try {
            return jsonObject.getInt(key);
        } catch (JSONException e) {
            return null;
        }
    }

    // Bool
    public Boolean getBoolean(String key) {
        if (isNull(key)) {
            return null;
        }
        try {
            return jsonObject.getBoolean(key);
        } catch (JSONException e) {
            return null;
        }
    }

    // Double
    public Double getDouble(String key) {
        if (isNull(key)) {
            return null;
        }
        try {
            return jsonObject.getDouble(key);
        } catch (JSONException e) {
            return null;
        }
    }

    // Long
    public Long getLong(String key) {
        if (isNull(key)) {
            return null;
        }
        try {
            return jsonObject.getLong(key);
        } catch (JSONException e) {
            return null;
        }
    }
    
    // JSONObject
    public JSONObject getJSONObject(String key) {
        if (isNull(key)) {
            return null;
        }
        try {
            return jsonObject.getJSONObject(key);
        } catch (JSONException e) {
            return null;
        }
    }

    // JSONArray
    public JSONArray getJSONArray(String key) {
        if (isNull(key)) {
            return null;
        }
        try {
            return jsonObject.getJSONArray(key);
        } catch (JSONException e) {
            return null;
        }
    }

    // Generic
    public Object get(String key) {
        if (isNull(key)) {
            return null;
        }
        try {
            return jsonObject.get(key);
        } catch (JSONException e) {
            return null;
        }
    }
}

