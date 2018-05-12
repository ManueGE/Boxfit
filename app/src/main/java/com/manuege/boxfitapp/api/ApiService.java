package com.manuege.boxfitapp.api;

import com.manuege.boxfitapp.model.java.Parent;
import com.manuege.boxfitapp.model.kotlin.KtParent;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Manu on 11/3/18.
 */

public interface ApiService {
    @GET("model")
    Call<List<Parent>> getModelList();

    @GET("model/1")
    Call<Parent> getModelDetail();

    @GET("model")
    Call<List<KtParent>> getKtModelList();

    @GET("model/1")
    Call<KtParent> getKtModelDetail();
}
