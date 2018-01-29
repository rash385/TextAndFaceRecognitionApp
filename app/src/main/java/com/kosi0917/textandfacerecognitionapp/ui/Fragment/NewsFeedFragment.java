package com.kosi0917.textandfacerecognitionapp.ui.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.kosi0917.textandfacerecognitionapp.Application.Application;
import com.kosi0917.textandfacerecognitionapp.Common.utils.VkListHelper;
import com.kosi0917.textandfacerecognitionapp.Model.VK.WallItem;
import com.kosi0917.textandfacerecognitionapp.Model.view.BaseViewModel;
import com.kosi0917.textandfacerecognitionapp.Model.view.NewsItemBodyViewModel;
import com.kosi0917.textandfacerecognitionapp.Model.view.NewsItemFooterViewModel;
import com.kosi0917.textandfacerecognitionapp.Model.view.NewsItemHeaderViewModel;
import com.kosi0917.textandfacerecognitionapp.R;
import com.kosi0917.textandfacerecognitionapp.rest.api.WallApi;
import com.kosi0917.textandfacerecognitionapp.rest.model.request.WallGetRequestModel;
import com.kosi0917.textandfacerecognitionapp.rest.model.response.GetWallResponse;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by vibo0917 on 1/25/2018.
 */

public class NewsFeedFragment extends BaseFeedFragment {

    @Inject
    WallApi mWallApi;



    public NewsFeedFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Application.getApplicationComponent().inject(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mWallApi.get(new WallGetRequestModel(-72495085).toMap())
                .flatMap(full -> Observable.fromIterable(VkListHelper.getWallList(full.response)))
                .flatMap(wallItem -> {
                    List<BaseViewModel> baseItems = new ArrayList<>();
                    baseItems.add(new NewsItemHeaderViewModel(wallItem));
                    baseItems.add(new NewsItemBodyViewModel(wallItem));
                    baseItems.add(new NewsItemFooterViewModel(wallItem));
                    return Observable.fromIterable(baseItems);
                })
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(objects -> mAdapter.addItems(objects));
    }

    @Override
    public int onCreateToolbarTitle() {
        return R.string.screen_name_news;
    }
}


