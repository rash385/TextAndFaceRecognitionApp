package com.kosi0917.textandfacerecognitionapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kosi0917.textandfacerecognitionapp.Model.facebook.DatFeed;
import com.kosi0917.textandfacerecognitionapp.Model.sentiment.Document;
import com.kosi0917.textandfacerecognitionapp.Model.sentiment.Documents;
import com.kosi0917.textandfacerecognitionapp.Model.sentiment.Results;
import com.kosi0917.textandfacerecognitionapp.Model.sentiment.ResultsDoc;
import com.kosi0917.textandfacerecognitionapp.analyze.ImagesAnalyzer.ImageActivities.ImageActivity;
import com.kosi0917.textandfacerecognitionapp.Interface.ItemClickListener;
import com.kosi0917.textandfacerecognitionapp.Model.facebook.GroupEntity;
import com.kosi0917.textandfacerecognitionapp.Model.facebook.RootFeed;
import com.kosi0917.textandfacerecognitionapp.Model.facebook.RootImgFeed;
import com.kosi0917.textandfacerecognitionapp.ProfileActivity;
import com.kosi0917.textandfacerecognitionapp.R;
import com.kosi0917.textandfacerecognitionapp.analyze.sentiment.GetSentiment;
import com.microsoft.projectoxford.emotion.EmotionServiceClient;
import com.microsoft.projectoxford.emotion.EmotionServiceRestClient;
import com.microsoft.projectoxford.emotion.contract.RecognizeResult;
import com.microsoft.projectoxford.emotion.contract.Scores;
import com.microsoft.projectoxford.emotion.rest.EmotionServiceException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    private List<ResultsDoc> documents = new ArrayList<ResultsDoc>();
    private EmotionServiceClient rest= new EmotionServiceRestClient("87aa57dc540b439193a60cc5bce69f90");
    Gson gson = new Gson();

    public FacebookImgAdapter(RootImgFeed rootImgFeed, RootFeed rootFeed,String textRes, GroupEntity groupEntity, Context mContext) {
        this.rootImgFeed = rootImgFeed;
        this.groupEntity = groupEntity;
        this.rootFeed =rootFeed;
        this.mContext = mContext;
        try {
            Type collectionType = new TypeToken<List<ResultsDoc>>() {
            }.getType();
            JSONObject obj = new JSONObject(textRes);
            this.documents=gson.fromJson(obj.getString("documents").toString(),collectionType);
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
            holder.txtContent.setText(rootImgFeed.getData().get(position).getAttachments().getData().get(0).getDescription() + " " + documents.get(position).getScore());
            SimpleDateFormat formatForDateNow = new SimpleDateFormat("E yyyy.MM.dd 'in' hh:mm");
            holder.txtPubDate.setText(formatForDateNow.format(rootFeed.getData().get(position).getUpdated_time()));
            holder.profileName.setText(groupEntity.getName());

            //Выводим описание эмоции на экран
            processImage(rootImgFeed.getData().get(position).getAttachments().getData().get(0).getMedia().getImage().getSrc(),holder);

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

    private void processImage(String facebookImageURL,FacebookImgViewHolder holder) {

        //Create Async Task to Process Data

        AsyncTask<InputStream,String,List<RecognizeResult>> processAsync = new AsyncTask<InputStream, String, List<RecognizeResult>>() {
            @Override
            protected List<RecognizeResult> doInBackground(InputStream... params) {
                publishProgress("Please wait ...");
                List<RecognizeResult> result = null;
                try {
                    result = rest.recognizeImage(facebookImageURL);
                } catch (EmotionServiceException e) {
                    e.printStackTrace();
                }
                return result;
            }

            @Override
            protected void onPostExecute(List<RecognizeResult> recognizeResults) {
                for (RecognizeResult res: recognizeResults)
                {
                    String emotionStatus = getEmotion(res);
                    holder.txtEmotion.setText(emotionStatus);
                }
            }
        };

        processAsync.execute(/*inputStream*/);
    }


    @Override
    public int getItemCount() {
        return rootImgFeed.data.size();
    }


    private String getEmotion(RecognizeResult res) {
        List<Double> list = new ArrayList<>();
        Scores scores = res.scores;

        list.add(scores.anger);
        list.add(scores.contempt);
        list.add(scores.happiness);
        list.add(scores.disgust);
        list.add(scores.fear);
        list.add(scores.neutral);
        list.add(scores.sadness);
        list.add(scores.surprise);


        //Sort List
        Collections.sort(list);

        double maxNum = list.get(list.size() - 1);

        if (maxNum == scores.anger)
            return "Anger";
        else if(maxNum == scores.sadness)
            return "Sadness";
        else if(maxNum == scores.surprise)
            return "Surprise";
        else if(maxNum == scores.happiness)
            return "Happiness";
        else if(maxNum == scores.disgust)
            return "Disgust";
        else if(maxNum == scores.fear)
            return "Fear";
        else if(maxNum == scores.neutral)
            return "Neutral";
        else if(maxNum == scores.contempt)
            return "Contempt";

        return "Can't detect";
    }
}



class FacebookImgViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

    public TextView txtTitle,txtPubDate,txtContent,profileName,txtEmotion;
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
        txtEmotion = (TextView)itemView.findViewById(R.id.tv_likes_count_fb);
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