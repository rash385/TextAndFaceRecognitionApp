package com.kosi0917.textandfacerecognitionapp.mvp.view;

import com.arellomobile.mvp.MvpView;

/**
 * Created by vibo0917 on 1/24/2018.
 */

public interface MainView extends MvpView {
    void startSignIn();
    void signedIn();
}