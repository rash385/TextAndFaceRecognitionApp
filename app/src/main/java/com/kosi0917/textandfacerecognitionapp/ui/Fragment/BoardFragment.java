package com.kosi0917.textandfacerecognitionapp.ui.Fragment;

import android.os.Bundle;
import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.kosi0917.textandfacerecognitionapp.R;
import com.kosi0917.textandfacerecognitionapp.mvp.presenter.BaseFeedPresenter;
import com.kosi0917.textandfacerecognitionapp.mvp.presenter.BoardPresenter;

import butterknife.ButterKnife;
import io.reactivex.annotations.Nullable;

/**
 * Created by mozil on 18.03.2018.
 */

public class BoardFragment extends BaseFeedFragment {
    @InjectPresenter
    BoardPresenter mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @Override
    protected BaseFeedPresenter onCreateFeedPresenter() {
        return mPresenter;
    }

    @Override
    public int onCreateToolbarTitle() {
        return R.string.screen_name_topics;
    }
}

