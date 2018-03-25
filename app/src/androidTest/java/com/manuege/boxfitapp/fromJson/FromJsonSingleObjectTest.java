package com.manuege.boxfitapp.fromJson;

import com.manuege.boxfit.MainJsonSerializer;
import com.manuege.boxfitapp.AbstractObjectBoxTest;
import com.manuege.boxfitapp.model.Child;
import com.manuege.boxfitapp.model.Parent;
import com.manuege.boxfitapp.utils.JsonProvider;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Manu on 24/3/18.
 */

public class FromJsonSingleObjectTest extends AbstractObjectBoxTest {
    @Test
    public void fromJson_importNewObject() throws Exception {
        JSONObject object = JsonProvider.getJSONObject("parent.json");
        MainJsonSerializer mainSerializer = new MainJsonSerializer(boxStore);
        Parent parent = mainSerializer.fromJson(Parent.class, object);

        Assert.assertEquals(1, boxStore.boxFor(Parent.class).count());
        Assert.assertNotNull(parent);

        Assert.assertEquals(1, parent.id);
        Assert.assertEquals(2, (long) parent.longClassField);

        Assert.assertEquals(3, parent.integerField);
        Assert.assertEquals(4, (int) parent.integerClassField);

        Assert.assertEquals(true, parent.boolField);
        Assert.assertEquals(false, parent.boolClassField);

        Assert.assertEquals(5.5, parent.doubleField, 0);
        Assert.assertEquals(6.6, parent.doubleClassField, 0);

        Assert.assertEquals("hello", parent.stringField);
        Assert.assertEquals("inferred", parent.serializerNameInferred);
        Assert.assertEquals("keyPath", parent.keyPathField);
        Assert.assertEquals("fakeKeyPath", parent.fakeKeyPathField);

        Assert.assertEquals(1, parent.toOne.getTarget().id);
        Assert.assertEquals("one", parent.toOne.getTarget().value);

        Assert.assertEquals(2, parent.toMany.size());
        Assert.assertEquals(2, parent.toMany.get(0).id);
        Assert.assertEquals("two", parent.toMany.get(0).value);

        Assert.assertEquals(2, parent.list.size());
        Assert.assertEquals(4, parent.list.get(0).id);
        Assert.assertEquals("four", parent.list.get(0).value);

        Assert.assertEquals(Parent.Enum.ONE, parent.enumField);

        Assert.assertEquals(117, parent.dateField.getYear());
        Assert.assertEquals(8, parent.dateField.getMonth());
        Assert.assertEquals(17, parent.dateField.getDate());
    }

    @Test
    public void fromJson_updateExistingObject() throws Exception {
        MainJsonSerializer serializer = new MainJsonSerializer(boxStore);
        JSONObject first_json = JsonProvider.getJSONObject("parent_2.json");
        serializer.fromJson(Parent.class, first_json);

        JSONObject second_json = JsonProvider.getJSONObject("parent.json");
        serializer.fromJson(Parent.class, second_json);

        Parent parent = serializer.fromJson(Parent.class, second_json);

        Assert.assertEquals(1, boxStore.boxFor(Parent.class).count());
        Assert.assertNotNull(parent);

        Assert.assertEquals(1, parent.id);
        Assert.assertEquals(2, (long) parent.longClassField);

        Assert.assertEquals(3, parent.integerField);
        Assert.assertEquals(4, (int) parent.integerClassField);

        Assert.assertEquals(true, parent.boolField);
        Assert.assertEquals(false, parent.boolClassField);

        Assert.assertEquals(5.5, parent.doubleField, 0);
        Assert.assertEquals(6.6, parent.doubleClassField, 0);

        Assert.assertEquals("hello", parent.stringField);
        Assert.assertEquals("inferred", parent.serializerNameInferred);
        Assert.assertEquals("keyPath", parent.keyPathField);
        Assert.assertEquals("fakeKeyPath", parent.fakeKeyPathField);

        Assert.assertEquals(1, parent.toOne.getTarget().id);
        Assert.assertEquals("one", parent.toOne.getTarget().value);

        Assert.assertEquals(2, parent.toMany.size());
        Assert.assertEquals(2, parent.toMany.get(0).id);
        Assert.assertEquals("two", parent.toMany.get(0).value);

        Assert.assertEquals(2, parent.list.size());
        Assert.assertEquals(4, parent.list.get(0).id);
        Assert.assertEquals("four", parent.list.get(0).value);

        Assert.assertEquals(Parent.Enum.ONE, parent.enumField);

        Assert.assertEquals(117, parent.dateField.getYear());
        Assert.assertEquals(8, parent.dateField.getMonth());
        Assert.assertEquals(17, parent.dateField.getDate());
    }

