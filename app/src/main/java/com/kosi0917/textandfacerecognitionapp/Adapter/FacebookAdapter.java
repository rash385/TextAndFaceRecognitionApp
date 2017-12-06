package com.kosi0917.textandfacerecognitionapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kosi0917.textandfacerecognitionapp.Model.RootFeed;
import com.kosi0917.textandfacerecognitionapp.R;

/**
 * Created by kosi0917 on 06-Dec-17.
 */

public class FacebookAdapter extends RecyclerView.Adapter<FeedViewHolder> {
    RootFeed rootFeed;
    private Context mContext;
    private LayoutInflater inflater;

    public FacebookAdapter(RootFeed rootFeed, Context mContext) {
        this.rootFeed = rootFeed;
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
        holder.txtTitle.setText(rootFeed.getData().get(position).getId());
        holder.txtPubDate.setText(rootFeed.getData().get(position).getUpdated_time().toString());
        holder.txtContent.setText(rootFeed.getData().get(position).getStory());
    }

    @Override
    public int getItemCount() {
        return rootFeed.data.size();
    }
}
