package com.kosi0917.textandfacerecognitionapp.Model.VK;

import java.util.List;

/**
 * Created by vibo0917 on 12/14/2017.
 */

public class VkModel {
    private int id;
    private int from_id;
    private int owner_id;
    private int date;
    private String post_type;
    private String text;
    private List<Photo> attachments;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFrom_id() {
        return from_id;
    }

    public void setFrom_id(int from_id) {
        this.from_id = from_id;
    }

    public int getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(int owner_id) {
        this.owner_id = owner_id;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getPost_type() {
        return post_type;
    }

    public void setPost_type(String post_type) {
        this.post_type = post_type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Photo> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Photo> attachments) {
        this.attachments = attachments;
    }



    @Override
    public String toString() {
        return "VkModel{" +
                "id=" + id +
                ", from_id=" + from_id +
                ", owner_id=" + owner_id +
                ", date=" + date +
                ", post_type='" + post_type + '\'' +
                ", text='" + text + '\'' +
                ", attachments=" + attachments +
                '}';
    }
}
