package com.kosi0917.textandfacerecognitionapp.ui.Activity.FBActivities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kosi0917.textandfacerecognitionapp.Adapter.FacebookImgAdapter;
import com.kosi0917.textandfacerecognitionapp.Common.manager.MyFragmentManager;
import com.kosi0917.textandfacerecognitionapp.Model.sentiment.Documents;
import com.kosi0917.textandfacerecognitionapp.analyze.ImagesAnalyzer.ImageActivities.StatisticsGraphicsActivity;
import com.kosi0917.textandfacerecognitionapp.Model.facebook.DatFeed;
import com.kosi0917.textandfacerecognitionapp.Model.facebook.Datum;
import com.kosi0917.textandfacerecognitionapp.Model.facebook.GroupEntity;
import com.kosi0917.textandfacerecognitionapp.Model.facebook.RootFeed;
import com.kosi0917.textandfacerecognitionapp.Model.facebook.RootImgFeed;
import com.kosi0917.textandfacerecognitionapp.ProfileActivity;
import com.kosi0917.textandfacerecognitionapp.R;
import com.kosi0917.textandfacerecognitionapp.analyze.sentiment.GetSentiment;
import com.microsoft.projectoxford.emotion.contract.RecognizeResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

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
    String data, firstName, lastName, imgUrl, dataMessage, groupInfoJson;
    Documents documents = new Documents();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottom_navigation_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ic_arrow: {
                // do your sign-out stuff
                break;
            }
            // case blocks for other MenuItems (if any)
        }
        return false;
    }

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
                        switch (item.getItemId()) {
                            case R.id.ic_arrow:
                                goMainScreen();
                                break;
                            case R.id.ic_start_analyze:
                                goAnliseScreen();
                                break;
                            case R.id.ic_backup:
                                goPostAnliseScreen();
                                break;
                        }
                        return false;
                    }
                });


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false);
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
                //Commented until Facebook change privacy policy
                /*Bundle b = new Bundle();
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
                ).executeAsync();*/
                try {
                    JSONObject obj = new JSONObject("{\n" +
                            "  \"data\": [\n" +
                            "    {\n" +
                            "      \"id\": \"1995900030677807_2025894434345033\",\n" +
                            "      \"attachments\": {\n" +
                            "        \"data\": [\n" +
                            "          {\n" +
                            "            \"description\": \"И еще одно лицо\",\n" +
                            "            \"media\": {\n" +
                            "              \"image\": {\n" +
                            "                \"height\": 456,\n" +
                            "                \"src\": \"https://scontent.xx.fbcdn.net/v/t1.0-9/27540063_175900063184552_3714493698155537382_n.jpg?_nc_cat=0&oh=924fd42c46b6c0e628c10fa4e033b7e3&oe=5B2E66FE\",\n" +
                            "                \"width\": 456\n" +
                            "              }\n" +
                            "            },\n" +
                            "            \"target\": {\n" +
                            "              \"id\": \"175900063184552\",\n" +
                            "              \"url\": \"https://www.facebook.com/photo.php?fbid=175900063184552&set=gm.2025894434345033&type=3\"\n" +
                            "            },\n" +
                            "            \"type\": \"photo\",\n" +
                            "            \"url\": \"https://www.facebook.com/photo.php?fbid=175900063184552&set=gm.2025894434345033&type=3\"\n" +
                            "          }\n" +
                            "        ]\n" +
                            "      }\n" +
                            "    },\n" +
                            "    {\n" +
                            "      \"id\": \"1995900030677807_2025894317678378\",\n" +
                            "      \"attachments\": {\n" +
                            "        \"data\": [\n" +
                            "          {\n" +
                            "            \"description\": \"Добавление больших лиц\",\n" +
                            "            \"media\": {\n" +
                            "              \"image\": {\n" +
                            "                \"height\": 637,\n" +
                            "                \"src\": \"https://scontent.xx.fbcdn.net/v/t1.0-9/27540279_175899986517893_2236527829484773815_n.jpg?_nc_cat=0&oh=d26bf4b54771a2a2caa3c3a88a94db79&oe=5B2AEC95\",\n" +
                            "                \"width\": 640\n" +
                            "              }\n" +
                            "            },\n" +
                            "            \"target\": {\n" +
                            "              \"id\": \"175899986517893\",\n" +
                            "              \"url\": \"https://www.facebook.com/photo.php?fbid=175899986517893&set=gm.2025894317678378&type=3\"\n" +
                            "            },\n" +
                            "            \"type\": \"photo\",\n" +
                            "            \"url\": \"https://www.facebook.com/photo.php?fbid=175899986517893&set=gm.2025894317678378&type=3\"\n" +
                            "          }\n" +
                            "        ]\n" +
                            "      }\n" +
                            "    },\n" +
                            "    {\n" +
                            "      \"id\": \"1995900030677807_2025894201011723\",\n" +
                            "      \"attachments\": {\n" +
                            "        \"data\": [\n" +
                            "          {\n" +
                            "            \"description\": \"Новый тестовый пост\",\n" +
                            "            \"media\": {\n" +
                            "              \"image\": {\n" +
                            "                \"height\": 496,\n" +
                            "                \"src\": \"https://scontent.xx.fbcdn.net/v/t31.0-8/s720x720/27500967_175899769851248_4200472170430048554_o.jpg?_nc_cat=0&oh=32790c83ddd9ebde9ef5603a1f01af98&oe=5B63DD7F\",\n" +
                            "                \"width\": 720\n" +
                            "              }\n" +
                            "            },\n" +
                            "            \"target\": {\n" +
                            "              \"id\": \"175899769851248\",\n" +
                            "              \"url\": \"https://www.facebook.com/photo.php?fbid=175899769851248&set=gm.2025894201011723&type=3\"\n" +
                            "            },\n" +
                            "            \"type\": \"photo\",\n" +
                            "            \"url\": \"https://www.facebook.com/photo.php?fbid=175899769851248&set=gm.2025894201011723&type=3\"\n" +
                            "          }\n" +
                            "        ]\n" +
                            "      }\n" +
                            "    },\n" +
                            "    {\n" +
                            "      \"id\": \"1995900030677807_1996872567247220\",\n" +
                            "      \"attachments\": {\n" +
                            "        \"data\": [\n" +
                            "          {\n" +
                            "            \"description\": \"И последний тестовый пост\",\n" +
                            "            \"media\": {\n" +
                            "              \"image\": {\n" +
                            "                \"height\": 183,\n" +
                            "                \"src\": \"https://scontent.xx.fbcdn.net/v/t1.0-9/24862657_142047846569774_6591215018659456011_n.jpg?_nc_cat=0&oh=d6186f6c428592c02add81b83458a95a&oe=5B307C10\",\n" +
                            "                \"width\": 275\n" +
                            "              }\n" +
                            "            },\n" +
                            "            \"target\": {\n" +
                            "              \"id\": \"142047846569774\",\n" +
                            "              \"url\": \"https://www.facebook.com/photo.php?fbid=142047846569774&set=gm.1996872567247220&type=3\"\n" +
                            "            },\n" +
                            "            \"type\": \"photo\",\n" +
                            "            \"url\": \"https://www.facebook.com/photo.php?fbid=142047846569774&set=gm.1996872567247220&type=3\"\n" +
                            "          }\n" +
                            "        ]\n" +
                            "      }\n" +
                            "    },\n" +
                            "    {\n" +
                            "      \"id\": \"1995900030677807_1996872207247256\",\n" +
                            "      \"attachments\": {\n" +
                            "        \"data\": [\n" +
                            "          {\n" +
                            "            \"description\": \"Тестовый пост.\",\n" +
                            "            \"media\": {\n" +
                            "              \"image\": {\n" +
                            "                \"height\": 194,\n" +
                            "                \"src\": \"https://scontent.xx.fbcdn.net/v/t1.0-9/24775078_142047273236498_8425383663097165177_n.jpg?_nc_cat=0&oh=8e6053b048758e9a7a05eb71ddb9d5a7&oe=5B6D7504\",\n" +
                            "                \"width\": 259\n" +
                            "              }\n" +
                            "            },\n" +
                            "            \"target\": {\n" +
                            "              \"id\": \"142047273236498\",\n" +
                            "              \"url\": \"https://www.facebook.com/photo.php?fbid=142047273236498&set=gm.1996872207247256&type=3\"\n" +
                            "            },\n" +
                            "            \"type\": \"photo\",\n" +
                            "            \"url\": \"https://www.facebook.com/photo.php?fbid=142047273236498&set=gm.1996872207247256&type=3\"\n" +
                            "          }\n" +
                            "        ]\n" +
                            "      }\n" +
                            "    },\n" +
                            "    {\n" +
                            "      \"id\": \"1995900030677807_1996871993913944\",\n" +
                            "      \"attachments\": {\n" +
                            "        \"data\": [\n" +
                            "          {\n" +
                            "            \"description\": \"Хороший день. Облачно. Возможны осадки.\",\n" +
                            "            \"media\": {\n" +
                            "              \"image\": {\n" +
                            "                \"height\": 250,\n" +
                            "                \"src\": \"https://scontent.xx.fbcdn.net/v/t1.0-9/24862605_142047103236515_2196739627047104392_n.jpg?_nc_cat=0&oh=3bb57de93f9892b0dcf082f4f34bbe5f&oe=5B6A33FF\",\n" +
                            "                \"width\": 202\n" +
                            "              }\n" +
                            "            },\n" +
                            "            \"target\": {\n" +
                            "              \"id\": \"142047103236515\",\n" +
                            "              \"url\": \"https://www.facebook.com/photo.php?fbid=142047103236515&set=gm.1996871993913944&type=3\"\n" +
                            "            },\n" +
                            "            \"type\": \"photo\",\n" +
                            "            \"url\": \"https://www.facebook.com/photo.php?fbid=142047103236515&set=gm.1996871993913944&type=3\"\n" +
                            "          }\n" +
                            "        ]\n" +
                            "      }\n" +
                            "    },\n" +
                            "    {\n" +
                            "      \"id\": \"1995900030677807_1996871320580678\",\n" +
                            "      \"attachments\": {\n" +
                            "        \"data\": [\n" +
                            "          {\n" +
                            "            \"description\": \"Еще одна публикация. С тестовой эмоцией. Посмотрим, что выйдет.\",\n" +
                            "            \"media\": {\n" +
                            "              \"image\": {\n" +
                            "                \"height\": 183,\n" +
                            "                \"src\": \"https://scontent.xx.fbcdn.net/v/t1.0-9/24852566_142046396569919_3644381466357605349_n.jpg?_nc_cat=0&oh=5b18d49ae52502236e4f99c0d3cf3e37&oe=5B364A2B\",\n" +
                            "                \"width\": 275\n" +
                            "              }\n" +
                            "            },\n" +
                            "            \"target\": {\n" +
                            "              \"id\": \"142046396569919\",\n" +
                            "              \"url\": \"https://www.facebook.com/photo.php?fbid=142046396569919&set=gm.1996871320580678&type=3\"\n" +
                            "            },\n" +
                            "            \"type\": \"photo\",\n" +
                            "            \"url\": \"https://www.facebook.com/photo.php?fbid=142046396569919&set=gm.1996871320580678&type=3\"\n" +
                            "          }\n" +
                            "        ]\n" +
                            "      }\n" +
                            "    },\n" +
                            "    {\n" +
                            "      \"id\": \"1995900030677807_1996871103914033\",\n" +
                            "      \"attachments\": {\n" +
                            "        \"data\": [\n" +
                            "          {\n" +
                            "            \"description\": \"Тестовая эмоция. С тестовым текстом. Для проверки.\",\n" +
                            "            \"media\": {\n" +
                            "              \"image\": {\n" +
                            "                \"height\": 511,\n" +
                            "                \"src\": \"https://scontent.xx.fbcdn.net/v/t1.0-9/24796321_142045939903298_4206407193643141053_n.jpg?_nc_cat=0&oh=dac644e3ed9582ed9cbf1e2efdaeb366&oe=5B286F87\",\n" +
                            "                \"width\": 700\n" +
                            "              }\n" +
                            "            },\n" +
                            "            \"target\": {\n" +
                            "              \"id\": \"142045939903298\",\n" +
                            "              \"url\": \"https://www.facebook.com/photo.php?fbid=142045939903298&set=gm.1996871103914033&type=3\"\n" +
                            "            },\n" +
                            "            \"type\": \"photo\",\n" +
                            "            \"url\": \"https://www.facebook.com/photo.php?fbid=142045939903298&set=gm.1996871103914033&type=3\"\n" +
                            "          }\n" +
                            "        ]\n" +
                            "      }\n" +
                            "    },\n" +
                            "    {\n" +
                            "      \"id\": \"1995900030677807_1996068073994336\"\n" +
                            "    },\n" +
                            "    {\n" +
                            "      \"id\": \"1995900030677807_1996058920661918\"\n" +
                            "    },\n" +
                            "    {\n" +
                            "      \"id\": \"1995900030677807_1995929894008154\"\n" +
                            "    },\n" +
                            "    {\n" +
                            "      \"id\": \"1995900030677807_1995919017342575\"\n" +
                            "    },\n" +
                            "    {\n" +
                            "      \"id\": \"1995900030677807_1995918907342586\"\n" +
                            "    },\n" +
                            "    {\n" +
                            "      \"id\": \"1995900030677807_1995908784010265\"\n" +
                            "    },\n" +
                            "    {\n" +
                            "      \"id\": \"1995900030677807_1995908584010285\"\n" +
                            "    },\n" +
                            "    {\n" +
                            "      \"id\": \"1995900030677807_1995907640677046\"\n" +
                            "    },\n" +
                            "    {\n" +
                            "      \"id\": \"1995900030677807_1995900614011082\",\n" +
                            "      \"attachments\": {\n" +
                            "        \"data\": [\n" +
                            "          {\n" +
                            "            \"description\": \"Тестовый текст, который я здесь пишу.\",\n" +
                            "            \"media\": {\n" +
                            "              \"image\": {\n" +
                            "                \"height\": 720,\n" +
                            "                \"src\": \"https://scontent.xx.fbcdn.net/v/t1.0-9/s720x720/24862325_140733963367829_1539081896761182930_n.jpg?_nc_cat=0&oh=cb642238c213a814ff9cee720b8c8535&oe=5B3057DA\",\n" +
                            "                \"width\": 524\n" +
                            "              }\n" +
                            "            },\n" +
                            "            \"target\": {\n" +
                            "              \"id\": \"140733963367829\",\n" +
                            "              \"url\": \"https://www.facebook.com/photo.php?fbid=140733963367829&set=gm.1995900614011082&type=3\"\n" +
                            "            },\n" +
                            "            \"type\": \"photo\",\n" +
                            "            \"url\": \"https://www.facebook.com/photo.php?fbid=140733963367829&set=gm.1995900614011082&type=3\"\n" +
                            "          }\n" +
                            "        ]\n" +
                            "      }\n" +
                            "    },\n" +
                            "    {\n" +
                            "      \"id\": \"1995900030677807_1995900374011106\"\n" +
                            "    },\n" +
                            "    {\n" +
                            "      \"id\": \"1995900030677807_1995900037344473\"\n" +
                            "    }\n" +
                            "  ],\n" +
                            "  \"paging\": {\n" +
                            "    \"previous\": \"https://graph.facebook.com/v2.12/1995900030677807/feed?fields=attachments&format=json&icon_size=16&since=1517658772&access_token=EAACEdEose0cBAD2kdjnjhVK0EM1NGSFSEwTtuq1fuubk088jJbISa3JZBJRNd7SAbWg1nZAAqtKhnB46COb1SdOROu754Yo9rvuQ0cvuMxzmK9ogmb6cvoC2iOu1vjdBXYS4eFNkWze9unZCpB4gECo4ZBYUFZCo5rpcqhHTF6AZCrL4ZAaiG7t3VOurLVhsyIZD&limit=25&__paging_token=enc_AdBJ2iHEzwaEMx6xRo4w7ffpgPG2j7V8dZBNVXnDAKCvxBW0jzoRPegI1SbzbGsMIvDde6BeJ4oCNqmehiGWgN5skZCgZAsnH65yq0wMmGutZCJjyAZDZD&__previous=1\",\n" +
                            "    \"next\": \"https://graph.facebook.com/v2.12/1995900030677807/feed?fields=attachments&format=json&icon_size=16&access_token=EAACEdEose0cBAD2kdjnjhVK0EM1NGSFSEwTtuq1fuubk088jJbISa3JZBJRNd7SAbWg1nZAAqtKhnB46COb1SdOROu754Yo9rvuQ0cvuMxzmK9ogmb6cvoC2iOu1vjdBXYS4eFNkWze9unZCpB4gECo4ZBYUFZCo5rpcqhHTF6AZCrL4ZAaiG7t3VOurLVhsyIZD&limit=25&until=1512546764&__paging_token=enc_AdBo33ZCbxAD9EpwYXSj9N664ietemaPbgtRW0E8PQLJRuKmT2JQCMJcZAD5SAHFmS1iuJFLQWAFgmaNbF5BLcbBgisUZBwbnZCW2LdVgTDDttREOAZDZD\"\n" +
                            "  }\n" +
                            "}");
                    if (obj.has("data")) {
                        data = obj.getString("data");
                        Log.e(TAG, data);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return data;
            }

            @Override
            protected void onPostExecute(String s) {
                mDialog.dismiss();
                Type collectionType = new TypeToken<List<DatFeed>>() {
                }.getType();
                List<DatFeed> data = new Gson().fromJson(s, collectionType);
                List<DatFeed> viewedData = new ArrayList<DatFeed>();
                for (int i = 0; i < data.size(); i++)
                    if (data.get(i).attachments != null)
                        viewedData.add(data.get(i));
                rootImgFeed = new RootImgFeed(viewedData);

                Type newsDescriptionType = new TypeToken<List<Datum>>() {
                }.getType();
                List<Datum> descriptionData = new Gson().fromJson(dataMessage, newsDescriptionType);
                rootFeed = new RootFeed(descriptionData);

                Type newGroupType = new TypeToken<GroupEntity>() {
                }.getType();
                GroupEntity groupData = new Gson().fromJson(groupInfoJson, newGroupType);

                int i = 0;
                for (DatFeed datFeed : rootImgFeed.getData())
                    documents.add(String.valueOf(i++), datFeed.getAttachments().getData().get(0).getDescription());
                FacebookImgAdapter feedadapter = new FacebookImgAdapter(rootImgFeed, rootFeed, processText(documents), groupData, getBaseContext());
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

    private void goAnliseScreen() {
        Intent intent = new Intent(this, StatisticsGraphicsActivity.class);
        intent.putExtra("data", data);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void goPostAnliseScreen() {
        Intent intent = new Intent(this, StatisticsGraphicsActivity.class);
        intent.putExtra("data", data);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private String processText(Documents documents) {
        //Create Async Task to Process Data
        final String[] res = {""};
        AsyncTask<String, String, String> loadDataAsync = new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {
                String result = "";
                try
                {
                    result = GetSentiment.getSentiment(documents);
                } catch (
                        Exception e)

                {
                    e.printStackTrace();
                }
                System.out.println(result);
                return result;
            }

            @Override
            protected void onPostExecute(String recognizeResults) {
                res[0] =recognizeResults;
            }

        };
        loadDataAsync.execute(/*inputStream*/);
        return res[0];
    }
}
