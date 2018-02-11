package com.kosi0917.textandfacerecognitionapp.ui.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kosi0917.textandfacerecognitionapp.Model.view.BaseViewModel;

/**
 * Created by vibo0917 on 1/26/2018.
 */

public abstract class BaseViewHolder<Item extends BaseViewModel> extends RecyclerView.ViewHolder {

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void bindViewHolder(Item item);

    public abstract void unbindViewHolder();
}