    @Test
    public void fromJson_partialUpdateExistingObject() throws Exception {
        MainJsonSerializer serializer = new MainJsonSerializer(boxStore);
        JSONObject first_json = JsonProvider.getJSONObject("parent.json");
        serializer.fromJson(Parent.class, first_json);

        JSONObject second_json = JsonProvider.getJSONObject("partial_parent.json");
        Parent parent = serializer.fromJson(Parent.class, second_json);

        Assert.assertEquals(1, boxStore.boxFor(Parent.class).count());
        Assert.assertNotNull(parent);

        Assert.assertEquals(1, parent.id);
        Assert.assertEquals(20, (long) parent.longClassField);

        Assert.assertEquals(30, parent.integerField);
        Assert.assertEquals(4, (int) parent.integerClassField);

        Assert.assertEquals(false, parent.boolField);
        Assert.assertEquals(false, parent.boolClassField);

        Assert.assertEquals(5.5, parent.doubleField, 0);
        Assert.assertEquals(60.6, parent.doubleClassField, 0);

        Assert.assertEquals("bye", parent.stringField);
        Assert.assertEquals("inferred", parent.serializerNameInferred);
        Assert.assertEquals("keyPath", parent.keyPathField);
        Assert.assertEquals("fakeKeyPath", parent.fakeKeyPathField);

        Assert.assertEquals(1, parent.toOne.getTarget().id);
        Assert.assertEquals("one", parent.toOne.getTarget().value);

        Assert.assertEquals(2, parent.toMany.size());
        Assert.assertEquals(2, parent.toMany.get(0).id);
        Assert.assertEquals("two", parent.toMany.get(0).value);

        Assert.assertEquals(2, parent.list.size());
        Assert.assertEquals(4, parent.list.get(0).id);
        Assert.assertEquals("four", parent.list.get(0).value);

        Assert.assertEquals(Parent.Enum.ONE, parent.enumField);

        Assert.assertEquals(117, parent.dateField.getYear());
        Assert.assertEquals(8, parent.dateField.getMonth());
        Assert.assertEquals(17, parent.dateField.getDate());
    }

    @Test
    public void fromJson_relationshipWithIds() throws Exception {
        MainJsonSerializer serializer = new MainJsonSerializer(boxStore);
        JSONObject json = JsonProvider.getJSONObject("parent_with_properties_with_id.json");
        Parent parent = serializer.fromJson(Parent.class, json);

        Assert.assertEquals(1, parent.toOne.getTarget().id);
        Assert.assertNull(parent.toOne.getTarget().value);

        Assert.assertEquals(2, parent.toMany.size());
        Assert.assertEquals(2, parent.toMany.get(0).id);
        Assert.assertNull(parent.toMany.get(0).value);
        Assert.assertEquals(3, parent.toMany.get(1).id);
        Assert.assertNull(parent.toMany.get(1).value);

        Assert.assertEquals(3, parent.list.size());
        Assert.assertEquals(4, parent.list.get(0).id);
        Assert.assertNull(parent.list.get(0).value);
        Assert.assertEquals(5, parent.list.get(1).id);
        Assert.assertNull(parent.list.get(1).value);
        Assert.assertEquals(6, parent.list.get(2).id);
        Assert.assertNull(parent.list.get(2).value);
    }

