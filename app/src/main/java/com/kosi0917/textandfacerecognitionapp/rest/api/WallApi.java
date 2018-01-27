package com.kosi0917.textandfacerecognitionapp.rest.api;

import com.kosi0917.textandfacerecognitionapp.rest.model.response.WallGetResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by vibo0917 on 1/25/2018.
 */

public interface WallApi {

    @GET(ApiMethods.WALL_GET)
    Call<WallGetResponse> get(@QueryMap Map<String, String> map);

}
