package com.kosi0917.textandfacerecognitionapp.ImagesAnalyzer.ImageActivities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.kosi0917.textandfacerecognitionapp.ImagesAnalyzer.ImageHelper;
import com.kosi0917.textandfacerecognitionapp.R;
import com.microsoft.projectoxford.emotion.EmotionServiceClient;
import com.microsoft.projectoxford.emotion.EmotionServiceRestClient;
import com.microsoft.projectoxford.emotion.contract.RecognizeResult;
import com.microsoft.projectoxford.emotion.contract.Scores;
import com.microsoft.projectoxford.emotion.rest.EmotionServiceException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by sivko on 23.01.2018.
 */

public class ImageActivity extends AppCompatActivity {
    ImageView imageView;
    Button btnProcess;
    EmotionServiceClient restClient = new EmotionServiceRestClient("87aa57dc540b439193a60cc5bce69f90");
    private int TAKE_PICTURE_CODE = 100;
    Bitmap mBitmap;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_analize);

        initViews();

        // if (checkSelfPermission(Manifest.p))
    }

    private void initViews() {
        btnProcess = (Button)findViewById(R.id.analise);
     //   btnTakePicture = (Button)findViewById(R.id.btnTakePic);
        imageView = (ImageView)findViewById(R.id.imageView);

        //Event
/*        btnTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicFromGallery();
            }
        });*/

        btnProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processImage();
            }
        });

    }

    private void processImage() {
        //Conver image to stream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        mBitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

        //Create Async Task to Process Data
        AsyncTask<InputStream,String,List<RecognizeResult>> processAsync = new AsyncTask<InputStream, String, List<RecognizeResult>>() {

            ProgressDialog mDialog = new ProgressDialog(ImageActivity.this);

            @Override
            protected void onPreExecute() {
                mDialog.show();
            }

            @Override
            protected List<RecognizeResult> doInBackground(InputStream... params) {
                publishProgress("Please wait ...");
                List<RecognizeResult> result = null;
                try {
                    result = restClient.recognizeImage(params[0]);
                } catch (EmotionServiceException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return  result;
            }

            @Override
            protected void onProgressUpdate(String... values) {
                mDialog.setMessage(values[0]);
            }

            @Override
            protected void onPostExecute(List<RecognizeResult> recognizeResults) {
                mDialog.dismiss();
                for (RecognizeResult res: recognizeResults)
                {
                    String status = getEmotion(res);
                    imageView.setImageBitmap(ImageHelper.drawRectOnBitmap(mBitmap,res.faceRectangle,status));
                }
            }
        };

        processAsync.execute(inputStream);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TAKE_PICTURE_CODE)
        {
            Uri selectedImageUri = data.getData();
            InputStream in = null;
            try {
                in = getContentResolver().openInputStream(selectedImageUri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            mBitmap = BitmapFactory.decodeStream(in);
            imageView.setImageBitmap(mBitmap);
        }
    }

    private void takePicFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,TAKE_PICTURE_CODE);
    }
}
