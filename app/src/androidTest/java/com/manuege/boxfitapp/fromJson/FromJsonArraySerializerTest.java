package com.manuege.boxfitapp.fromJson;

import com.manuege.boxfit.BoxfitSerializer;
import com.manuege.boxfit.utils.Json;
import com.manuege.boxfitapp.AbstractObjectBoxTest;
import com.manuege.boxfitapp.model.Child;
import com.manuege.boxfitapp.model.Parent;
import com.manuege.boxfitapp.utils.JsonProvider;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Manu on 28/1/18.
 */

public class FromJsonArraySerializerTest extends AbstractObjectBoxTest {
    @Test
    public void fromJsonArray_canSerialize() {
        JSONObject object = JsonProvider.getJSONObject("paginated_parent.json");
        JSONArray array = new Json(object).getJSONArray("results");
        BoxfitSerializer serializer = new BoxfitSerializer(boxStore);
        List<Parent> objects = serializer.fromJson(Parent.class, array);

        assertEquals(2, boxStore.boxFor(Parent.class).count());
        assertEquals(6, boxStore.boxFor(Child.class).count());

        Parent parent = objects.get(0);
        assertEquals("hello", parent.stringField);
    }
}
