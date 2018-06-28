package com.manuege.boxfitapp.fromJson;

import com.manuege.boxfit.BoxfitSerializer;
import com.manuege.boxfitapp.AbstractObjectBoxTest;
import com.manuege.boxfitapp.model.java.ManualId;
import com.manuege.boxfitapp.utils.JsonProvider;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class FromJsonManualIdTest extends AbstractObjectBoxTest {

    @Test
    public void fromJson_importNewObject() throws Exception {
        JSONObject json = JsonProvider.getJSONObject("manual_id.json");
        BoxfitSerializer boxfitSerializer = new BoxfitSerializer(boxStore);
        ManualId object = boxfitSerializer.fromJson(ManualId.class, json);

        Assert.assertEquals(1, boxStore.boxFor(ManualId.class).count());

        Assert.assertNotNull(object);
        Assert.assertEquals("a", object.manualId);
        Assert.assertEquals("one", object.value);

        // second object
        json.put("id", "b");
        json.put("value", "two");

        ManualId object2 = boxfitSerializer.fromJson(ManualId.class, json);

        Assert.assertEquals(2, boxStore.boxFor(ManualId.class).count());
        Assert.assertNotNull(object);
        Assert.assertEquals("b", object2.manualId);
        Assert.assertEquals("two", object2.value);
    }

    @Test
    public void fromJson_updateObject() throws Exception {
        JSONObject json = JsonProvider.getJSONObject("manual_id.json");
        BoxfitSerializer boxfitSerializer = new BoxfitSerializer(boxStore);
        boxfitSerializer.fromJson(ManualId.class, json);

        Assert.assertEquals(1, boxStore.boxFor(ManualId.class).count());

        json.put("value", "uno");
        ManualId object = boxfitSerializer.fromJson(ManualId.class, json);

        Assert.assertEquals(1, boxStore.boxFor(ManualId.class).count());
        Assert.assertNotNull(object);
        Assert.assertEquals("a", object.manualId);
        Assert.assertEquals("uno", object.value);
    }

    @Test
    public void fromJson_newObjectsFromList() throws Exception {
        JSONArray jsonArray = JsonProvider.getJSONArray("manual_id_array.json");
        BoxfitSerializer boxfitSerializer = new BoxfitSerializer(boxStore);
        List<ManualId> manualIds = boxfitSerializer.fromJson(ManualId.class, jsonArray);

        Assert.assertEquals(3, boxStore.boxFor(ManualId.class).count());

        ManualId object = manualIds.get(0);
        Assert.assertEquals("a", object.manualId);
        Assert.assertEquals("one", object.value);

        ManualId object2 = manualIds.get(1);
        Assert.assertEquals("b", object2.manualId);
        Assert.assertEquals("two", object2.value);

        ManualId object3 = manualIds.get(2);
        Assert.assertEquals("c", object3.manualId);
        Assert.assertEquals("three", object3.value);
    }

    @Test
    public void fromJson_newSingleObjectFromList() throws Exception {
        JSONObject json = JsonProvider.getJSONObject("manual_id.json");
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(json);

        BoxfitSerializer boxfitSerializer = new BoxfitSerializer(boxStore);
        List<ManualId> manualIds = boxfitSerializer.fromJson(ManualId.class, jsonArray);

        Assert.assertEquals(1, boxStore.boxFor(ManualId.class).count());

        ManualId object = manualIds.get(0);
        Assert.assertEquals("a", object.manualId);
        Assert.assertEquals("one", object.value);
    }

    @Test
    public void fromJson_noObjectFromList() throws Exception {
        JSONArray jsonArray = new JSONArray();

        BoxfitSerializer boxfitSerializer = new BoxfitSerializer(boxStore);
        List<ManualId> manualIds = boxfitSerializer.fromJson(ManualId.class, jsonArray);

        Assert.assertEquals(0, boxStore.boxFor(ManualId.class).count());
    }

    @Test
    public void fromJson_updateObjectsFromList() throws Exception {
        BoxfitSerializer boxfitSerializer = new BoxfitSerializer(boxStore);

        JSONObject json = JsonProvider.getJSONObject("manual_id.json");
        boxfitSerializer.fromJson(ManualId.class, json);

        JSONArray jsonArray = JsonProvider.getJSONArray("manual_id_array.json");
        jsonArray.getJSONObject(0).put("value", "uno");
        List<ManualId> manualIds = boxfitSerializer.fromJson(ManualId.class, jsonArray);

        Assert.assertEquals(3, boxStore.boxFor(ManualId.class).count());

        ManualId object = object = manualIds.get(0);
        Assert.assertEquals("a", object.manualId);
        Assert.assertEquals("uno", object.value);

        ManualId object2 = manualIds.get(1);
        Assert.assertEquals("b", object2.manualId);
        Assert.assertEquals("two", object2.value);

        ManualId object3 = manualIds.get(2);
        Assert.assertEquals("c", object3.manualId);
        Assert.assertEquals("three", object3.value);
    }

    @Test
    public void fromJson_updateSingleObjectFromList() throws Exception {
        BoxfitSerializer boxfitSerializer = new BoxfitSerializer(boxStore);

        JSONObject json = JsonProvider.getJSONObject("manual_id.json");
        boxfitSerializer.fromJson(ManualId.class, json);

        json.put("value", "uno");
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(json);

        List<ManualId> manualIds = boxfitSerializer.fromJson(ManualId.class, jsonArray);

        Assert.assertEquals(1, boxStore.boxFor(ManualId.class).count());

        ManualId object = manualIds.get(0);
        Assert.assertEquals("a", object.manualId);
        Assert.assertEquals("uno", object.value);
    }
}
