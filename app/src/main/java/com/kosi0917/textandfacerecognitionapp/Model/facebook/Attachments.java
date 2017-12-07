package com.kosi0917.textandfacerecognitionapp.Model.facebook;

import java.util.List;

/**
 * Created by kosi0917 on 07-Dec-17.
 */

public class Attachments {
    public List<DatFeed2> data;

    public Attachments(List<DatFeed2> data) {
        this.data = data;
    }

    public List<DatFeed2> getData() {
        return data;
    }

    public void setData(List<DatFeed2> data) {
        this.data = data;
    }
}
