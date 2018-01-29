package com.kosi0917.textandfacerecognitionapp.ui.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.kosi0917.textandfacerecognitionapp.Common.BaseAdapter;
import com.kosi0917.textandfacerecognitionapp.R;

/**
 * Created by mozil on 28.01.2018.
 */

public abstract class BaseFeedFragment extends BaseFragment{

    protected SwipeRefreshLayout mSwipeRefreshLayout;
    protected ProgressBar mProgressBar;

    RecyclerView mRecyclerView;

    BaseAdapter mAdapter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpSwipeToRefreshLayout(view);

        setUpRecyclerView(view);
        setUpAdapter(mRecyclerView);
    }

    private void setUpRecyclerView(View rootView) {
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    protected void setUpAdapter(RecyclerView rv) {
        mAdapter = new BaseAdapter();
        rv.setAdapter(mAdapter);
    }

    private void setUpSwipeToRefreshLayout(View rootView) {
        mSwipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.swipe_refresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mProgressBar = getBaseActivity().getProgressBar();
    }

    @Override
    protected int getMainContentLayout() {
        return R.layout.vk_fragment_feed;
    }

    @Override
    public int onCreateToolbarTitle() {
        return 0;
    }
}
