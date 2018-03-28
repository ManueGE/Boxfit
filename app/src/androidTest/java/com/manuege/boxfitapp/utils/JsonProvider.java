package com.manuege.boxfitapp.utils;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Manu on 28/1/18.
 */

public class JsonProvider {
    public static String getResponse(String filename) {
        try {
            // https://medium.com/@yair.kukielka/android-unit-tests-explained-part-2-a0f1e1413569
            Context context = InstrumentationRegistry.getTargetContext();
            InputStream inputStream = context.getResources().getAssets().open(filename);
            return readTextStream(inputStream);
        } catch (IOException e) {
            return null;
        }
    }

    public static JSONObject getJSONObject(String filename) {
        try {
            // https://medium.com/@yair.kukielka/android-unit-tests-explained-part-2-a0f1e1413569
            String s = getResponse(filename);
            return new JSONObject(s);
        } catch (JSONException e) {
            return null;
        }
    }

    public static JSONArray getJSONArray(String filename) {
        try {

            String s = getResponse(filename);
            return new JSONArray(s);
        } catch (JSONException e) {
            return null;
        }
    }

    private static String readTextStream(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
