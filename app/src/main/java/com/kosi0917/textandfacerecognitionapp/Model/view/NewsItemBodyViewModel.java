package com.kosi0917.textandfacerecognitionapp.Model.view;

import android.view.View;

import com.kosi0917.textandfacerecognitionapp.Model.VK.WallItem;
import com.kosi0917.textandfacerecognitionapp.ui.holder.NewsItemBodyHolder;

/**
 * Created by vibo0917 on 1/26/2018.
 */

public class NewsItemBodyViewModel extends BaseViewModel {
    private int mId;

    private String mText;

    public NewsItemBodyViewModel(WallItem wallItem) {
        this.mId = wallItem.getId();
        this.mText = wallItem.getText();
    }


    @Override
    public LayoutTypes getType() {
        return LayoutTypes.NewsFeedItemBody;
    }

    @Override
    public NewsItemBodyHolder onCreateViewHolder(View view) {
        return new NewsItemBodyHolder(view);
    }


    public String getText() {
        return mText;
    }

    public int getId() {
        return mId;
    }
}
