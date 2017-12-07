package com.kosi0917.textandfacerecognitionapp.Model.facebook;

/**
 * Created by kosi0917 on 07-Dec-17.
 */

public class AttachTarget {
    public String id;
    public String url;

    public AttachTarget(String id, String url) {
        this.id = id;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
