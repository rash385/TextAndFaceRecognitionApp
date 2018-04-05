package com.kosi0917.textandfacerecognitionapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
//import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.AccessToken;
import com.google.gson.Gson;
import com.kosi0917.textandfacerecognitionapp.Adapter.Feedadapter;
import com.kosi0917.textandfacerecognitionapp.Common.HTTPDatahandler;
import com.kosi0917.textandfacerecognitionapp.Model.RSSObject;

/**
 * Created by kosi0917 on 04-Dec-17.
 */

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    RSSObject rssObject;
    String firstName,lastName,imgUrl;

    //Rss link

    private final String RSS_LINK = "http://rss.nytimes.com/services/xml/rss/nyt/Science.xml";
    private final String RSS_TO_JSON_API = "https://api.rss2json.com/v1/api.json?rss_url=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_login);
        Bundle inBundle = getIntent().getExtras();
        firstName = inBundle.getString("name");
        lastName = inBundle.getString("surname");
        imgUrl = inBundle.getString("imageUrl");

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

        loadRSS();
    }

    private void loadRSS() {
        AsyncTask<String,String,String> loadRssAsync = new AsyncTask<String, String, String>() {
            ProgressDialog mDialog = new ProgressDialog(MainActivity.this);

            @Override
            protected void onPreExecute() {
                mDialog.setMessage("Пожалуста, подождите...");
                mDialog.show();
            }

            @Override
            protected String doInBackground(String... params) {
               String result;
                HTTPDatahandler http = new HTTPDatahandler();
                result = http.GetHttpData(params[0]);
                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                mDialog.dismiss();
                rssObject = new Gson().fromJson(s, RSSObject.class);
                Feedadapter feedadapter = new Feedadapter(rssObject,getBaseContext());
                recyclerView.setAdapter(feedadapter);
                feedadapter.notifyDataSetChanged();
            }
        };
        StringBuilder url_get_data = new StringBuilder(RSS_TO_JSON_API);
        url_get_data.append(RSS_LINK);
        loadRssAsync.execute(url_get_data.toString());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_refesh)
            loadRSS();
        return true;
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
