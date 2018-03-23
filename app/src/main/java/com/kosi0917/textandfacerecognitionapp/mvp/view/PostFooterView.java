package com.kosi0917.textandfacerecognitionapp.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.kosi0917.textandfacerecognitionapp.Model.VK.WallItem;
import com.kosi0917.textandfacerecognitionapp.Model.view.counter.LikeCounterViewModel;

/**
 * Created by vibo0917 on 3/23/2018.
 */

public interface PostFooterView extends MvpView {
    void like(LikeCounterViewModel likes);

    void openComments(WallItem wallItem);
}