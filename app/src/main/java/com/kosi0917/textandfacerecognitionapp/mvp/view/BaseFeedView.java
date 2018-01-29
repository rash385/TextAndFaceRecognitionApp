package com.kosi0917.textandfacerecognitionapp.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.kosi0917.textandfacerecognitionapp.Model.view.BaseViewModel;

import java.util.List;

/**
 * Created by vibo0917 on 1/29/2018.
 */

public interface BaseFeedView extends MvpView {
    void showRefreshing();

    void hideRefreshing();


    void showListProgress();

    void hideListProgress();


    void showError(String message);


    void setItems(List<BaseViewModel> items);

    void addItems(List<BaseViewModel> items);
}