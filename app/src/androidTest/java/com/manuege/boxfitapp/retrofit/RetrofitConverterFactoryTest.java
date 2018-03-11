package com.manuege.boxfitapp.retrofit;

import com.manuege.boxfitapp.AbstractRetrofitTest;
import com.manuege.boxfitapp.model.CoreModel;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

import retrofit2.Response;

/**
 * Created by Manu on 11/3/18.
 */

public class RetrofitConverterFactoryTest extends AbstractRetrofitTest {
    // https://riggaroo.co.za/retrofit-2-mocking-http-responses/

    @Test
    public void canSerializeModel() throws IOException {
        mockResponse(200, "core_model.json");

        Response<CoreModel> response = getService().getCoreModelDetail().execute();

        Assert.assertTrue(response.isSuccessful());
        CoreModel coreModel = response.body();

        Assert.assertEquals(1, coreModel.id);
        Assert.assertEquals(2, (long) coreModel.longClassField);

        Assert.assertEquals(3, coreModel.integerField);
        Assert.assertEquals(4, (int) coreModel.integerClassField);

        Assert.assertEquals(true, coreModel.boolField);
        Assert.assertEquals(false, coreModel.boolClassField);

        Assert.assertEquals(5.5, coreModel.doubleField, 0);
        Assert.assertEquals(6.6, coreModel.doubleClassField, 0);

        Assert.assertEquals(coreModel.stringField, "hello");
    }
}
