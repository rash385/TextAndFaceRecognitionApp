package com.kosi0917.textandfacerecognitionapp.Model.VK;

/**
 * Created by mozil on 28.01.2018.
 */

import com.vk.sdk.api.model.Identifiable;

public interface Owner extends Identifiable{

    String getFullName();
    String getPhoto();
}