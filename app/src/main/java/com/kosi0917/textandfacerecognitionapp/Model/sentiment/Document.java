package com.kosi0917.textandfacerecognitionapp.Model.sentiment;

/**
 * Created by kosi0917 on 16-Apr-18.
 */

public class Document {
    public String id,
            text;

    public Document(String id, String text) {
        this.setId(id);
        this.setText(text);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
