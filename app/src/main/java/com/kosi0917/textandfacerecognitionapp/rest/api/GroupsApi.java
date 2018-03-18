package com.kosi0917.textandfacerecognitionapp.rest.api;

import com.kosi0917.textandfacerecognitionapp.Model.VK.Group;
import com.kosi0917.textandfacerecognitionapp.Model.VK.Member;
import com.kosi0917.textandfacerecognitionapp.rest.model.response.BaseItemResponse;
import com.kosi0917.textandfacerecognitionapp.rest.model.response.Full;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by mozil on 18.03.2018.
 */

public interface GroupsApi {

    @GET(ApiMethods.GROUPS_GET_MEMBERS)
    Observable<Full<BaseItemResponse<Member>>> getMembers(@QueryMap Map<String, String> map);

    @GET(ApiMethods.GROUPS_GET_BY_ID)
    Observable<Full<List<Group>>> getById(@QueryMap Map<String, String> map);

}