    @Test
    public void fromJson_relationshipWithIdsAndExistingObjects() throws Exception {
        boxStore.boxFor(Child.class).put(
                new Child(1, "one"),
                new Child(2, "two"),
                new Child(3, "three"),
                new Child(4, "four"),
                new Child(5, "five"),
                new Child(6, "six")
        );

        MainJsonSerializer serializer = new MainJsonSerializer(boxStore);
        JSONObject json = JsonProvider.getJSONObject("parent_with_properties_with_id.json");
        Parent parent = serializer.fromJson(Parent.class, json);

        Assert.assertEquals(1, parent.toOne.getTarget().id);
        Assert.assertEquals("one", parent.toOne.getTarget().value);

        Assert.assertEquals(2, parent.toMany.size());
        Assert.assertEquals(2, parent.toMany.get(0).id);
        Assert.assertEquals("two", parent.toMany.get(0).value);
        Assert.assertEquals(3, parent.toMany.get(1).id);
        Assert.assertEquals("three", parent.toMany.get(1).value);

        Assert.assertEquals(3, parent.list.size());
        Assert.assertEquals(4, parent.list.get(0).id);
        Assert.assertEquals("four", parent.list.get(0).value);
        Assert.assertEquals(5, parent.list.get(1).id);
        Assert.assertEquals("five", parent.list.get(1).value);
        Assert.assertEquals(6, parent.list.get(2).id);
        Assert.assertEquals("six", parent.list.get(2).value);
    }

    @Test
    public void fromJson_canAutoConvertStringInNumbersAndViceVersa() throws Exception {
        MainJsonSerializer serializer = new MainJsonSerializer(boxStore);
        JSONObject json = JsonProvider.getJSONObject("parent.json");
        json.put("integer", "1");
        json.put("string", 2);
        Parent parent = serializer.fromJson(Parent.class, json);

        Assert.assertEquals(1, parent.integerField);
        Assert.assertEquals("2", parent.stringField);
    }

    @Test
    public void albumSerializer_overridesNullProperties() throws Exception {

        Parent existingParent = new Parent();
        boxStore.boxFor(Parent.class).put(existingParent);

        Child one = new Child(1, "one");
        Child two = new Child(2, "two");
        Child three = new Child(3, "three");
        Child four = new Child(4, "four");
        Child five = new Child(5, "five");
        boxStore.boxFor(Child.class).put(one, two, three, four, five);

        existingParent.id = 1;
        existingParent.integerClassField = 1;
        existingParent.stringField = "hello";
        existingParent.toOne.setTarget(one);
        existingParent.toMany.add(one);
        existingParent.toMany.add(two);
        existingParent.toMany.add(three);
        existingParent.list.add(four);
        existingParent.list.add(five);

        MainJsonSerializer serializer = new MainJsonSerializer(boxStore);
        JSONObject json = new JSONObject();
        json.put("id", 1);
        json.put("integerClass", null);
        json.put("string", null);
        json.put("toOne", null);
        json.put("toMany", null);
        json.put("list", null);

        serializer.fromJson(Parent.class, json);

        Parent parent = serializer.fromJson(Parent.class, json);
        Assert.assertNull(parent.integerClassField);
        Assert.assertNull(parent.stringField);
        Assert.assertNull(parent.toOne.getTarget());
        Assert.assertEquals(0, parent.toMany.size());
        Assert.assertEquals(0, parent.list.size());
    }

    @Test
    public void fromJson_serializeObjectThatNeedsConversion() throws Exception {
        MainJsonSerializer serializer = new MainJsonSerializer(boxStore);
        JSONObject json = JsonProvider.getJSONObject("parent.json");
        json.put("_id", 1);
        json.remove("id");
        Parent parent = serializer.fromJson(Parent.class, json);
        Assert.assertEquals(1, parent.id);
        Assert.assertEquals("hello", parent.stringField);
    }
}
