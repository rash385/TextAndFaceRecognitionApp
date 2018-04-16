package com.kosi0917.textandfacerecognitionapp.Model.sentiment;

import java.util.List;

/**
 * Created by sivko on 16.04.2018.
 */

public class Results {
    private List<ResultsDoc> documents;

    public List<ResultsDoc> getDocuments() {
        return documents;
    }

    public void setDocuments(List<ResultsDoc> documents) {
        this.documents = documents;
    }
}
