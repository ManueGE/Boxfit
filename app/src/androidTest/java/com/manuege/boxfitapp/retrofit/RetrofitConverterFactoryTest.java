package com.manuege.boxfitapp.retrofit;

import com.manuege.boxfitapp.AbstractRetrofitTest;
import com.manuege.boxfitapp.model.java.Parent;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;

/**
 * Created by Manu on 11/3/18.
 */

public class RetrofitConverterFactoryTest extends AbstractRetrofitTest {
    // https://riggaroo.co.za/retrofit-2-mocking-http-responses/

    @Test
    public void canSerializeModel() throws IOException {
        mockResponse(200, "parent.json");

        Response<Parent> response = getService().getModelDetail().execute();

        Assert.assertTrue(response.isSuccessful());
        Parent parent = response.body();

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
    }

    @Test
    public void canSerializeListOfModel() throws IOException {
        mockResponse(200, "parents_list.json");

        Response<List<Parent>> response = getService().getModelList().execute();

        Assert.assertTrue(response.isSuccessful());
        List<Parent> list = response.body();

        Assert.assertEquals(list.size(), 2);

        Parent parent = list.get(0);

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

        Assert.assertEquals(1, parent.toOne.getTarget().id);
        Assert.assertEquals("one", parent.toOne.getTarget().value);

        Assert.assertEquals(2, parent.toMany.size());
        Assert.assertEquals(2, parent.toMany.get(0).id);
        Assert.assertEquals("two", parent.toMany.get(0).value);

        Assert.assertEquals(2, parent.list.size());
        Assert.assertEquals(4, parent.list.get(0).id);
        Assert.assertEquals("four", parent.list.get(0).value);

        Assert.assertEquals(2, list.get(1).id);
    }
}
