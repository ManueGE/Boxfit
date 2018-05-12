package com.manuege.boxfitapp.tojson;

import com.manuege.boxfit.BoxfitSerializer;
import com.manuege.boxfitapp.AbstractObjectBoxTest;
import com.manuege.boxfitapp.model.java.Child;
import com.manuege.boxfitapp.model.java.Parent;
import com.manuege.boxfitapp.model.java.ToJsonTestObject;
import com.manuege.boxfitapp.utils.JsonProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Manu on 18/3/18.
 */

public class ToJsonTest extends AbstractObjectBoxTest{

    @Test
    public void parentSerializer_toJson() throws JSONException {
        Parent parent = new Parent();
        boxStore.boxFor(Parent.class).put(parent);

        parent.id = 1;
        parent.longClassField = 2L;
        parent.integerField = 3;
        parent.integerClassField = 4;
        parent.boolField = true;
        parent.boolClassField = false;
        parent.doubleField = 5.5;
        parent.doubleClassField = 6.6;
        parent.stringField = "hello";
        parent.serializerNameInferred = "inferred";

        Child one = new Child(1, "one");
        Child two = new Child(2, "two");
        Child three = new Child(3, "three");
        Child four = new Child(4, "four");
        Child five = new Child(5, "five");
        boxStore.boxFor(Child.class).put(one, two, three, four, five);

        parent.keyPathField = "keyPath";
        parent.fakeKeyPathField = "fakeKeyPath";

        parent.toOne.setTarget(one);

        parent.toMany.add(two);
        parent.toMany.add(three);

        parent.list.add(four);
        parent.list.add(five);

        parent.enumField = Parent.Enum.ONE;

        Calendar calendar = Calendar.getInstance();
        calendar.set(2017, 8, 17);
        parent.dateField = calendar.getTime();

        BoxfitSerializer boxfitSerializer = new BoxfitSerializer(boxStore);
        JSONObject actual = boxfitSerializer.toJson(parent);
        JSONObject expected = JsonProvider.getJSONObject("parent_for_to_json.json");
        Assert.assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void parentSerializer_toJsonNullsIgnored() throws JSONException {
        Parent parent = new Parent();
        boxStore.boxFor(Parent.class).put(parent);

        parent.id = 1;
        parent.integerField = 3;
        parent.boolField = true;
        parent.doubleField = 5.5;

        JSONObject expected = new JSONObject();
        expected.put("id", 1);
        expected.put("integer", 3);
        expected.put("bool", true);
        expected.put("double", 5.5);
        expected.put("toMany", new JSONArray());
        expected.put("list", new JSONArray());

        BoxfitSerializer boxfitSerializer = new BoxfitSerializer(boxStore);
        JSONObject actual = boxfitSerializer.toJson(parent);
        Assert.assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void parentSerializer_toJsonIgnoreAndIncludeNull() throws JSONException {
        ToJsonTestObject object = new ToJsonTestObject();
        object.ignoredField = "hello";
        boxStore.boxFor(ToJsonTestObject.class).put(object);

        JSONObject expected = new JSONObject();
        expected.put("long_class", JSONObject.NULL);
        expected.put("integer_class", JSONObject.NULL);
        expected.put("bool_class", JSONObject.NULL);
        expected.put("double_class", JSONObject.NULL);
        expected.put("string", JSONObject.NULL);
        expected.put("toOne", JSONObject.NULL);

        BoxfitSerializer boxfitSerializer = new BoxfitSerializer(boxStore);
        JSONObject actual = boxfitSerializer.toJson(object);
        Assert.assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void parentSerializer_toJsonList() throws JSONException {
        Parent parent1 = new Parent();
        boxStore.boxFor(Parent.class).put(parent1);

        parent1.id = 1;
        parent1.stringField = "hello";

        Parent parent2 = new Parent();
        boxStore.boxFor(Parent.class).put(parent2);

        parent2.id = 2;
        parent2.stringField = "world";

        ArrayList<Parent> list = new ArrayList<>();
        list.add(parent1);
        list.add(parent2);

        BoxfitSerializer boxfitSerializer = new BoxfitSerializer(boxStore);
        JSONArray actual = boxfitSerializer.toJson(list);

        JSONObject object1 = new JSONObject();
        object1.put("id", 1);
        object1.put("integer", 0);
        object1.put("bool", false);
        object1.put("double", 0);
        object1.put("string", "hello");
        object1.put("toMany", new JSONArray());
        object1.put("list", new JSONArray());

        JSONObject object2 = new JSONObject();
        object2.put("id", 2);
        object2.put("integer", 0);
        object2.put("bool", false);
        object2.put("double", 0);
        object2.put("string", "world");
        object2.put("toMany", new JSONArray());
        object2.put("list", new JSONArray());

        JSONArray expected = new JSONArray();
        expected.put(object1);
        expected.put(object2);

        Assert.assertEquals(expected.toString(), actual.toString());
    }
}
