package com.kosi0917.textandfacerecognitionapp.rest.model.response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vibo0917 on 1/25/2018.
 */

public class BaseItemResponse <T> {
    public Integer count;
    public List<T> items = new ArrayList<>();

    public Integer getCount() {
        return count;
    }

    public List<T> getItems() {
        return items;
    }
}