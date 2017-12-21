package com.kosi0917.textandfacerecognitionapp.Application;

import android.content.Intent;

import com.kosi0917.textandfacerecognitionapp.FBActivities.FacebookLoginActivity;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKAccessTokenTracker;
import com.vk.sdk.VKSdk;

/**
 * Created by Lawrence on 07.12.2017.
 */

public class Application extends android.app.Application {

    VKAccessTokenTracker vkAccessTokenTracker = new VKAccessTokenTracker() {
        @Override
        public void onVKAccessTokenChanged(VKAccessToken oldToken, VKAccessToken newToken) {
            if (newToken == null) {
            // VKAccessToken is invalid
                Intent intent = new Intent(Application.this, FacebookLoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        VKSdk.initialize(this);
        vkAccessTokenTracker.startTracking();

    }
}
