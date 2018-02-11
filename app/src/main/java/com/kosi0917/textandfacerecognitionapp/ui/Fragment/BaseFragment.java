package com.kosi0917.textandfacerecognitionapp.ui.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.kosi0917.textandfacerecognitionapp.ui.Activity.BaseActivity;

/**
 * Created by vibo0917 on 1/24/2018.
 */

public abstract class BaseFragment extends MvpAppCompatFragment {

    private String mToolbarTitle;

    @LayoutRes
    protected abstract int getMainContentLayout();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(getMainContentLayout(), container, false);
    }

    public String createToolbarTitle(Context context) {
        return context.getString(onCreateToolbarTitle());
    }

    public BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    @StringRes
    public abstract int onCreateToolbarTitle();

}
