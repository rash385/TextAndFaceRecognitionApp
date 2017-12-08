package com.kosi0917.textandfacerecognitionapp.Activity;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kosi0917.textandfacerecognitionapp.FacebookLoginActivity;
import com.kosi0917.textandfacerecognitionapp.ProfileActivity;
import com.kosi0917.textandfacerecognitionapp.R;
import com.kosi0917.textandfacerecognitionapp.Tools.DownloadImage;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiCommunityFull;
import com.vk.sdk.api.model.VKApiPhoto;
import com.vk.sdk.api.model.VKAttachments;
import com.vk.sdk.api.model.VKList;
import com.vk.sdk.api.model.VKPhotoArray;
import com.vk.sdk.api.model.VKWallPostResult;
import com.vk.sdk.api.photo.VKImageParameters;
import com.vk.sdk.api.photo.VKUploadImage;
import com.vk.sdk.dialogs.VKShareDialog;
import com.vk.sdk.dialogs.VKShareDialogBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lawrence on 07.12.2017.
 */

public class VKProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private String OWNER_ID;
    List<String> list;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vk_profile_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle inBundle = getIntent().getExtras();
        if (inBundle != null) {
            OWNER_ID = inBundle.getString("owner_id");
            String name = inBundle.getString("name");
            String surname = inBundle.getString("surname");
            String photo_max_orig = inBundle.getString("photo_max_orig");
            TextView nameView = (TextView) findViewById(R.id.VKNameAndSurname);
            nameView.setText("" + name + " " + surname);

            new DownloadImage((ImageView) findViewById(R.id.VKProfileImage)).execute(photo_max_orig);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.VKGetAllPhoto:
                getAllPhoto();
                break;

            case R.id.VKShare:
                share();
                break;

            case R.id.VKLogout:
                logout();
                break;
        }
    }

    private void share() {
        VKPhotoArray photos = new VKPhotoArray();
        photos.add(new VKApiPhoto("photo-47200925_314622346"));
        VKShareDialogBuilder builder = new VKShareDialogBuilder();
        builder.setText("I created this post with VK Android SDK" +
                "\nSee additional information below\n#vksdk");
        builder.setUploadedPhotos(photos);
        builder.setAttachmentLink("VK Android SDK information",
                "https://vk.com/dev/android_sdk");
        builder.setShareDialogListener(new VKShareDialog.VKShareDialogListener() {
            @Override
            public void onVkShareComplete(int postId) {
                // recycle bitmap if need
            }
            @Override
            public void onVkShareCancel() {
                // recycle bitmap if need
            }
            @Override
            public void onVkShareError(VKError error) {
                // recycle bitmap if need
            }
        }).show(getFragmentManager(), "VK_SHARE_DIALOG");
    }

    private void getAllPhoto() {
         VKRequest request = new VKRequest("photos.getAll", VKParameters.from(VKApiConst.OWNER_ID, OWNER_ID), VKPhotoArray.class);
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                try {
                    list = new ArrayList<>();
                    JSONObject jsonObject = (JSONObject) response.json.get("response");
                    JSONArray jsonArray = (JSONArray) jsonObject.get("items");
                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject post = (JSONObject) jsonArray.get(i);
                        //System.out.println(String.valueOf(post));
                        System.out.println(post.get("photo_604"));
                        list.add((String) post.get("photo_604"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //VKList list = (VKList) response.parsedModel;
                new DownloadImage((ImageView) findViewById(R.id.VKProfileImage)).execute(list.get(3));
                //System.out.println(list.get(2));
                Toast.makeText(VKProfileActivity.this, "Кол-во фоток: "+ list.size(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void logout(){
        VKSdk.logout();
        Intent login = new Intent(VKProfileActivity.this, FacebookLoginActivity.class);
        startActivity(login);
        finish();
    }


}
