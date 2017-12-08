package com.kosi0917.textandfacerecognitionapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import com.kosi0917.textandfacerecognitionapp.FBActivities.FacebookImgNewsActivity;
import com.kosi0917.textandfacerecognitionapp.FBActivities.FacebookLoginActivity;
import com.kosi0917.textandfacerecognitionapp.FBActivities.FacebookNewsActivity;

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
    private String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Bundle inBundle = getIntent().getExtras();
        name = inBundle.getString("name");
        surname = inBundle.getString("surname");
        imageUrl = inBundle.getString("imageUrl");
        userId = inBundle.getString("uid");

        TextView nameView = (TextView)findViewById(R.id.nameAndSurname);
        nameView.setText("" + name + " " + surname);

        new ProfileActivity.DownloadImage((ImageView)findViewById(R.id.profileImage)).execute(imageUrl);
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

    public void getFeedWithPick(){
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
                                goFbNewsScreenWithPick(data);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).executeAsync();
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

            case R.id.getPosts:
                getPosts();
                break;

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
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void goFbNewsScreen(String data) {
        Intent intent = new Intent(this, FacebookNewsActivity.class);
        intent.putExtra("data",data);
        startActivity(intent);
        finish();
    }

    private void goFbNewsScreenWithPick(String data) {
        Intent intent = new Intent(this, FacebookImgNewsActivity.class);
        intent.putExtra("data",data);
        startActivity(intent);
        finish();
    }
}
