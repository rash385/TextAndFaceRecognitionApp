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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kosi0917.textandfacerecognitionapp.Adapter.FacebookImgAdapter;
import com.kosi0917.textandfacerecognitionapp.Application.Application;
import com.kosi0917.textandfacerecognitionapp.Common.manager.MyFragmentManager;
import com.kosi0917.textandfacerecognitionapp.Model.facebook.DatFeed;
import com.kosi0917.textandfacerecognitionapp.Model.facebook.Datum;
import com.kosi0917.textandfacerecognitionapp.Model.facebook.GroupEntity;
import com.kosi0917.textandfacerecognitionapp.Model.facebook.RootFeed;
import com.kosi0917.textandfacerecognitionapp.Model.facebook.RootImgFeed;
import com.kosi0917.textandfacerecognitionapp.ProfileActivity;
import com.kosi0917.textandfacerecognitionapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sivko on 07.12.2017.
 */

public class FacebookImgNewsActivity extends MvpAppCompatActivity {
    @Inject
    MyFragmentManager myFragmentManager;


    @BindView(R.id.progress_fb)
    protected ProgressBar mProgressBar;


    RecyclerView recyclerView;
    RootImgFeed rootImgFeed;
    RootFeed rootFeed;
    GroupEntity groupEntity;
    String TAG = "FacebookImgNewsActivity";
    String data,firstName,lastName,imgUrl,dataMessage,groupInfoJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle inBundle = getIntent().getExtras();
        data = inBundle.getString("data");
        dataMessage = inBundle.getString("dataMessages");
        firstName = inBundle.getString("name");
        lastName = inBundle.getString("surname");
        imgUrl = inBundle.getString("imageUrl");
        groupInfoJson = inBundle.getString("groupInfoJson");
        setContentView(R.layout.activity_facebook_login);
//        ButterKnife.bind(this);

       // Application.getApplicationComponent().inject(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarFb);
        toolbar.setTitle("Group News");
        setSupportActionBar(toolbar);

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
                    case R.id.ic_android:
                        //Некторый код
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
    public ProgressBar getProgressBar() {
        return mProgressBar;
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
                List<DatFeed> viewedData = new ArrayList<DatFeed>();
                for (int i=0; i < data.size(); i++)
                    if(data.get(i).attachments!=null)
                        viewedData.add(data.get(i));
                rootImgFeed = new RootImgFeed(viewedData);

                Type newsDescriptionType = new TypeToken<List<Datum>>(){}.getType();
                List<Datum> descriptionData = new Gson().fromJson(dataMessage, newsDescriptionType);
                rootFeed = new RootFeed(descriptionData);

                Type newGroupType = new TypeToken<GroupEntity>(){}.getType();
                GroupEntity groupData = new Gson().fromJson(groupInfoJson, newGroupType);

                FacebookImgAdapter feedadapter = new FacebookImgAdapter(rootImgFeed,rootFeed,groupData,getBaseContext());
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