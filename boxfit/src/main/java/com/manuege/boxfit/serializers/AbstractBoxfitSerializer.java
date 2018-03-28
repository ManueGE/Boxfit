package com.manuege.boxfit.serializers;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * A base class responsible of convert objects and lists from / to JSON.
 */
public interface AbstractBoxfitSerializer {
    <T> T fromJson(Class<? extends T> clazz, JSONObject jsonObject);
    <T> List<T> fromJson(Class<? extends T> clazz, JSONArray jsonArray);
    <T> JSONObject toJson(T object);
    <T> JSONArray toJson(List<T> object);
}
