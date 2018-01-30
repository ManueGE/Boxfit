package com.manuege.boxfit.utils;

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
    public static JSONObject getJSONObject(String filename) {
        try {
            // https://medium.com/@yair.kukielka/android-unit-tests-explained-part-2-a0f1e1413569
            Context context = InstrumentationRegistry.getTargetContext();
            InputStream inputStream = context.getResources().getAssets().open(filename);
            String s = readTextStream(inputStream);
            return new JSONObject(s);
        } catch (JSONException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    public static JSONArray getJSONArray(String filename) {
        InputStream inputStream = JsonProvider.class.getClassLoader().getResourceAsStream(filename);
        String s = readTextStream(inputStream);
        try {
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
