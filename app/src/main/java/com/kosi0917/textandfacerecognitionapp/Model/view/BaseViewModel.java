package com.kosi0917.textandfacerecognitionapp.Model.view;

import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kosi0917.textandfacerecognitionapp.R;
import com.kosi0917.textandfacerecognitionapp.ui.holder.BaseViewHolder;

/**
 * Created by vibo0917 on 1/26/2018.
 */

public abstract class BaseViewModel {

    public abstract LayoutTypes getType();

    public BaseViewHolder createViewHolder(ViewGroup parent) {
        return onCreateViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(getType().getValue(), parent, false));
    }

    protected abstract BaseViewHolder onCreateViewHolder(View view);


    public enum LayoutTypes {
        NewsFeedItemHeader(R.layout.vk_item_news_header),
        NewsFeedItemBody(R.layout.vk_item_news_body),
        NewsFeedItemFooter(R.layout.vk_item_news_footer);


        private final int id;

        LayoutTypes(int resId) {
            this.id = resId;
        }

        @LayoutRes
        public int getValue() {
            return id;
        }
    }
}