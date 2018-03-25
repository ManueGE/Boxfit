package com.manuege.boxfit.serializers;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Manu on 11/3/18.
 */

public interface AbstractMainSerializer {
    <T> T fromJson(Class<? extends T> clazz, JSONObject jsonObject);
    <T> List<T> fromJson(Class<? extends T> clazz, JSONArray jsonArray);
    <T> JSONObject toJson(T object);
    <T> JSONArray toJson(List<T> object);
}
