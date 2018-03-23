package com.kosi0917.textandfacerecognitionapp.di.component;

import com.kosi0917.textandfacerecognitionapp.Common.manager.NetworkManager;
import com.kosi0917.textandfacerecognitionapp.Model.view.CommentBodyViewModel;
import com.kosi0917.textandfacerecognitionapp.Model.view.CommentFooterViewModel;
import com.kosi0917.textandfacerecognitionapp.di.module.ApplicationModule;
import com.kosi0917.textandfacerecognitionapp.di.module.ManagerModule;
import com.kosi0917.textandfacerecognitionapp.di.module.RestModule;
import com.kosi0917.textandfacerecognitionapp.mvp.presenter.BoardPresenter;
import com.kosi0917.textandfacerecognitionapp.mvp.presenter.CommentsPresenter;
import com.kosi0917.textandfacerecognitionapp.mvp.presenter.InfoPresenter;
import com.kosi0917.textandfacerecognitionapp.mvp.presenter.MainPresenter;
import com.kosi0917.textandfacerecognitionapp.mvp.presenter.MembersPresenter;
import com.kosi0917.textandfacerecognitionapp.mvp.presenter.NewsFeedPresenter;
import com.kosi0917.textandfacerecognitionapp.mvp.presenter.OpenedPostPresenter;
import com.kosi0917.textandfacerecognitionapp.ui.Activity.BaseActivity;
import com.kosi0917.textandfacerecognitionapp.ui.Activity.VKLoginActivity;
import com.kosi0917.textandfacerecognitionapp.ui.Fragment.CommentsFragment;
import com.kosi0917.textandfacerecognitionapp.ui.Fragment.NewsFeedFragment;
import com.kosi0917.textandfacerecognitionapp.ui.Fragment.OpenedPostFragment;
import com.kosi0917.textandfacerecognitionapp.ui.holder.NewsItemBodyHolder;
import com.kosi0917.textandfacerecognitionapp.ui.holder.NewsItemFooterHolder;
import com.kosi0917.textandfacerecognitionapp.ui.holder.attachment.ImageAttachmentHolder;
import com.kosi0917.textandfacerecognitionapp.ui.holder.attachment.VideoAttachmentHolder;


import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by vibo0917 on 1/25/2018.
 */

@Singleton
@Component(
        modules = {ApplicationModule.class, RestModule.class, ManagerModule.class})
public interface ApplicationComponent {

    //activities
    void inject(BaseActivity activity);
    void inject(VKLoginActivity activity);

    //fragments
    void inject(NewsFeedFragment fragment);
    void inject(OpenedPostFragment fragment);
    void inject(CommentsFragment fragment);

    //holders
    void inject(NewsItemBodyHolder holder);
    void inject(NewsItemFooterHolder holder);
    void inject(ImageAttachmentHolder holder);
    void inject(VideoAttachmentHolder holder);
    void inject(CommentBodyViewModel.CommentBodyViewHolder holder);
    void inject(CommentFooterViewModel.CommentFooterHolder holder);

    //presenters
    void inject(NewsFeedPresenter presenter);
    void inject(MainPresenter presenter);
    void inject(MembersPresenter presenter);
    void inject(BoardPresenter presenter);
    void inject(InfoPresenter presenter);
    void inject(OpenedPostPresenter presenter);
    void inject(CommentsPresenter presenter);

    //managers
    void inject(NetworkManager manager);


}
