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

    private String mAttachmentString;

    private boolean mIsRepost;

    public NewsItemBodyViewModel(WallItem wallItem) {
        this.mId = wallItem.getId();
        this.mIsRepost = wallItem.haveSharedRepost();

        if (mIsRepost) {
            this.mText = wallItem.getSharedRepost().getText();
            this.mAttachmentString = wallItem.getSharedRepost().getAttachmentsString();
        } else {
            this.mText = wallItem.getText();
            this.mAttachmentString = wallItem.getAttachmentsString();
        }
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

    public String getmAttachmentString() {
        return mAttachmentString;
    }
}