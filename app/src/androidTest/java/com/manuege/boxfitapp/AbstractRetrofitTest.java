package com.manuege.boxfitapp;

import com.manuege.boxfit.MainJsonSerializer;
import com.manuege.boxfitapp.api.ApiService;
import com.manuege.boxfitapp.utils.JsonProvider;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;

import java.io.IOException;

import retrofit2.Retrofit;

/**
 * Created by Manu on 11/3/18.
 */

public abstract class AbstractRetrofitTest extends AbstractObjectBoxTest {

    MockWebServer server;

    @Override
    public void setUp() throws IOException {
        super.setUp();
        server = new MockWebServer();
        server.start();
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        server.shutdown();
    }

    protected ApiService getService() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(MainJsonSerializer.getConverterFactory(boxStore))
                .baseUrl(server.getUrl("/").toString())
                .build();
        return retrofit.create(ApiService.class);
    }

    protected void mockResponse(int code, String fileResponse) {
        server.enqueue(new MockResponse()
                .setResponseCode(code)
                .setBody(JsonProvider.getResponse(fileResponse)));
    }
}
