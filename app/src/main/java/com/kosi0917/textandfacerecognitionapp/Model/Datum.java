package com.kosi0917.textandfacerecognitionapp.Model;

import java.util.Date;

/**
 * Created by kosi0917 on 06-Dec-17.
 */

public class Datum
{
    public String story;
    public Date updated_time;
    public String id;
    public String message;

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public Date getUpdated_time() {
        return updated_time;
    }

    public void setUpdated_time(Date updated_time) {
        this.updated_time = updated_time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Datum(String story, Date updated_time, String id, String message) {

        this.story = story;
        this.updated_time = updated_time;
        this.id = id;
        this.message = message;
    }
}
