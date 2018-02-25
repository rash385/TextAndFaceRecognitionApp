package com.kosi0917.textandfacerecognitionapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kosi0917.textandfacerecognitionapp.ImagesAnalyzer.ImageActivities.ImageActivity;
import com.kosi0917.textandfacerecognitionapp.Interface.ItemClickListener;
import com.kosi0917.textandfacerecognitionapp.Model.facebook.GroupEntity;
import com.kosi0917.textandfacerecognitionapp.Model.facebook.RootFeed;
import com.kosi0917.textandfacerecognitionapp.Model.facebook.RootImgFeed;
import com.kosi0917.textandfacerecognitionapp.ProfileActivity;
import com.kosi0917.textandfacerecognitionapp.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by kosi0917 on 07-Dec-17.
 */

public class FacebookImgAdapter extends RecyclerView.Adapter<FacebookImgViewHolder> {
    private RootImgFeed rootImgFeed;
    private GroupEntity groupEntity;
    private RootFeed rootFeed;
    private Context mContext;
    private LayoutInflater inflater;

    public FacebookImgAdapter(RootImgFeed rootImgFeed, RootFeed rootFeed, GroupEntity groupEntity, Context mContext) {
        this.rootImgFeed = rootImgFeed;
        this.groupEntity = groupEntity;
        this.rootFeed =rootFeed;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public FacebookImgViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.fb_item_news_body,parent,false);
        return new FacebookImgViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FacebookImgViewHolder holder, int position) {
            new ProfileActivity.DownloadImage(holder.feedImg).execute(rootImgFeed.getData().get(position).getAttachments().getData().get(0).getMedia().getImage().getSrc());
            new ProfileActivity.DownloadImage(holder.groupImg).execute(groupEntity.getIcon());
            holder.txtContent.setText(rootImgFeed.getData().get(position).getAttachments().getData().get(0).getDescription());
            SimpleDateFormat formatForDateNow = new SimpleDateFormat("E yyyy.MM.dd 'in' hh:mm");
            holder.txtPubDate.setText(formatForDateNow.format(rootFeed.getData().get(position).getUpdated_time()));
            holder.profileName.setText(groupEntity.getName());

            holder.setItemClickListener(new ItemClickListener() {
                @Override
                public void onClick(View view, int position, boolean isLongClick) {
                    if(!isLongClick){
                        Intent intent = new Intent(view.getContext(),ImageActivity.class);
                        intent.putExtra("imageUrl", rootImgFeed.getData().get(position).getAttachments().getData().get(0).getMedia().getImage().getSrc());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                    }
                }
            });
    }

    @Override
    public int getItemCount() {
        return rootImgFeed.data.size();
    }
}

class FacebookImgViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

    public TextView txtTitle,txtPubDate,txtContent,profileName;
    public ImageView feedImg;
    public CircleImageView groupImg;
    private ItemClickListener itemClickListener;

    public FacebookImgViewHolder(View itemView) {
        super(itemView);
        txtPubDate = (TextView)itemView.findViewById(R.id.tv_date_fb);
        groupImg = (CircleImageView)itemView.findViewById(R.id.civ_profile_image_fb);
        txtContent = (TextView)itemView.findViewById(R.id.tv_text_fb);
        feedImg = (ImageView)itemView.findViewById(R.id.newsPicId_fb);
        profileName = (TextView)itemView.findViewById(R.id.tv_profile_name_fb);

        //Set Event
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }

    @Override
    public boolean onLongClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),true);
        return true;
    }
}