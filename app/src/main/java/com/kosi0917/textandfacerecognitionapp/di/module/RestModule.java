package com.kosi0917.textandfacerecognitionapp.di.module;

import com.kosi0917.textandfacerecognitionapp.rest.RestClient;
import com.kosi0917.textandfacerecognitionapp.rest.api.GroupsApi;
import com.kosi0917.textandfacerecognitionapp.rest.api.UsersApi;
import com.kosi0917.textandfacerecognitionapp.rest.api.WallApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by vibo0917 on 1/25/2018.
 */

@Module
public class RestModule {
    private RestClient mRestClient;


    public RestModule() {
        mRestClient = new RestClient();
    }


    @Provides
    @Singleton
    public RestClient provideRestClient() {
        return mRestClient;
    }

    @Provides
    @Singleton
    public WallApi provideWallApi() {
        return mRestClient.createService(WallApi.class);
    }

    @Provides
    @Singleton
    public UsersApi provideUsersApi() {
        return mRestClient.createService(UsersApi.class);
    }

    @Provides
    @Singleton
    public GroupsApi provideGroupsApi() {
        return mRestClient.createService(GroupsApi.class);
    }
}
