package com.kosi0917.textandfacerecognitionapp.Activity;

import android.annotation.SuppressLint;
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

import com.kosi0917.textandfacerecognitionapp.FacebookLoginActivity;
import com.kosi0917.textandfacerecognitionapp.ProfileActivity;
import com.kosi0917.textandfacerecognitionapp.R;
import com.kosi0917.textandfacerecognitionapp.Tools.DownloadImage;
import com.vk.sdk.VKSdk;

import java.io.InputStream;

/**
 * Created by Lawrence on 07.12.2017.
 */

public class VKProfileActivity extends AppCompatActivity implements View.OnClickListener {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vk_profile_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle inBundle = getIntent().getExtras();
        if (inBundle != null) {
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
            case R.id.VKLogout:
                logout();
                break;
        }
    }

    private void logout(){
        VKSdk.logout();
        Intent login = new Intent(VKProfileActivity.this, FacebookLoginActivity.class);
        startActivity(login);
        finish();
    }


}
