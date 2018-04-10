package com.kosi0917.textandfacerecognitionapp.ImagesAnalyzer.ImageActivities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kosi0917.textandfacerecognitionapp.ImagesAnalyzer.ImageHelper;
import com.kosi0917.textandfacerecognitionapp.Model.facebook.DatFeed;
import com.kosi0917.textandfacerecognitionapp.Model.facebook.RootImgFeed;
import com.kosi0917.textandfacerecognitionapp.R;
import com.microsoft.projectoxford.emotion.EmotionServiceClient;
import com.microsoft.projectoxford.emotion.EmotionServiceRestClient;
import com.microsoft.projectoxford.emotion.contract.RecognizeResult;
import com.microsoft.projectoxford.emotion.rest.EmotionServiceException;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * Created by sivko on 08.04.2018.
 */

public class StatisticsGraphicsActivity extends AppCompatActivity {
    EmotionServiceClient restClient = new EmotionServiceRestClient("87aa57dc540b439193a60cc5bce69f90");
    private int TAKE_PICTURE_CODE = 100;
    LineChartView chart;
    String json;
    RootImgFeed rootImgFeed;
    List<Double> happinesList = new ArrayList<>();

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle inBundle = getIntent().getExtras();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_graphic_statistics);

        chart = (LineChartView) findViewById(R.id.chart_graphic);
        json = inBundle.getString("data");
        Type collectionType = new TypeToken<List<DatFeed>>(){}.getType();
        List<DatFeed> data = new Gson().fromJson(json, collectionType);
        List<DatFeed> viewedData = new ArrayList<DatFeed>();
        for (int i=0; i < data.size(); i++)
            if(data.get(i).attachments!=null)
                viewedData.add(data.get(i));
        rootImgFeed = new RootImgFeed(viewedData);
        EmotionServiceClient restClient = new EmotionServiceRestClient("87aa57dc540b439193a60cc5bce69f90");

        for(DatFeed imageData: rootImgFeed.getData() )
            processImage(imageData.getAttachments().getData().get(0).getMedia().getImage().getSrc());

        //Create image graphics
        ImageHelper.drawStaticsForAll(chart,happinesList);
    }

    private void processImage(String facebookImageURL) {
        //Create Async Task to Process Data
        AsyncTask<InputStream,String,List<RecognizeResult>> processAsync = new AsyncTask<InputStream, String, List<RecognizeResult>>() {
            @Override
            protected List<RecognizeResult> doInBackground(InputStream... params) {
                publishProgress("Please wait ...");
                List<RecognizeResult> result = null;
                try {
                    result = restClient.recognizeImage(facebookImageURL);
                } catch (EmotionServiceException e) {
                    e.printStackTrace();
                }
                return result;
            }
            @Override
            protected void onPostExecute(List<RecognizeResult> recognizeResults) {
                for (RecognizeResult res: recognizeResults)
                {
                    System.out.println(res.scores.happiness);
                    happinesList.add(res.scores.happiness);
                }
            }
        };

        processAsync.execute(/*inputStream*/);
    }
}
