package com.manuege.boxfitapp.fromJson;

import com.manuege.boxfit.BoxfitSerializer;
import com.manuege.boxfitapp.AbstractObjectBoxTest;
import com.manuege.boxfitapp.model.java.Paginated;
import com.manuege.boxfitapp.model.java.PaginatedResponse;
import com.manuege.boxfitapp.model.java.Child;
import com.manuege.boxfitapp.model.java.Parent;
import com.manuege.boxfitapp.utils.JsonProvider;

import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Manu on 28/1/18.
 */

public class FromJsonArrayPaginatedResponseTest extends AbstractObjectBoxTest {
    @Test
    public void fromJsonPaginatedResponse_canSerialize() {
        JSONObject jsonObject = JsonProvider.getJSONObject("paginated_parent.json");
        BoxfitSerializer serializer = new BoxfitSerializer(boxStore);
        PaginatedResponse<Parent> object = serializer.fromJson(Paginated.Parents.class, jsonObject);

        assertEquals(10, object.getCount());
        assertEquals(0, object.getPrevious());
        assertEquals(2, object.getNext());

        assertEquals(2, boxStore.boxFor(Parent.class).count());
        assertEquals(6, boxStore.boxFor(Child.class).count());

        Parent parent = object.getResults().get(0);
        assertEquals("hello", parent.stringField);
    }

    @Test
    public void fromJsonPaginatedResponse_canSerializeSubclassOfGeneric() {
        JSONObject jsonObject = JsonProvider.getJSONObject("paginated_parent.json");
        BoxfitSerializer serializer = new BoxfitSerializer(boxStore);
        PaginatedResponse<Parent> object = serializer.fromJson(Paginated.ParentsSubclass.class, jsonObject);

        assertEquals(10, object.getCount());
        assertEquals(0, object.getPrevious());
        assertEquals(2, object.getNext());

        assertEquals(2, boxStore.boxFor(Parent.class).count());
        assertEquals(6, boxStore.boxFor(Child.class).count());

        Parent parent = object.getResults().get(0);
        assertEquals("hello", parent.stringField);
    }
}
