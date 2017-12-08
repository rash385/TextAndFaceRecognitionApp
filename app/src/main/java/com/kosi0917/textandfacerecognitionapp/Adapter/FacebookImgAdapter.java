package com.kosi0917.textandfacerecognitionapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kosi0917.textandfacerecognitionapp.Model.facebook.RootImgFeed;
import com.kosi0917.textandfacerecognitionapp.ProfileActivity;
import com.kosi0917.textandfacerecognitionapp.R;

/**
 * Created by kosi0917 on 07-Dec-17.
 */

public class FacebookImgAdapter extends RecyclerView.Adapter<FeedViewHolder> {
    private RootImgFeed rootImgFeed;
    private Context mContext;
    private LayoutInflater inflater;

    public FacebookImgAdapter(RootImgFeed rootImgFeed, Context mContext) {
        this.rootImgFeed = rootImgFeed;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public FeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.row,parent,false);
        return new FeedViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FeedViewHolder holder, int position) {
        RootImgFeed tetsted = rootImgFeed;
        if(rootImgFeed.getData().get(position).attachments!=null) {
            new ProfileActivity.DownloadImage(holder.feedImg).execute(rootImgFeed.getData().get(position).getAttachments().getData().get(0).getMedia().getImage().getSrc());
            holder.txtTitle.setText(rootImgFeed.getData().get(position).getAttachments().getData().get(0).getDescription());
            holder.txtPubDate.setText(" ");
            holder.txtContent.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return rootImgFeed.data.size();
    }
}
