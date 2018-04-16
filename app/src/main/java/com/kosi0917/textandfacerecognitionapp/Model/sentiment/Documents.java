package com.kosi0917.textandfacerecognitionapp.Model.sentiment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kosi0917 on 16-Apr-18.
 */

public class Documents {
    public List<Document> documents;

    public Documents() {
        this.documents = new ArrayList<Document>();
    }
    public void add(String id, String text) {
        this.documents.add (new Document (id, text));
    }
}
