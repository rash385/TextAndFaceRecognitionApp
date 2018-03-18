package com.kosi0917.textandfacerecognitionapp.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.kosi0917.textandfacerecognitionapp.Application.Application;
import com.kosi0917.textandfacerecognitionapp.Model.VK.Group;
import com.kosi0917.textandfacerecognitionapp.Model.view.BaseViewModel;
import com.kosi0917.textandfacerecognitionapp.Model.view.InfoContactsViewModel;
import com.kosi0917.textandfacerecognitionapp.Model.view.InfoLinksViewModel;
import com.kosi0917.textandfacerecognitionapp.Model.view.InfoStatusViewModel;
import com.kosi0917.textandfacerecognitionapp.consts.ApiConstants;
import com.kosi0917.textandfacerecognitionapp.mvp.view.BaseFeedView;
import com.kosi0917.textandfacerecognitionapp.rest.api.GroupsApi;
import com.kosi0917.textandfacerecognitionapp.rest.model.request.GroupsGetByIdRequestModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.realm.Realm;

/**
 * Created by mozil on 18.03.2018.
 */

@InjectViewState
public class InfoPresenter extends BaseFeedPresenter<BaseFeedView> {

    @Inject
    GroupsApi mGroupApi;

    public InfoPresenter() {
        Application.getApplicationComponent().inject(this);
    }

    @Override
    public Observable<BaseViewModel> onCreateLoadDataObservable(int count, int offset) {

        return mGroupApi.getById(new GroupsGetByIdRequestModel(ApiConstants.MY_GROUP_ID).toMap())
                .flatMap(listFull -> Observable.fromIterable(listFull.response))
                .doOnNext(this::saveToDb)
                .flatMap(group -> Observable.fromIterable(parsePojoModel(group)));
    }

    @Override
    public Observable<BaseViewModel> onCreateRestoreDataObservable() {
        return Observable.fromCallable(getListFromRealmCallable())
                .flatMap(group -> Observable.fromIterable(parsePojoModel(group)));
    }


    private List<BaseViewModel> parsePojoModel(Group group) {
        List<BaseViewModel> items = new ArrayList<>();
        items.add(new InfoStatusViewModel(group));
        items.add(new InfoContactsViewModel());
        items.add(new InfoLinksViewModel());

        return items;
    }


    public Callable<Group> getListFromRealmCallable() {
        return () -> {
            Realm realm = Realm.getDefaultInstance();
            Group result = realm.where(Group.class)
                    .equalTo("id", Math.abs(ApiConstants.MY_GROUP_ID))
                    .findFirst();
            return realm.copyFromRealm(result);
        };
    }
}
