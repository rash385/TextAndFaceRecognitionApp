package com.kosi0917.textandfacerecognitionapp.Model.VK;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKSdk;

/**
 * Created by vibo0917 on 1/24/2018.
 */

public class CurrentUser {

    public static String getAccessToken() {
        if (VKAccessToken.currentToken() == null) {
            return null;
        }

        return VKAccessToken.currentToken().accessToken;
    }

    public static String getId() {
        if (VKAccessToken.currentToken() != null) {
            return VKAccessToken.currentToken().userId;
        }

        return null;
    }

    public static boolean isAuthorized() {
        return VKSdk.isLoggedIn()
                && VKAccessToken.currentToken() != null
                && !VKAccessToken.currentToken().isExpired();
    }

}