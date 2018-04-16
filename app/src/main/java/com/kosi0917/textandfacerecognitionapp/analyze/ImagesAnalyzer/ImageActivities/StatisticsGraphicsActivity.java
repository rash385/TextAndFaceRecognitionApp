package com.kosi0917.textandfacerecognitionapp.analyze.ImagesAnalyzer.ImageActivities;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kosi0917.textandfacerecognitionapp.analyze.ImagesAnalyzer.ImageHelper;
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
    Button btnStartAnalisis;
    List<RecognizeResult> happinesList = new ArrayList<>();
    List<Double> chossenResults = new ArrayList<>();

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.graphics_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ic_positive: {
                for(RecognizeResult recognizeResult:happinesList)
                    chossenResults.add(recognizeResult.scores.happiness);
                break;
            }
            case R.id.ic_negative: {
                for(RecognizeResult recognizeResult:happinesList)
                    chossenResults.add(recognizeResult.scores.anger);
                break;
            }

            case R.id.ic_surprise: {
                for(RecognizeResult recognizeResult:happinesList)
                    chossenResults.add(recognizeResult.scores.surprise);
                break;
            }

            case R.id.ic_sadness: {
                for(RecognizeResult recognizeResult:happinesList)
                    chossenResults.add(recognizeResult.scores.sadness);
                break;
            }
            case R.id.ic_back: {

                break;
            }
            // case blocks for other MenuItems (if any)
        }
        return false;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle inBundle = getIntent().getExtras();
        super.onCreate(savedInstanceState);

        setContentView(R.layout.page_graphic_statistics);
        Toolbar toolbar = (Toolbar) findViewById(R.id.image_analise_toolbar);
        toolbar.inflateMenu(R.menu.graphics_menu);
        setSupportActionBar(toolbar);



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
        initViews();

}

    private void initViews() {
        btnStartAnalisis = (Button)findViewById(R.id.start_analise_graphic);
        btnStartAnalisis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageHelper.drawStaticsForAll(chart,chossenResults);
            }
        });

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
                    happinesList.add(res);
                }
            }
        };

        processAsync.execute(/*inputStream*/);
    }
}
