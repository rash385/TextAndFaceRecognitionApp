package com.kosi0917.textandfacerecognitionapp.Model.VK.attachment.video;

import io.realm.RealmObject;

/**
 * Created by mozil on 20.03.2018.
 */

public class File extends RealmObject {
    public String external;

    public String getExternal() {
        return external;
    }

    public void setExternal(String external) {
        this.external = external;
    }
}