package com.kosi0917.textandfacerecognitionapp.consts;

import com.vk.sdk.VKScope;

/**
 * Created by vibo0917 on 1/26/2018.
 */

public class ApiConstants {

    public static String[] scope = new String[] {VKScope.WALL, VKScope.FRIENDS, VKScope.MESSAGES, VKScope.PHOTOS};
    public static final Double DEFAULT_VERSION = 5.67;
    public static final int DEFAULT_COUNT = 10;

    public static final String DEFAULT_USER_FIELDS = "photo_100";

    public static final String[] DEFAULT_LOGIN_SCOPE = {VKScope.EMAIL};

}
