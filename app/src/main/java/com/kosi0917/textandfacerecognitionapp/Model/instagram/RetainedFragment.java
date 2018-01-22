package com.kosi0917.textandfacerecognitionapp.Model.instagram;

import android.app.Fragment;
import android.os.Bundle;

/**
 * Created by sivko on 17.12.2017.
 */

public class RetainedFragment extends Fragment {
    private Data[] data;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // retain this fragment
        setRetainInstance(true);
    }

    public Data[] getData() {
        return data;
    }

    public void setData(Data[] data) {
        this.data = data;
    }
}
