package com.manuege.boxfitapp.transformers;

import com.manuege.boxfit.transformers.Transformer;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.converter.PropertyConverter;

public class ListIntToStringTransformer implements PropertyConverter<List<Integer>, String>, Transformer<List<Integer>, String> {
    @Override
    public List<Integer> transform(String object) {
        return convertToEntityProperty(object);
    }

    @Override
    public String inverseTransform(List<Integer> object) {
        return convertToDatabaseValue(object);
    }

    @Override
    public List<Integer> convertToEntityProperty(String databaseValue) {
        ArrayList<Integer> list = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(databaseValue);
            for (int i = 0; i < jsonArray.length(); i++) {
                list.add(jsonArray.getInt(i));
            }

        } catch (JSONException ignore) {}
        return list;
    }

    @Override
    public String convertToDatabaseValue(List<Integer> entityProperty) {
        JSONArray jsonArray = new JSONArray();
        for (Integer i: entityProperty) {
            jsonArray.put(i);
        }
        return jsonArray.toString();
    }
}
