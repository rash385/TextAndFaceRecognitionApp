package com.kosi0917.textandfacerecognitionapp.ImagesAnalyzer.ImageActivities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.kosi0917.textandfacerecognitionapp.ImagesAnalyzer.ImageHelper;
import com.kosi0917.textandfacerecognitionapp.R;
import com.microsoft.projectoxford.emotion.EmotionServiceClient;
import com.microsoft.projectoxford.emotion.EmotionServiceRestClient;
import com.microsoft.projectoxford.emotion.contract.RecognizeResult;

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
        List<RecognizeResult> result = null;
        //TODO: Add recognize image
      //  result = restClient.recognizeImage(facebookImageURL);
        ImageHelper.drawStaticsForAll(chart);
    }
}
