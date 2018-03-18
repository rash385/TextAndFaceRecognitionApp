package com.kosi0917.textandfacerecognitionapp.ui.Fragment;

import android.os.Bundle;

import com.kosi0917.textandfacerecognitionapp.R;

import io.reactivex.annotations.Nullable;

/**
 * Created by mozil on 18.03.2018.
 */

public class MyPostsFragment extends NewsFeedFragment {
    public MyPostsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.setEnableIdFiltering(true);
    }

    @Override
    public int onCreateToolbarTitle() {
        return R.string.screen_name_my_posts;
    }
}

