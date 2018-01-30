package com.manuege.boxfit.library.utils;

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

    // String
    public String getString(String key, String defaultValue) {
        try {
            return jsonObject.getString(key);
        } catch (JSONException e) {
            return defaultValue;
        }
    }

    public String getString(String key) {
        return getString(key, null);
    }

    // Integer
    public Integer getInt(String key, Integer defaultValue) {
        try {
            return jsonObject.getInt(key);
        } catch (JSONException e) {
            return defaultValue;
        }
    }

    public Integer getInt(String key) {
        return getInt(key, null);
    }

    // Bool
    public Boolean getBoolean(String key, Boolean defaultValue) {
        try {
            return jsonObject.getBoolean(key);
        } catch (JSONException e) {
            return defaultValue;
        }
    }

    public Boolean getBoolean(String key) {
        return getBoolean(key, null);
    }

    // Double
    public Double getDouble(String key, Double defaultValue) {
        try {
            return jsonObject.getDouble(key);
        } catch (JSONException e) {
            return defaultValue;
        }
    }

    public Double getDouble(String key) {
        return getDouble(key, null);
    }

    // Long
    public Long getLong(String key, Long defaultValue) {
        try {
            return jsonObject.getLong(key);
        } catch (JSONException e) {
            return defaultValue;
        }
    }

    public Long getLong(String key) {
        return getLong(key, null);
    }
    
    // JSONObject
    public JSONObject getJSONObject(String key, JSONObject defaultValue) {
        try {
            return jsonObject.getJSONObject(key);
        } catch (JSONException e) {
            return defaultValue;
        }
    }

    public JSONObject getJSONObject(String key) {
        return getJSONObject(key, null);
    }

    // JSONArray
    public JSONArray getJSONArray(String key, JSONArray defaultValue) {
        try {
            return jsonObject.getJSONArray(key);
        } catch (JSONException e) {
            return defaultValue;
        }
    }

    public JSONArray getJSONArray(String key) {
        return getJSONArray(key, null);
    }

    // Generic
    public Object get(String key, Object defaultValue) {
        try {
            return jsonObject.get(key);
        } catch (JSONException e) {
            return defaultValue;
        }
    }

    public Object get(String key) {
        return get(key, null);
    }
}

