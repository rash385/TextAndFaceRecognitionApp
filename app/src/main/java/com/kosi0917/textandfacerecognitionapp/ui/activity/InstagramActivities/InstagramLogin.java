package com.kosi0917.textandfacerecognitionapp.ui.activity.InstagramActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.kosi0917.textandfacerecognitionapp.InstagramLib.AuthenticationDialog;
import com.kosi0917.textandfacerecognitionapp.Interface.AuthenticationListener;
import com.kosi0917.textandfacerecognitionapp.R;

/**
 * Created by sivko on 10.12.2017.
 */

public class InstagramLogin extends AppCompatActivity implements AuthenticationListener {
    private AuthenticationDialog auth_dialog;
    private Button btn_get_access_token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_facebook);
        btn_get_access_token = (Button) findViewById(R.id.btn_instagram_login);
        btn_get_access_token.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth_dialog = new AuthenticationDialog(InstagramLogin.this, InstagramLogin.this);
                auth_dialog.setCancelable(true);
                auth_dialog.show();
            }
        });
    }

    @Override
    public void onCodeReceived(String access_token) {
        if (access_token == null) {
            auth_dialog.dismiss();
        }

        Intent i = new Intent(InstagramLogin.this, FeedActivity.class);
        i.putExtra("access_token", access_token);
        startActivity(i);

    }
}

