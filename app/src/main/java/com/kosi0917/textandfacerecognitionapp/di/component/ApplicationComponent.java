package com.kosi0917.textandfacerecognitionapp.di.component;

import com.kosi0917.textandfacerecognitionapp.di.module.ApplicationModule;
import com.kosi0917.textandfacerecognitionapp.di.module.ManagerModule;
import com.kosi0917.textandfacerecognitionapp.di.module.RestModule;
import com.kosi0917.textandfacerecognitionapp.ui.Activity.BaseActivity;
import com.kosi0917.textandfacerecognitionapp.ui.Activity.VKLoginActivity;
import com.kosi0917.textandfacerecognitionapp.ui.Fragment.NewsFeedFragment;


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

}
