package com.kosi0917.textandfacerecognitionapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.kosi0917.textandfacerecognitionapp.ui.activity.FBActivities.FacebookImgNewsActivity;
import com.kosi0917.textandfacerecognitionapp.ui.activity.FBActivities.FacebookLoginActivity;
import com.kosi0917.textandfacerecognitionapp.ui.activity.FBActivities.FacebookNewsActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kosi0917 on 05-Dec-17.
 */


public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private ShareDialog shareDialog;
    private String name, surname, imageUrl, userId;
    private String TAG = "ProfileActivity";
    private String data, groupInfoJson;
    private String dataBeginning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);


        Bundle inBundle = getIntent().getExtras();
        name = inBundle.getString("name");
        surname = inBundle.getString("surname");
        imageUrl = inBundle.getString("imageUrl");
        userId = inBundle.getString("uid");

        TextView nameView = (TextView)findViewById(R.id.nameAndSurname);
        nameView.setText("" + name + " " + surname);

        new DownloadImage((ImageView)findViewById(R.id.profileImage)).execute(imageUrl);
    }

    private void share(){
        shareDialog = new ShareDialog(this);
        List<String> ids = new ArrayList<String>();
        ids.add("{USER_ID}");
        ids.add("{USER_ID}");
        ids.add("{USER_ID}");

        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("https://vk.com/konstantinesp"))
                .setContentTitle("Страница разработчика Константина")
                .setContentDescription("Вы можете перейти по этой ссылке и задать мне вопрос.")
                //.setShareHashtag(new ShareHashtag.Builder().setHashtag("#sitepoint").build())
                .setPeopleIds(ids)
                .setPlaceId("{PLACE_ID}")
                .build();

        shareDialog.show(content);
    }

    private void getPosts(){
        goMainScreen();
    }

    private void getFeed() {
    /*   GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        Log.e(TAG,response.toString());
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,feed");
        request.setParameters(parameters);
        request.executeAsync();
    }
*/
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
                                goFbNewsScreen(data);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).executeAsync();
    }

    //Переход к странице с постами
    public void getFeedWithPick() {
        Bundle b = new Bundle();
        Bundle addFields = new Bundle();

        //Commented per Facebook change policy
     /*   addFields.putString("fields","icon,id,name,privacy");
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "1995900030677807",
                addFields,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        Log.e(TAG,response.toString());
                        try {
                            JSONObject obj = new JSONObject(response.getRawResponse());
                            groupInfoJson = obj.toString();
                            Log.e(TAG,groupInfoJson);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).executeAsync();*/

        //hack
        try {
            JSONObject obj = new JSONObject("{\n" +
                    "  \"icon\": \"https://static.xx.fbcdn.net/rsrc.php/v3/yF/r/MzwrKZOhtIS.png\",\n" +
                    "  \"id\": \"1995900030677807\",\n" +
                    "  \"name\": \"Тестовая группа приложения textAndFaceRecognition\",\n" +
                    "  \"privacy\": \"OPEN\"\n" +
                    "}");
            groupInfoJson = obj.toString();
            Log.e(TAG, groupInfoJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        /*Получение постов
        * Получение вложений и общей информации через Graph Api
         */
      /*  b.putString("fields", "attachments");
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "1995900030677807/feed",
                b,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        Log.e(TAG, response.toString());
                        try {
                            JSONObject obj = new JSONObject(response.getRawResponse());
                            if (obj.has("data")) {
                                data = obj.getString("data");
                                Log.e(TAG, data);

                                //Получение общей информации о посте onSuccess()
                                new GraphRequest(
                                        AccessToken.getCurrentAccessToken(),
                                        "1995900030677807/feed",
                                        null,
                                        HttpMethod.GET,
                                        new GraphRequest.Callback() {
                                            public void onCompleted(GraphResponse response) {
                                                Log.e(TAG, response.toString());
                                                try {
                                                    JSONObject obj = new JSONObject(response.getRawResponse());
                                                    if (obj.has("data")) {
                                                        dataBeginning = obj.getString("data");
                                                        Log.e(TAG, dataBeginning);
                                                        goFbNewsScreenWithPick(data, dataBeginning, groupInfoJson);
                                                    }
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                ).executeAsync();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).executeAsync();*/

        //hack
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
                try {
                    JSONObject objBeg = new JSONObject("{\n" +
                            "  \"data\": [\n" +
                            "    {\n" +
                            "      \"message\": \"И еще одно лицо\",\n" +
                            "      \"updated_time\": \"2018-02-03T11:52:52+0000\",\n" +
                            "      \"id\": \"1995900030677807_2025894434345033\"\n" +
                            "    },\n" +
                            "    {\n" +
                            "      \"message\": \"Добавление больших лиц\",\n" +
                            "      \"updated_time\": \"2018-02-03T11:52:36+0000\",\n" +
                            "      \"id\": \"1995900030677807_2025894317678378\"\n" +
                            "    },\n" +
                            "    {\n" +
                            "      \"message\": \"Новый тестовый пост\",\n" +
                            "      \"updated_time\": \"2018-02-03T11:52:13+0000\",\n" +
                            "      \"id\": \"1995900030677807_2025894201011723\"\n" +
                            "    },\n" +
                            "    {\n" +
                            "      \"message\": \"И последний тестовый пост\",\n" +
                            "      \"updated_time\": \"2017-12-08T07:44:12+0000\",\n" +
                            "      \"id\": \"1995900030677807_1996872567247220\"\n" +
                            "    },\n" +
                            "    {\n" +
                            "      \"message\": \"Тестовый пост.\",\n" +
                            "      \"updated_time\": \"2017-12-08T07:43:33+0000\",\n" +
                            "      \"id\": \"1995900030677807_1996872207247256\"\n" +
                            "    },\n" +
                            "    {\n" +
                            "      \"message\": \"Хороший день. Облачно. Возможны осадки.\",\n" +
                            "      \"updated_time\": \"2017-12-08T07:43:00+0000\",\n" +
                            "      \"id\": \"1995900030677807_1996871993913944\"\n" +
                            "    },\n" +
                            "    {\n" +
                            "      \"message\": \"Еще одна публикация. С тестовой эмоцией. Посмотрим, что выйдет.\",\n" +
                            "      \"updated_time\": \"2017-12-08T07:41:18+0000\",\n" +
                            "      \"id\": \"1995900030677807_1996871320580678\"\n" +
                            "    },\n" +
                            "    {\n" +
                            "      \"message\": \"Тестовая эмоция. С тестовым текстом. Для проверки.\",\n" +
                            "      \"updated_time\": \"2017-12-08T07:40:23+0000\",\n" +
                            "      \"id\": \"1995900030677807_1996871103914033\"\n" +
                            "    },\n" +
                            "    {\n" +
                            "      \"message\": \"Как здорово, что этот пост такой классный) Тестим, как можем.\",\n" +
                            "      \"updated_time\": \"2017-12-06T15:51:36+0000\",\n" +
                            "      \"id\": \"1995900030677807_1996068073994336\"\n" +
                            "    },\n" +
                            "    {\n" +
                            "      \"message\": \"Я люблю Netcracker, но это не точно\",\n" +
                            "      \"updated_time\": \"2017-12-06T15:27:43+0000\",\n" +
                            "      \"id\": \"1995900030677807_1996058920661918\"\n" +
                            "    },\n" +
                            "    {\n" +
                            "      \"story\": \"Константин Сиволапов changed the privacy setting of Тестовая группа приложения textAndFaceRecognition from Closed to Public.\",\n" +
                            "      \"updated_time\": \"2017-12-06T09:41:21+0000\",\n" +
                            "      \"id\": \"1995900030677807_1995929894008154\"\n" +
                            "    },\n" +
                            "    {\n" +
                            "      \"story\": \"Константин Сиволапов changed the privacy setting of Тестовая группа приложения textAndFaceRecognition from Public to Closed.\",\n" +
                            "      \"updated_time\": \"2017-12-06T08:58:11+0000\",\n" +
                            "      \"id\": \"1995900030677807_1995919017342575\"\n" +
                            "    },\n" +
                            "    {\n" +
                            "      \"story\": \"Константин Сиволапов changed the privacy setting of Тестовая группа приложения textAndFaceRecognition from Closed to Public.\",\n" +
                            "      \"updated_time\": \"2017-12-06T08:57:26+0000\",\n" +
                            "      \"id\": \"1995900030677807_1995918907342586\"\n" +
                            "    },\n" +
                            "    {\n" +
                            "      \"story\": \"Константин Сиволапов changed the privacy setting of Тестовая группа приложения textAndFaceRecognition from Public to Closed.\",\n" +
                            "      \"updated_time\": \"2017-12-06T08:28:33+0000\",\n" +
                            "      \"id\": \"1995900030677807_1995908784010265\"\n" +
                            "    },\n" +
                            "    {\n" +
                            "      \"story\": \"Константин Сиволапов changed the privacy setting of Тестовая группа приложения textAndFaceRecognition from Closed to Public.\",\n" +
                            "      \"updated_time\": \"2017-12-06T08:27:54+0000\",\n" +
                            "      \"id\": \"1995900030677807_1995908584010285\"\n" +
                            "    },\n" +
                            "    {\n" +
                            "      \"story\": \"Константин Сиволапов changed the privacy setting of Тестовая группа приложения textAndFaceRecognition from Secret to Closed.\",\n" +
                            "      \"updated_time\": \"2017-12-06T08:24:06+0000\",\n" +
                            "      \"id\": \"1995900030677807_1995907640677046\"\n" +
                            "    },\n" +
                            "    {\n" +
                            "      \"message\": \"Тестовый текст, который я здесь пишу.\",\n" +
                            "      \"updated_time\": \"2017-12-06T07:55:06+0000\",\n" +
                            "      \"id\": \"1995900030677807_1995900614011082\"\n" +
                            "    },\n" +
                            "    {\n" +
                            "      \"message\": \"Здесь публикуются отзывы и фотографии о тестовых вещах.\",\n" +
                            "      \"story\": \"Константин Сиволапов updated the description of the group Тестовая группа приложения textAndFaceRecognition.\",\n" +
                            "      \"updated_time\": \"2017-12-06T07:54:10+0000\",\n" +
                            "      \"id\": \"1995900030677807_1995900374011106\"\n" +
                            "    },\n" +
                            "    {\n" +
                            "      \"story\": \"Константин Сиволапов created the group Тестовая группа приложения textAndFaceRecognition.\",\n" +
                            "      \"updated_time\": \"2017-12-06T07:52:44+0000\",\n" +
                            "      \"id\": \"1995900030677807_1995900037344473\"\n" +
                            "    }\n" +
                            "  ],\n" +
                            "  \"paging\": {\n" +
                            "    \"previous\": \"https://graph.facebook.com/v2.12/1995900030677807/feed?format=json&icon_size=16&since=1517658772&access_token=EAACEdEose0cBAD2kdjnjhVK0EM1NGSFSEwTtuq1fuubk088jJbISa3JZBJRNd7SAbWg1nZAAqtKhnB46COb1SdOROu754Yo9rvuQ0cvuMxzmK9ogmb6cvoC2iOu1vjdBXYS4eFNkWze9unZCpB4gECo4ZBYUFZCo5rpcqhHTF6AZCrL4ZAaiG7t3VOurLVhsyIZD&limit=25&__paging_token=enc_AdBJ2iHEzwaEMx6xRo4w7ffpgPG2j7V8dZBNVXnDAKCvxBW0jzoRPegI1SbzbGsMIvDde6BeJ4oCNqmehiGWgN5skZCgZAsnH65yq0wMmGutZCJjyAZDZD&__previous=1\",\n" +
                            "    \"next\": \"https://graph.facebook.com/v2.12/1995900030677807/feed?format=json&icon_size=16&access_token=EAACEdEose0cBAD2kdjnjhVK0EM1NGSFSEwTtuq1fuubk088jJbISa3JZBJRNd7SAbWg1nZAAqtKhnB46COb1SdOROu754Yo9rvuQ0cvuMxzmK9ogmb6cvoC2iOu1vjdBXYS4eFNkWze9unZCpB4gECo4ZBYUFZCo5rpcqhHTF6AZCrL4ZAaiG7t3VOurLVhsyIZD&limit=25&until=1512546764&__paging_token=enc_AdBo33ZCbxAD9EpwYXSj9N664ietemaPbgtRW0E8PQLJRuKmT2JQCMJcZAD5SAHFmS1iuJFLQWAFgmaNbF5BLcbBgisUZBwbnZCW2LdVgTDDttREOAZDZD\"\n" +
                            "  }\n" +
                            "}");
                    if (objBeg.has("data")) {
                        dataBeginning = objBeg.getString("data");
                        Log.e(TAG, dataBeginning);
                        goFbNewsScreenWithPick(data, dataBeginning, groupInfoJson);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void logout(){
        LoginManager.getInstance().logOut();
        Intent login = new Intent(ProfileActivity.this, FacebookLoginActivity.class);
        startActivity(login);
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.share:
                share();
                break;

           // case R.id.getPosts:
             //   getPosts();
              //  break;

            case R.id.logout:
                logout();
                break;
            case R.id.getNews:
                getFeed();
                break;
            case R.id.getPickNews:
                getFeedWithPick();
                break;
        }
    }

    public static class DownloadImage extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImage(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    private void goMainScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("surname", surname);
        intent.putExtra("imageUrl", imageUrl);
        startActivity(intent);
        finish();
    }

    private void goFbNewsScreen(String data) {
        Intent intent = new Intent(this, FacebookNewsActivity.class);
        intent.putExtra("data",data);
        intent.putExtra("name", name);
        intent.putExtra("surname", surname);
        intent.putExtra("imageUrl", imageUrl);
        startActivity(intent);
        finish();
    }

    private void goFbNewsScreenWithPick(String data,String dataBeginning, String groupInfoJson) {
        Intent intent = new Intent(this, FacebookImgNewsActivity.class);

        intent.putExtra("data",data);
        intent.putExtra("dataMessages", dataBeginning);
        intent.putExtra("name", name);
        intent.putExtra("surname", surname);
        intent.putExtra("imageUrl", imageUrl);
        intent.putExtra("groupInfoJson",groupInfoJson);
        startActivity(intent);
        finish();
    }
}
