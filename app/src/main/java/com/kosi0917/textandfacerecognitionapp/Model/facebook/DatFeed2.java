package com.kosi0917.textandfacerecognitionapp.Model.facebook;

/**
 * Created by kosi0917 on 07-Dec-17.
 */

public class DatFeed2 {
    public String description;
    public AttachMedia media;
    public AttachTarget target;
    public String type;
    public String url;

    public DatFeed2(String description, AttachMedia media, AttachTarget target, String type, String url) {
        this.description = description;
        this.media = media;
        this.target = target;
        this.type = type;
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AttachMedia getMedia() {
        return media;
    }

    public void setMedia(AttachMedia media) {
        this.media = media;
    }

    public AttachTarget getTarget() {
        return target;
    }

    public void setTarget(AttachTarget target) {
        this.target = target;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
