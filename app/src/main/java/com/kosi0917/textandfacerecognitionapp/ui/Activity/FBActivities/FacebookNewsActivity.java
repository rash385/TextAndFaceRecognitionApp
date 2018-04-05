package com.kosi0917.textandfacerecognitionapp.ui.Activity.FBActivities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kosi0917.textandfacerecognitionapp.Adapter.FacebookAdapter;
import com.kosi0917.textandfacerecognitionapp.Model.facebook.Datum;
import com.kosi0917.textandfacerecognitionapp.Model.facebook.RootFeed;
import com.kosi0917.textandfacerecognitionapp.ProfileActivity;
import com.kosi0917.textandfacerecognitionapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by kosi0917 on 06-Dec-17.
 */

public class FacebookNewsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RootFeed rootFeed;
    String TAG = "FacebookNewsActivity";
    String data,firstName,lastName,imgUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle inBundle = getIntent().getExtras();
        data = inBundle.getString("data");
        firstName = inBundle.getString("name");
        lastName = inBundle.getString("surname");
        imgUrl = inBundle.getString("imageUrl");
        setContentView(R.layout.activity_facebook_login);
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.NavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.ic_arrow:
                                goMainScreen();
                                break;
                        }
                        return false;
                    }
                });

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        loadFeed();
    }

    private void loadFeed() {
        AsyncTask<String, String, String> loadFeedAsync = new AsyncTask<String, String, String>() {
            ProgressDialog mDialog = new ProgressDialog(FacebookNewsActivity.this);
            @Override
            protected void onPreExecute() {
                mDialog.setMessage("Пожалуста, подождите...");
                mDialog.show();
            }

            @Override
            protected String doInBackground(String... params) {
                new GraphRequest(
                        AccessToken.getCurrentAccessToken(),
                        "1995900030677807/feed",
                        null,
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
                Type collectionType = new TypeToken<List<Datum>>(){}.getType();
                List<Datum> data = new Gson().fromJson(s, collectionType);
                rootFeed = new RootFeed(data);
                FacebookAdapter feedadapter = new FacebookAdapter(rootFeed,getBaseContext());
                recyclerView.setAdapter(feedadapter);
                feedadapter.notifyDataSetChanged();
            }

        };
        loadFeedAsync.execute(data);
    }

    private void goMainScreen() {
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("name", firstName);
        intent.putExtra("surname", lastName);
        intent.putExtra("imageUrl", imgUrl);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
