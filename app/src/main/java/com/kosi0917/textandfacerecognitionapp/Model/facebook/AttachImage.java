package com.kosi0917.textandfacerecognitionapp.Model.facebook;

/**
 * Created by kosi0917 on 07-Dec-17.
 */

public class AttachImage {
    public int height;
    public String src;
    public int width;

    public AttachImage(int height, String src, int width) {
        this.height = height;
        this.src = src;
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
