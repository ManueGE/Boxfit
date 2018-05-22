package com.manuege.boxfit.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class Json {
    private JSONObject jsonObject;
    private HashMap<String, KeyPathResult> cachedKeyPathResults = new HashMap<>();

    // Constructor
    public Json(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    // Key Path
    private class KeyPathResult {
        JSONObject json;
        String key;

        public KeyPathResult(JSONObject json, String key) {
            this.json = json;
            this.key = key;
        }
    }

    private KeyPathResult getKeyPath(String keyPath) {
        if (!cachedKeyPathResults.containsKey(keyPath)) {
            cachedKeyPathResults.put(keyPath, getKeyPath(jsonObject, keyPath));
        }
        return cachedKeyPathResults.get(keyPath);
    }

    private KeyPathResult getKeyPath(JSONObject jsonObject, String keyPath) {
        if (jsonObject.has(keyPath)) {
            return new KeyPathResult(jsonObject, keyPath);
        }

        Integer indexOfDot = keyPath.indexOf(".");
        if (indexOfDot != -1) {
            String beforeDot = keyPath.substring(0, indexOfDot);
            String afterDot = keyPath.substring(indexOfDot + 1);

            JSONObject nextJsonObject;
            try {
                nextJsonObject = jsonObject.getJSONObject(beforeDot);
            } catch (JSONException ignore) {
                nextJsonObject = null;
            }

            if (nextJsonObject != null) {
                return getKeyPath(nextJsonObject, afterDot);
            }
        }

        return new KeyPathResult(jsonObject, keyPath);
    }

    // Helpers
    public boolean has(String keyPath) {
        KeyPathResult keyPathResult = getKeyPath(keyPath);
        return keyPathResult.json.has(keyPathResult.key);
    }

    public boolean isNull(String keyPath) {
        KeyPathResult keyPathResult = getKeyPath(keyPath);
        return keyPathResult.json.isNull(keyPathResult.key);
    }

    // String
    public String getString(String keyPath) {
        if (isNull(keyPath)) {
            return null;
        }
        try {
            KeyPathResult keyPathResult = getKeyPath(keyPath);
            return keyPathResult.json.getString(keyPathResult.key);
        } catch (JSONException e) {
            return null;
        }
    }

    // Integer
    public Integer getInt(String keyPath) {
        if (isNull(keyPath)) {
            return null;
        }
        try {
            KeyPathResult keyPathResult = getKeyPath(keyPath);
            return keyPathResult.json.getInt(keyPathResult.key);
        } catch (JSONException e) {
            return null;
        }
    }

    // Bool
    public Boolean getBoolean(String keyPath) {
        if (isNull(keyPath)) {
            return null;
        }
        try {
            KeyPathResult keyPathResult = getKeyPath(keyPath);
            return keyPathResult.json.getBoolean(keyPathResult.key);
        } catch (JSONException e) {
            return null;
        }
    }

    // Double
    public Double getDouble(String keyPath) {
        if (isNull(keyPath)) {
            return null;
        }
        try {
            KeyPathResult keyPathResult = getKeyPath(keyPath);
            return keyPathResult.json.getDouble(keyPathResult.key);
        } catch (JSONException e) {
            return null;
        }
    }

    // Long
    public Long getLong(String keyPath) {
        if (isNull(keyPath)) {
            return null;
        }
        try {
            KeyPathResult keyPathResult = getKeyPath(keyPath);
            return keyPathResult.json.getLong(keyPathResult.key);
        } catch (JSONException e) {
            return null;
        }
    }
    
    // JSONObject
    public JSONObject getJSONObject(String keyPath) {
        if (isNull(keyPath)) {
            return null;
        }
        try {
            KeyPathResult keyPathResult = getKeyPath(keyPath);
            return keyPathResult.json.getJSONObject(keyPathResult.key);
        } catch (JSONException e) {
            return null;
        }
    }

    // JSONArray
    public JSONArray getJSONArray(String keyPath) {
        if (isNull(keyPath)) {
            return null;
        }
        try {
            KeyPathResult keyPathResult = getKeyPath(keyPath);
            return keyPathResult.json.getJSONArray(keyPathResult.key);
        } catch (JSONException e) {
            return null;
        }
    }

    // Generic
    public Object get(String keyPath) {
        if (isNull(keyPath)) {
            return null;
        }
        try {
            KeyPathResult keyPathResult = getKeyPath(keyPath);
            return keyPathResult.json.get(keyPathResult.key);
        } catch (JSONException e) {
            return null;
        }
    }
}

