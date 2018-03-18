package com.kosi0917.textandfacerecognitionapp.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.kosi0917.textandfacerecognitionapp.Application.Application;
import com.kosi0917.textandfacerecognitionapp.Model.VK.Member;
import com.kosi0917.textandfacerecognitionapp.Model.VK.Topic;
import com.kosi0917.textandfacerecognitionapp.Model.view.BaseViewModel;
import com.kosi0917.textandfacerecognitionapp.Model.view.TopicViewModel;
import com.kosi0917.textandfacerecognitionapp.consts.ApiConstants;
import com.kosi0917.textandfacerecognitionapp.mvp.view.BaseFeedView;
import com.kosi0917.textandfacerecognitionapp.rest.api.BoardApi;
import com.kosi0917.textandfacerecognitionapp.rest.model.request.BoardGetTopicsRequestModel;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by mozil on 18.03.2018.
 */

@InjectViewState
public class BoardPresenter extends BaseFeedPresenter<BaseFeedView> {
    @Inject
    BoardApi mBoardApi;

    public BoardPresenter() {
        Application.getApplicationComponent().inject(this);
    }

    @Override
    public Observable<BaseViewModel> onCreateLoadDataObservable(int count, int offset) {
        return mBoardApi.getTopics(new BoardGetTopicsRequestModel(
                ApiConstants.MY_GROUP_ID, count, offset).toMap())
                .flatMap(baseItemResponseFull -> Observable.fromIterable(baseItemResponseFull.response.getItems()))
                .doOnNext(topic -> topic.setGroupId(ApiConstants.MY_GROUP_ID))
                .doOnNext(this::saveToDb)
                .map(TopicViewModel::new);
    }

    @Override
    public Observable<BaseViewModel> onCreateRestoreDataObservable() {
        return Observable.fromCallable(getListFromRealmCallable())
                .flatMap(Observable::fromIterable)
                .map(TopicViewModel::new);
    }


    public Callable<List<Topic>> getListFromRealmCallable() {
        return () -> {
            String[] sortFields = {Member.ID};
            Sort[] sortOrder = {Sort.DESCENDING};

            Realm realm = Realm.getDefaultInstance();
            RealmResults<Topic> results = realm.where(Topic.class)
                    .equalTo("groupId", ApiConstants.MY_GROUP_ID)
                    .findAllSorted(sortFields, sortOrder);

            return realm.copyFromRealm(results);
        };
    }
}
