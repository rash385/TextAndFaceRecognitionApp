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

public class InfoContactsViewModel extends BaseViewModel {
    @Override
    public LayoutTypes getType() {
        return LayoutTypes.InfoContacts;
    }

    @Override
    public InfoContactsViewHolder onCreateViewHolder(View view) {
        return new InfoContactsViewHolder(view);
    }


    static class InfoContactsViewHolder extends BaseViewHolder<InfoContactsViewModel> {
        @BindView(R.id.rv_contacts)
        RelativeLayout rvContacts;

        public InfoContactsViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bindViewHolder(InfoContactsViewModel infoContactsViewModel) {
        }

        @Override
        public void unbindViewHolder() {
        }
    }
}