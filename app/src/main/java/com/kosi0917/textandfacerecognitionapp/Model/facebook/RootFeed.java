package com.kosi0917.textandfacerecognitionapp.Model.facebook;

import com.kosi0917.textandfacerecognitionapp.Model.facebook.Datum;

import java.util.List;

/**
 * Created by kosi0917 on 06-Dec-17.
 */

public class RootFeed {
    public List<Datum> data;

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public RootFeed(List<Datum> data) {

        this.data = data;
    }
}
