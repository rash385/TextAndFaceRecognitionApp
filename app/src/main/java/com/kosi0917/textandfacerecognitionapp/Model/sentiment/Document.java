package com.kosi0917.textandfacerecognitionapp.Model.sentiment;

/**
 * Created by kosi0917 on 16-Apr-18.
 */

public class Document {
    public String id,
            language,
            text;

    public Document(String id, String language, String text) {
        this.setId(id);
        this.setLanguage(language);
        this.setText(text);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
