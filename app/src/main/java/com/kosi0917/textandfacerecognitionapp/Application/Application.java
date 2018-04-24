package com.kosi0917.textandfacerecognitionapp.Application;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.kosi0917.textandfacerecognitionapp.fcm.MyPreferencesManager;
import com.kosi0917.textandfacerecognitionapp.ui.activity.FBActivities.FacebookLoginActivity;
import com.kosi0917.textandfacerecognitionapp.di.component.ApplicationComponent;
import com.kosi0917.textandfacerecognitionapp.di.component.DaggerApplicationComponent;
import com.kosi0917.textandfacerecognitionapp.di.module.ApplicationModule;
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKAccessTokenTracker;
import com.vk.sdk.VKSdk;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Lawrence on 07.12.2017.
 */

public class Application extends android.app.Application {

    private static ApplicationComponent sApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        initComponent();

        VKSdk.initialize(this);

        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);

        DrawerImageLoader.init(new AbstractDrawerImageLoader() {
            @Override
            public void set(ImageView imageView, Uri uri, Drawable placeholder, String tag) {
                Glide.with(imageView.getContext()).load(uri).into(imageView);
            }
        });

        MyPreferencesManager.getInstance().init(this);

    }

    private void initComponent() {
        sApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();
    }

    public static ApplicationComponent getApplicationComponent() {
        return sApplicationComponent;
    }
}
