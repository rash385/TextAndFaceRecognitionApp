package com.kosi0917.textandfacerecognitionapp.di.module;

import com.kosi0917.textandfacerecognitionapp.Common.manager.MyFragmentManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by vibo0917 on 1/25/2018.
 */

@Module
public class ManagerModule {
    @Provides
    @Singleton
    MyFragmentManager provideMyFragmentManager() {
        return new MyFragmentManager();
    }
}