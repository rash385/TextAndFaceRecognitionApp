package com.kosi0917.textandfacerecognitionapp.mvp.view;

import com.kosi0917.textandfacerecognitionapp.Model.view.NewsItemFooterViewModel;

/**
 * Created by mozil on 20.03.2018.
 */

public interface OpenedPostView extends BaseFeedView {
    void setFooter(NewsItemFooterViewModel viewModel);
}
