package com.kosi0917.textandfacerecognitionapp.Model.VK.attachment.doc;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by mozil on 20.03.2018.
 */

public class PhotoPreview extends RealmObject {
    RealmList<Size> sizes;

    public RealmList<Size> getSizes() {
        return sizes;
    }

    public void setSizes(RealmList<Size> sizes) {
        this.sizes = sizes;
    }
}
