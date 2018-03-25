package com.manuege.boxfitapp.fromJson;

import com.manuege.boxfit.MainJsonSerializer;
import com.manuege.boxfitapp.AbstractObjectBoxTest;
import com.manuege.boxfitapp.api.model.NoObjectBoxObject;
import com.manuege.boxfitapp.api.model.ParentResponse;
import com.manuege.boxfitapp.api.model.SingleParentResponse;
import com.manuege.boxfitapp.utils.JsonProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Created by Manu on 28/1/18.
 */

public class FromJsonNoEntitiesSerializationTests extends AbstractObjectBoxTest {
    @Test
    public void fromJsonNoEntities_canSerialize() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("integer", 1);
        jsonObject.put("string", "hello");

        MainJsonSerializer serializer = new MainJsonSerializer(boxStore);
        NoObjectBoxObject object = serializer.fromJson(NoObjectBoxObject.class, jsonObject);

        Assert.assertEquals(1, object.integer);
        Assert.assertEquals("hello", object.string);
    }

    @Test
    public void fromJsonNoEntities_canSerializeWithoutBoxStore() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("integer", 1);
        jsonObject.put("string", "hello");

        MainJsonSerializer serializer = new MainJsonSerializer(null);
        NoObjectBoxObject object = serializer.fromJson(NoObjectBoxObject.class, jsonObject);

        Assert.assertEquals(1, object.integer);
        Assert.assertEquals("hello", object.string);
    }

    @Test
    public void fromJsonNoEntities_canSerializeArray() throws JSONException {
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("integer", 1);
        jsonObject1.put("string", "hello");

        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("integer", 2);
        jsonObject2.put("string", "world");

        JSONArray jsonArray = new JSONArray();
        jsonArray.put(jsonObject1);
        jsonArray.put(jsonObject2);

        MainJsonSerializer serializer = new MainJsonSerializer(null);
        List<NoObjectBoxObject> array = serializer.fromJson(NoObjectBoxObject.class, jsonArray);

        Assert.assertEquals(2, array.size());

        Assert.assertEquals(1, array.get(0).integer);
        Assert.assertEquals("hello", array.get(0).string);

        Assert.assertEquals(2, array.get(1).integer);
        Assert.assertEquals("world", array.get(1).string);
    }

    @Test
    public void fromJsonNoEntities_canSerializeWthEntityField() throws JSONException {
        JSONObject parentJSON = JsonProvider.getJSONObject("parent.json");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("parent", parentJSON);
        MainJsonSerializer serializer = new MainJsonSerializer(boxStore);
        ParentResponse object = serializer.fromJson(ParentResponse.class, jsonObject);

        Assert.assertNotNull(object.parent);
        Assert.assertEquals(1, object.parent.id);
        Assert.assertEquals("hello", object.parent.stringField);
    }

    @Test
    public void fromJsonNoEntities_canSerializeWthGenericEntityField() throws JSONException {
        JSONObject parentJSON = JsonProvider.getJSONObject("parent.json");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("parent", parentJSON);
        MainJsonSerializer serializer = new MainJsonSerializer(boxStore);
        SingleParentResponse object = serializer.fromJson(SingleParentResponse.class, jsonObject);

        Assert.assertNotNull(object.data);
        Assert.assertEquals(1, object.data.id);
        Assert.assertEquals("hello", object.data.stringField);
    }
}
