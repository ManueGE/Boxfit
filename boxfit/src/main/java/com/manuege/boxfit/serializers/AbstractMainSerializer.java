package com.manuege.boxfit.serializers;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Manu on 11/3/18.
 */

public interface AbstractMainSerializer {
    <T> T serialize(Class clazz, JSONObject jsonObject);
    <T> List<T> serialize(Class clazz, JSONArray jsonArray);
}
