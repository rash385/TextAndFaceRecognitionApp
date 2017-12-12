package com.kosi0917.textandfacerecognitionapp.InstagramLib;

import com.kosi0917.textandfacerecognitionapp.Interface.RetrofitService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sivko on 12.12.2017.
 */
    public class RestClient {

        public static RetrofitService getRetrofitService() {
            return new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(RetrofitService.class);
        }
    }
