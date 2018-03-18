package com.kosi0917.textandfacerecognitionapp.di.component;

import com.kosi0917.textandfacerecognitionapp.Common.manager.NetworkManager;
import com.kosi0917.textandfacerecognitionapp.di.module.ApplicationModule;
import com.kosi0917.textandfacerecognitionapp.di.module.ManagerModule;
import com.kosi0917.textandfacerecognitionapp.di.module.RestModule;
import com.kosi0917.textandfacerecognitionapp.mvp.presenter.BoardPresenter;
import com.kosi0917.textandfacerecognitionapp.mvp.presenter.InfoPresenter;
import com.kosi0917.textandfacerecognitionapp.mvp.presenter.MainPresenter;
import com.kosi0917.textandfacerecognitionapp.mvp.presenter.MembersPresenter;
import com.kosi0917.textandfacerecognitionapp.mvp.presenter.NewsFeedPresenter;
import com.kosi0917.textandfacerecognitionapp.ui.Activity.BaseActivity;
import com.kosi0917.textandfacerecognitionapp.ui.Activity.VKLoginActivity;
import com.kosi0917.textandfacerecognitionapp.ui.Fragment.NewsFeedFragment;
import com.kosi0917.textandfacerecognitionapp.ui.holder.NewsItemBodyHolder;
import com.kosi0917.textandfacerecognitionapp.ui.holder.NewsItemFooterHolder;


import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by vibo0917 on 1/25/2018.
 */

@Singleton
@Component(modules = {ApplicationModule.class, ManagerModule.class, RestModule.class})
public interface ApplicationComponent {

    void inject(BaseActivity baseActivity);
    void inject(VKLoginActivity vkLoginActivity);

    void inject(NewsFeedFragment fragment);

    //holders
    void inject(NewsItemBodyHolder holder);
    void inject(NewsItemFooterHolder holder);

    //presenters
    void inject(NewsFeedPresenter presenter);

    //managers
    void inject(NetworkManager manager);

    void inject(MainPresenter presenter);

    void inject(MembersPresenter presenter);

    void inject(BoardPresenter presenter);

    void inject(InfoPresenter presenter);
}
