package com.manuege.boxfit.library.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Manu on 30/1/18.
 */

public class JsonArray {
    private JSONArray jsonArray;

    public JsonArray(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    public int length() {
        return jsonArray.length();
    }

    // String
    public String getString(int index, String defaultValue) {
        try {
            return jsonArray.getString(index);
        } catch (JSONException e) {
            return defaultValue;
        }
    }

    public String getString(int index) {
        return getString(index, null);
    }

    // Integer
    public Integer getInt(int index, Integer defaultValue) {
        try {
            return jsonArray.getInt(index);
        } catch (JSONException e) {
            return defaultValue;
        }
    }

    public Integer getInt(int index) {
        return getInt(index, null);
    }

    // Bool
    public Boolean getBoolean(int index, Boolean defaultValue) {
        try {
            return jsonArray.getBoolean(index);
        } catch (JSONException e) {
            return defaultValue;
        }
    }

    public Boolean getBoolean(int index) {
        return getBoolean(index, null);
    }

    // Double
    public Double getDouble(int index, Double defaultValue) {
        try {
            return jsonArray.getDouble(index);
        } catch (JSONException e) {
            return defaultValue;
        }
    }

    public Double getDouble(int index) {
        return getDouble(index, null);
    }

    // Long
    public Long getLong(int index, Long defaultValue) {
        try {
            return jsonArray.getLong(index);
        } catch (JSONException e) {
            return defaultValue;
        }
    }

    public Long getLong(int index) {
        return getLong(index, null);
    }

    // JSONObject
    public JSONObject getJSONObject(int index, JSONObject defaultValue) {
        try {
            return jsonArray.getJSONObject(index);
        } catch (JSONException e) {
            return defaultValue;
        }
    }

    public JSONObject getJSONObject(int index) {
        return getJSONObject(index, null);
    }

    // JSONArray
    public JSONArray getJSONArray(int index, JSONArray defaultValue) {
        try {
            return jsonArray.getJSONArray(index);
        } catch (JSONException e) {
            return defaultValue;
        }
    }

    public JSONArray getJSONArray(int index) {
        return getJSONArray(index, null);
    }

    // Generic
    public Object get(int index, Object defaultValue) {
        try {
            return jsonArray.get(index);
        } catch (JSONException e) {
            return defaultValue;
        }
    }

    public Object get(int index) {
        return get(index, null);
    }
}
