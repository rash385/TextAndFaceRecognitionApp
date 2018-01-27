package com.kosi0917.textandfacerecognitionapp.FBActivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.kosi0917.textandfacerecognitionapp.InstagramActivities.FeedActivity;
import com.kosi0917.textandfacerecognitionapp.InstagramActivities.InstagramLogin;
import com.kosi0917.textandfacerecognitionapp.InstagramLib.AuthenticationDialog;
import com.kosi0917.textandfacerecognitionapp.Interface.AuthenticationListener;
import com.kosi0917.textandfacerecognitionapp.MainActivity;
import com.kosi0917.textandfacerecognitionapp.ProfileActivity;
import com.kosi0917.textandfacerecognitionapp.R;
import com.kosi0917.textandfacerecognitionapp.ui.Activity.VKLoginActivity;
import com.vk.sdk.util.VKUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

public class FacebookLoginActivity extends AppCompatActivity implements  AuthenticationListener {

    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private String firstName,lastName, email,birthday,gender;
    private URL profilePicture;
    private String userId;
    private String TAG = "LoginActivity";
    private AuthenticationDialog auth_dialog;
    private Button btn_get_access_token;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getApplicationContext());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_facebook);

        String[] fingerprints = VKUtil.getCertificateFingerprint(this, this.getPackageName());
        Log.d("MainActivity", "Fingerprint: " + Arrays.toString(fingerprints));

        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.loginButton);

        btn_get_access_token = (Button) findViewById(R.id.btn_instagram_login);
        btn_get_access_token.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth_dialog = new AuthenticationDialog(FacebookLoginActivity.this, FacebookLoginActivity.this);
                auth_dialog.setCancelable(true);
                auth_dialog.show();
            }
        });

        FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.e(TAG, object.toString());
                        Log.e(TAG, response.toString());

                        try {
                            userId = object.getString("id");
                            profilePicture = new URL("https://graph.facebook.com/" + userId + "/picture?width=500&height=500");
                            if (object.has("first_name"))
                                firstName = object.getString("first_name");
                            if (object.has("last_name"))
                                lastName = object.getString("last_name");
                            if (object.has("email"))
                                email = object.getString("email");
                            if (object.has("gender"))
                                gender = object.getString("gender");

                            Intent main = new Intent(FacebookLoginActivity.this, ProfileActivity.class);
                            main.putExtra("name", firstName);
                            main.putExtra("surname", lastName);
                            main.putExtra("imageUrl", profilePicture.toString());
                            main.putExtra("uid", userId);
                            startActivity(main);
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name, email, gender , location");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException e) {
                e.printStackTrace();
            }
        };
        loginButton.registerCallback(callbackManager, callback);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    private void goMainScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void onMyButtonClick(View view)
    {
        Intent intent = new Intent(this, VKLoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    private void goInstagramScreen(){
        Intent intent = new Intent(this, InstagramLogin.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onCodeReceived(String auth_token) {
        if (auth_token == null) {
            auth_dialog.dismiss();
        }
        Intent i = new Intent(FacebookLoginActivity.this, FeedActivity.class);
        i.putExtra("access_token", auth_token);
        startActivity(i);
    }
}
