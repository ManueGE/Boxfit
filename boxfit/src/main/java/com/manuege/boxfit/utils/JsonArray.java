package com.manuege.boxfit.utils;

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
        if (jsonArray == null) {
            return 0;
        }
        return jsonArray.length();
    }

    private boolean isNull(Integer index) {
        return jsonArray.isNull(index);
    }

    // String
    public String getString(int index) {
        if (isNull(index)) {
            return null;
        }
        try {
            return jsonArray.getString(index);
        } catch (JSONException e) {
            return null;
        }
    }

    // Integer
    public Integer getInt(int index) {
        if (isNull(index)) {
            return null;
        }
        try {
            return jsonArray.getInt(index);
        } catch (JSONException e) {
            return null;
        }
    }

    // Bool
    public Boolean getBoolean(int index) {
        if (isNull(index)) {
            return null;
        }
        try {
            return jsonArray.getBoolean(index);
        } catch (JSONException e) {
            return null;
        }
    }

    // Double
    public Double getDouble(int index) {
        if (isNull(index)) {
            return null;
        }
        try {
            return jsonArray.getDouble(index);
        } catch (JSONException e) {
            return null;
        }
    }

    // Long
    public Long getLong(int index) {
        if (isNull(index)) {
            return null;
        }
        try {
            return jsonArray.getLong(index);
        } catch (JSONException e) {
            return null;
        }
    }

    // JSONObject
    public JSONObject getJSONObject(int index) {
        if (isNull(index)) {
            return null;
        }
        try {
            return jsonArray.getJSONObject(index);
        } catch (JSONException e) {
            return null;
        }
    }

    // JSONArray
    public JSONArray getJSONArray(int index) {
        if (isNull(index)) {
            return null;
        }
        try {
            return jsonArray.getJSONArray(index);
        } catch (JSONException e) {
            return null;
        }
    }

    // Generic
    public Object get(int index) {
        if (isNull(index)) {
            return null;
        }
        try {
            return jsonArray.get(index);
        } catch (JSONException e) {
            return null;
        }
    }
}
