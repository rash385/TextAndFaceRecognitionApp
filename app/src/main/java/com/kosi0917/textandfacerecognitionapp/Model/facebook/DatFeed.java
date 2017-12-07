package com.kosi0917.textandfacerecognitionapp.Model.facebook;

/**
 * Created by kosi0917 on 07-Dec-17.
 */

public class DatFeed {
    public String id;
    public Attachments attachments;

    public DatFeed(String id, Attachments attachments) {
        this.id = id;
        this.attachments = attachments;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Attachments getAttachments() {
        return attachments;
    }

    public void setAttachments(Attachments attachments) {
        this.attachments = attachments;
    }
}
