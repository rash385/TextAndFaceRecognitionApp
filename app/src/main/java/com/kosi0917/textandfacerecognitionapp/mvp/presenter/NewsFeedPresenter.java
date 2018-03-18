package com.kosi0917.textandfacerecognitionapp.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.kosi0917.textandfacerecognitionapp.Application.Application;
import com.kosi0917.textandfacerecognitionapp.Common.utils.VkListHelper;
import com.kosi0917.textandfacerecognitionapp.Model.VK.CurrentUser;
import com.kosi0917.textandfacerecognitionapp.Model.VK.WallItem;
import com.kosi0917.textandfacerecognitionapp.Model.view.BaseViewModel;
import com.kosi0917.textandfacerecognitionapp.Model.view.NewsItemBodyViewModel;
import com.kosi0917.textandfacerecognitionapp.Model.view.NewsItemFooterViewModel;
import com.kosi0917.textandfacerecognitionapp.Model.view.NewsItemHeaderViewModel;
import com.kosi0917.textandfacerecognitionapp.consts.ApiConstants;
import com.kosi0917.textandfacerecognitionapp.mvp.view.BaseFeedView;
import com.kosi0917.textandfacerecognitionapp.rest.api.WallApi;
import com.kosi0917.textandfacerecognitionapp.rest.model.request.WallGetRequestModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by vibo0917 on 1/29/2018.
 */

@InjectViewState
public class NewsFeedPresenter extends BaseFeedPresenter<BaseFeedView> {

    private boolean enableIdFiltering = false;

    @Inject
    WallApi mWallApi;


    public NewsFeedPresenter() {
        Application.getApplicationComponent().inject(this);
    }

    @Override
    public Observable<BaseViewModel> onCreateLoadDataObservable(int count, int offset) {
        return mWallApi.get(new WallGetRequestModel(ApiConstants.MY_GROUP_ID, count, offset).toMap())
                .flatMap(full -> Observable.fromIterable(VkListHelper.getWallList(full.response)))
                .compose(applyFilter())
                .doOnNext(this::saveToDb)
                .flatMap(wallItem -> {
                    List<BaseViewModel> baseItems = new ArrayList<>();
                    baseItems.add(new NewsItemHeaderViewModel(wallItem));
                    baseItems.add(new NewsItemBodyViewModel(wallItem));
                    baseItems.add(new NewsItemFooterViewModel(wallItem));
                    return Observable.fromIterable(baseItems);
                });
    }

    @Override
    public Observable<BaseViewModel> onCreateRestoreDataObservable() {
        return Observable.fromCallable(getListFromRealmCallable())
                .flatMap(Observable::fromIterable)
                .compose(applyFilter())
                .flatMap(wallItem -> Observable.fromIterable(parsePojoModel(wallItem)));
    }

    private List<BaseViewModel> parsePojoModel(WallItem wallItem) {
        List<BaseViewModel> baseItems = new ArrayList<>();
        baseItems.add(new NewsItemHeaderViewModel(wallItem));
        baseItems.add(new NewsItemBodyViewModel(wallItem));
        baseItems.add(new NewsItemFooterViewModel(wallItem));
        return baseItems;
    }

    public Callable<List<WallItem>> getListFromRealmCallable() {
        return () -> {
            String[] sortFields = {"date"};
            Sort[] sortOrder = {Sort.DESCENDING};
            Realm realm = Realm.getDefaultInstance();
            RealmResults<WallItem> realmResults = realm.where(WallItem.class)
                    .findAllSorted(sortFields, sortOrder);
            return realm.copyFromRealm(realmResults);
        };
    }

    public void setEnableIdFiltering(boolean enableIdFiltering) {
        this.enableIdFiltering = enableIdFiltering;
    }

    protected ObservableTransformer<WallItem, WallItem> applyFilter() {
        if (enableIdFiltering && CurrentUser.getId() != null) {
            return baseItemObservable -> baseItemObservable.
                    filter(wallItem -> CurrentUser.getId().equals(String.valueOf(wallItem.getFromId())));
        } else {
            return baseItemObservable -> baseItemObservable;
        }
    }
}


//     -72495085








