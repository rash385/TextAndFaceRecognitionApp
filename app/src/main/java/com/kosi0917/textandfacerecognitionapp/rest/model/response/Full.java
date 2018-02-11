package com.kosi0917.textandfacerecognitionapp.rest.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by vibo0917 on 1/25/2018.
 */

public class Full<T> {
    @SerializedName("response")
    @Expose
    public T response;
}
