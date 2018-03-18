package com.manuege.boxfitapp.tojson;

import com.manuege.boxfit.MainJsonSerializer;
import com.manuege.boxfitapp.AbstractObjectBoxTest;
import com.manuege.boxfitapp.model.Parent;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Manu on 18/3/18.
 */

public class ParentToJsonSerializer extends AbstractObjectBoxTest{
    @Test
    public void parentSerializer_toJson() throws JSONException {
        Parent parent = new Parent();
        parent.id = 1;
        parent.stringField = "hello";
        boxStore.boxFor(Parent.class).put(parent);

        MainJsonSerializer mainJsonSerializer = new MainJsonSerializer(boxStore);
        JSONObject object = mainJsonSerializer.toJson(parent);

        Assert.assertEquals(1, object.getLong("id"));
        Assert.assertEquals("hello", object.getString("string"));
    }
}
