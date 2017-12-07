package com.kosi0917.textandfacerecognitionapp.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.kosi0917.textandfacerecognitionapp.FacebookLoginActivity;
import com.kosi0917.textandfacerecognitionapp.R;
import com.vk.sdk.VKSdk;

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
        String name = inBundle.getString("name");
        String surname = inBundle.getString("surname");
        TextView nameView = (TextView)findViewById(R.id.VKNameAndSurname);
        nameView.setText("" + name + " " + surname);
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
