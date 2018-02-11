package com.kosi0917.textandfacerecognitionapp.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.kosi0917.textandfacerecognitionapp.Model.VK.Profile;

/**
 * Created by vibo0917 on 1/24/2018.
 */

public interface MainView extends MvpView {
    void startSignIn();
    void signedIn();

    void showCurrentUser(Profile profile);
}