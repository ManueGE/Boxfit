package com.manuege.boxfitapp.transformers;

import com.manuege.boxfit.transformers.JSONObjectTransformer;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Manu on 1/2/18.
 */

public class SlashIdTransformer implements JSONObjectTransformer {
    @Override
    public JSONObject transform(JSONObject object) {
        try {
            if (object.has("_id")) {
                object.put("id", object.get("_id"));
                object.remove("_id");
            }
        } catch (JSONException ignore) {}

        return object;
    }

    @Override
    public JSONObject inverseTransform(JSONObject object) {
        return object;
    }
}
