package com.kosi0917.textandfacerecognitionapp.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.kosi0917.textandfacerecognitionapp.Application.Application;
import com.kosi0917.textandfacerecognitionapp.Model.VK.Member;
import com.kosi0917.textandfacerecognitionapp.Model.view.BaseViewModel;
import com.kosi0917.textandfacerecognitionapp.Model.view.MemberViewModel;
import com.kosi0917.textandfacerecognitionapp.consts.ApiConstants;
import com.kosi0917.textandfacerecognitionapp.mvp.view.BaseFeedView;
import com.kosi0917.textandfacerecognitionapp.rest.api.GroupsApi;
import com.kosi0917.textandfacerecognitionapp.rest.model.request.GroupsGetMembersRequestModel;

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
public class MembersPresenter extends BaseFeedPresenter<BaseFeedView> {
    @Inject
    GroupsApi mGroupApi;

    public MembersPresenter() {
        Application.getApplicationComponent().inject(this);
    }

    @Override
    public Observable<BaseViewModel> onCreateLoadDataObservable(int count, int offset) {
        return mGroupApi.getMembers(new GroupsGetMembersRequestModel(
                ApiConstants.MY_GROUP_ID, count, offset).toMap())
                .flatMap(baseItemResponseFull -> {
                    return Observable.fromIterable(baseItemResponseFull.response.getItems());
                })
                .doOnNext(member -> saveToDb(member))
                .map(member -> new MemberViewModel(member));
    }

    @Override
    public Observable<BaseViewModel> onCreateRestoreDataObservable() {
        return Observable.fromCallable(getListFromRealmCallable())
                .flatMap(Observable::fromIterable)
                .map(member -> new MemberViewModel(member));
    }



    public Callable<List<Member>> getListFromRealmCallable() {
        return () -> {
            String[] sortFields = {Member.ID};
            Sort[] sortOrder = {Sort.ASCENDING};

            Realm realm = Realm.getDefaultInstance();
            RealmResults<Member> results = realm.where(Member.class)
                    .findAllSorted(sortFields, sortOrder);
            return realm.copyFromRealm(results);
        };
    }
}
