package com.kosi0917.textandfacerecognitionapp.Model.view;

import android.view.View;
import android.widget.RelativeLayout;

import com.kosi0917.textandfacerecognitionapp.R;
import com.kosi0917.textandfacerecognitionapp.ui.holder.BaseViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mozil on 18.03.2018.
 */

public class InfoLinksViewModel extends BaseViewModel {

    @Override
    public LayoutTypes getType() {
        return LayoutTypes.InfoLinks;
    }

    @Override
    public InfoLinksViewHolder onCreateViewHolder(View view) {
        return new InfoLinksViewHolder(view);
    }


    static class InfoLinksViewHolder extends BaseViewHolder<InfoLinksViewModel> {


        @BindView(R.id.rv_links)
        RelativeLayout rvLinks;

        public InfoLinksViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bindViewHolder(InfoLinksViewModel infoLinksViewModel) {

        }

        @Override
        public void unbindViewHolder() {

        }
    }
}
