package com.kosi0917.textandfacerecognitionapp.rest.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.kosi0917.textandfacerecognitionapp.Model.VK.attachment.video.Video;

import java.util.List;

/**
 * Created by mozil on 20.03.2018.
 */

public class VideosResponse {
    public int count;
    @SerializedName("items")
    @Expose
    public List<Video> items;
}
