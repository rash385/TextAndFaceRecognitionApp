package com.kosi0917.textandfacerecognitionapp.Model.facebook;

import java.util.List;

/**
 * Created by kosi0917 on 07-Dec-17.
 */

public class RootImgFeed {
    public List<DatFeed> data;

    public RootImgFeed(List<DatFeed> data) {
        this.data = data;
    }

    public List<DatFeed> getData() {
        return data;
    }

    public void setData(List<DatFeed> data) {
        this.data = data;
    }
}
