package com.kosi0917.textandfacerecognitionapp.ui.holder;

import android.view.View;
import android.widget.TextView;

import com.kosi0917.textandfacerecognitionapp.Model.view.NewsItemBodyViewModel;
import com.kosi0917.textandfacerecognitionapp.R;

/**
 * Created by vibo0917 on 1/26/2018.
 */

public class NewsItemBodyHolder extends BaseViewHolder<NewsItemBodyViewModel> {

    public TextView tvText;


    public NewsItemBodyHolder(View itemView) {
        super(itemView);

        tvText = (TextView) itemView.findViewById(R.id.tv_text);
    }

    @Override
    public void bindViewHolder(NewsItemBodyViewModel item) {
        tvText.setText(item.getText());
    }

    @Override
    public void unbindViewHolder() {
        tvText.setText(null);
    }
}