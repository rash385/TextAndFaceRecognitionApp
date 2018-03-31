package com.kosi0917.textandfacerecognitionapp.rest.api;

import com.kosi0917.textandfacerecognitionapp.Model.VK.CommentItem;
import com.kosi0917.textandfacerecognitionapp.Model.VK.Topic;
import com.kosi0917.textandfacerecognitionapp.rest.model.response.BaseItemResponse;
import com.kosi0917.textandfacerecognitionapp.rest.model.response.Full;
import com.kosi0917.textandfacerecognitionapp.rest.model.response.ItemWithSendersResponse;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by mozil on 18.03.2018.
 */

public interface BoardApi {
    @GET(ApiMethods.BOARD_GET_TOPICS)
    Observable<Full<BaseItemResponse<Topic>>> getTopics(@QueryMap Map<String, String> map);

    @GET(ApiMethods.BOARD_GET_COMMENTS)
    Observable<Full<ItemWithSendersResponse<CommentItem>>> getComments(@QueryMap Map<String, String> map);
}
