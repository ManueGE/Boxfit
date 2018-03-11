package com.manuege.boxfitapp.api;

import com.manuege.boxfitapp.model.CoreModel;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Manu on 11/3/18.
 */

public interface ApiService {
    @GET("model")
    Call<CoreModel> getCoreModelDetail();
}
