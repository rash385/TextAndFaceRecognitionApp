package com.kosi0917.textandfacerecognitionapp.FBActivities;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kosi0917.textandfacerecognitionapp.Adapter.FacebookAdapter;
import com.kosi0917.textandfacerecognitionapp.Adapter.FacebookImgAdapter;
import com.kosi0917.textandfacerecognitionapp.Model.facebook.DatFeed;
import com.kosi0917.textandfacerecognitionapp.Model.facebook.Datum;
import com.kosi0917.textandfacerecognitionapp.Model.facebook.RootFeed;
import com.kosi0917.textandfacerecognitionapp.Model.facebook.RootImgFeed;
import com.kosi0917.textandfacerecognitionapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by sivko on 07.12.2017.
 */

public class FacebookImgNewsActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    RootImgFeed rootImgFeed;
    String TAG = "FacebookImgNewsActivity";
    String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle inBundle = getIntent().getExtras();
        data = inBundle.getString("data");
        setContentView(R.layout.activity_facebook_login);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Group news with image");
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        loadFeed();
    }

    private void loadFeed() {
        AsyncTask<String, String, String> loadFeedAsync = new AsyncTask<String, String, String>() {
            ProgressDialog mDialog = new ProgressDialog(FacebookImgNewsActivity.this);
            @Override
            protected void onPreExecute() {
                mDialog.setMessage("Пожалуста, подождите...");
                mDialog.show();
            }

            @Override
            protected String doInBackground(String... params) {
                Bundle b = new Bundle();
                b.putString("fields", "attachments");
                new GraphRequest(
                        AccessToken.getCurrentAccessToken(),
                        "1995900030677807/feed",
                        b,
                        HttpMethod.GET,
                        new GraphRequest.Callback() {
                            public void onCompleted(GraphResponse response) {
                                Log.e(TAG,response.toString());
                                try {
                                    JSONObject obj = new JSONObject(response.getRawResponse());
                                    if (obj.has("data")) {
                                        data = obj.getString("data");
                                        Log.e(TAG,data);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                ).executeAsync();
                return data;
            }

            @Override
            protected void onPostExecute(String s) {
                mDialog.dismiss();
                Type collectionType = new TypeToken<List<DatFeed>>(){}.getType();
                List<DatFeed> data = new Gson().fromJson(s, collectionType);
                rootImgFeed = new RootImgFeed(data);
                FacebookImgAdapter feedadapter = new FacebookImgAdapter(rootImgFeed,getBaseContext());
                recyclerView.setAdapter(feedadapter);
                feedadapter.notifyDataSetChanged();
            }

        };
        loadFeedAsync.execute(data);
    }
}