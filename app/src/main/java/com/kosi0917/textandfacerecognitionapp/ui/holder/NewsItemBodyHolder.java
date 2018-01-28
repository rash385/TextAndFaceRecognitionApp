package com.kosi0917.textandfacerecognitionapp.ui.holder;

import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import com.kosi0917.textandfacerecognitionapp.Application.Application;
import com.kosi0917.textandfacerecognitionapp.Model.view.NewsItemBodyViewModel;
import com.kosi0917.textandfacerecognitionapp.R;

import javax.inject.Inject;

/**
 * Created by vibo0917 on 1/26/2018.
 */

public class NewsItemBodyHolder extends BaseViewHolder<NewsItemBodyViewModel> {

    private TextView tvText;

    private TextView tvAttachments;

    @Inject
    protected Typeface mFontGoogle;


    public NewsItemBodyHolder(View itemView) {
        super(itemView);
        Application.getApplicationComponent().inject(this);

        tvText = (TextView) itemView.findViewById(R.id.tv_text);
        tvAttachments = (TextView) itemView.findViewById(R.id.tv_attachments);

        if (tvAttachments != null) {
            tvAttachments.setTypeface(mFontGoogle);
        }
    }

    @Override
    public void bindViewHolder(NewsItemBodyViewModel item) {
        tvText.setText(item.getText());
        tvAttachments.setText(item.getmAttachmentString());
    }

    @Override
    public void unbindViewHolder() {
        tvText.setText(null);
        tvAttachments.setText(null);
    }
}