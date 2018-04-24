package com.kosi0917.textandfacerecognitionapp.ui.Fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.kosi0917.textandfacerecognitionapp.R;

public class MyPreferencesFragment extends PreferenceFragment {


    public MyPreferencesFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

    }



}