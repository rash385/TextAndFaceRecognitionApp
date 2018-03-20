package com.kosi0917.textandfacerecognitionapp.Common.utils;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.TextView;

/**
 * Created by mozil on 20.03.2018.
 */

public class UiHelper {
    private static UiHelper ourInstance = new UiHelper();

    private Resources resources;
    private Context context;

    public static UiHelper getInstance() {
        return ourInstance;
    }



    public void setUpTextViewWithVisibility(TextView textView, String s) {
        textView.setText(s);

        if (s.length() != 0) {
            textView.setVisibility(View.VISIBLE);
        } else {
            textView.setVisibility(View.GONE);
        }
    }


}