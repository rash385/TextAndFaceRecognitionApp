package com.kosi0917.textandfacerecognitionapp.ui.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kosi0917.textandfacerecognitionapp.FBActivities.FacebookLoginActivity;
import com.kosi0917.textandfacerecognitionapp.Model.VK.Photo;
import com.kosi0917.textandfacerecognitionapp.Model.VK.VkModel;
import com.kosi0917.textandfacerecognitionapp.R;
import com.kosi0917.textandfacerecognitionapp.Tools.DownloadImage;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiPhoto;
import com.vk.sdk.api.model.VKPhotoArray;
import com.vk.sdk.dialogs.VKShareDialog;
import com.vk.sdk.dialogs.VKShareDialogBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lawrence on 07.12.2017.
 */

public class VKProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private String OWNER_ID;
    List<String> list;
    List<Photo> photoListFromWall;
    List<VkModel> wallList;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vk_profile_activity);
   //     Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
 //       setSupportActionBar(toolbar);
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

            case R.id.VKGetWall:
                getWall();
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

    private void getWall() {
        VKRequest request = new VKRequest("wall.get", VKParameters.from(VKApiConst.OWNER_ID, -158518042, VKApiConst.COUNT, 3), VKPhotoArray.class);
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                try {
                    JSONObject jsonObject = (JSONObject) response.json.get("response");
                    JSONArray jsonArray = (JSONArray) jsonObject.get("items");
                    wallList = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        VkModel vkModel = new VkModel();
                        JSONObject post = (JSONObject) jsonArray.get(i);
                        System.out.println("Наш пост " + String.valueOf(post));
                        JSONArray attachments = (JSONArray) post.get("attachments");
                        System.out.println(attachments.toString());
                        Photo photo = new Photo();
                        photoListFromWall = new ArrayList<>();
                        for (int j = 0; j < attachments.length(); j++) {
                            JSONObject attachmentObject = (JSONObject) attachments.get(j);
                            System.out.println(attachmentObject.get("photo"));
                            if (attachmentObject.get("type").equals("photo")) {
                                JSONObject attachmentPhoto = (JSONObject) attachmentObject.get("photo");

                                photo.setId(Integer.parseInt(attachmentPhoto.get("id").toString()));
                                photo.setOwnerId(Integer.parseInt(attachmentPhoto.get("owner_id").toString()));
                                photo.setPhoto75(attachmentPhoto.get("photo_75").toString());
                                photo.setPhoto130(attachmentPhoto.get("photo_130").toString());
                                photo.setPhoto604(attachmentPhoto.get("photo_604").toString());
                                photo.setPhoto807(attachmentPhoto.get("photo_807").toString());
                                photo.setPhoto1280(attachmentPhoto.get("photo_1280").toString());
                                photo.setDate(Integer.parseInt(attachmentPhoto.get("date").toString()));
                                photoListFromWall.add(photo);
                            }
                            System.out.println(photo.toString());
                        }
                        vkModel.setId(Integer.parseInt(post.get("id").toString()));
                        vkModel.setFrom_id(Integer.parseInt(post.get("from_id").toString()));
                        vkModel.setOwner_id(Integer.parseInt(post.get("owner_id").toString()));
                        vkModel.setDate(Integer.parseInt(post.get("date").toString()));
                        vkModel.setPost_type(post.get("post_type").toString());
                        vkModel.setText(post.get("text").toString());
                        vkModel.setAttachments(photoListFromWall);
                        // на другом уровне вложенности
                        //vkModel.setCountComments(Integer.parseInt(post.get("countComments").toString()));
                        //vkModel.setCountLikes(Integer.parseInt(post.get("countLikes").toString()));
                        //vkModel.setCountReports(Integer.parseInt(post.get("countReports").toString()));
                        System.out.println(vkModel.toString());
                        wallList.add(vkModel);

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

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
