package com.kosi0917.textandfacerecognitionapp.Model.facebook;

/**
 * Created by kosi0917 on 07-Dec-17.
 */

public class AttachMedia {
    public AttachImage image;

    public AttachMedia(AttachImage image) {
        this.image = image;
    }

    public AttachImage getImage() {
        return image;
    }

    public void setImage(AttachImage image) {
        this.image = image;
    }
}
