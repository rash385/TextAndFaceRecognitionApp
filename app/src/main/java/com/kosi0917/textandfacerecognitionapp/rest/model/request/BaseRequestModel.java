package com.kosi0917.textandfacerecognitionapp.rest.model.request;

import com.google.gson.annotations.SerializedName;
import com.kosi0917.textandfacerecognitionapp.Model.VK.CurrentUser;
import com.kosi0917.textandfacerecognitionapp.consts.ApiConstants;
import com.vk.sdk.api.VKApiConst;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vibo0917 on 1/26/2018.
 */

public abstract class BaseRequestModel {

    @SerializedName(VKApiConst.VERSION)
    Double version = ApiConstants.DEFAULT_VERSION;

    @SerializedName(VKApiConst.ACCESS_TOKEN)
    String accessToken = CurrentUser.getAccessToken();

    private Double getVersion() {
        return version;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<>();
        map.put(VKApiConst.VERSION, String.valueOf(getVersion()));
        if (accessToken != null) {
            map.put(VKApiConst.ACCESS_TOKEN, getAccessToken());
        }

        onMapCreate(map);

        return map;
    }

    public abstract void onMapCreate(Map<String, String> map);
}