package com.kosi0917.textandfacerecognitionapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.kosi0917.textandfacerecognitionapp.FacebookLoginActivity;
import com.kosi0917.textandfacerecognitionapp.ProfileActivity;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKScopes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Lawrence on 06.12.2017.
 */

public class VKLoginActivity  extends AppCompatActivity {

    private String[] scope = new String[] {VKScope.WALL, VKScope.FRIENDS, VKScope.MESSAGES, VKScope.PHOTOS};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        VKSdk.login(this, scope);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
            // Пользователь успешно авторизовался
                Toast.makeText(getApplicationContext(), "Good", Toast.LENGTH_LONG).show();
                String token = VKAccessToken.currentToken().accessToken;
                VKParameters parameters = VKParameters.from(VKParameters.from(VKApiConst.ACCESS_TOKEN, token));
                parameters.put(VKApiConst.FIELDS, "photo_max_orig");
                parameters.put(VKApiConst.NAME_CASE, "nom");
                VKRequest request = VKApi.users().get(parameters);
                request.useSystemLanguage = true;
                request.executeWithListener(new VKRequest.VKRequestListener()
                {
                    @Override
                    public void onComplete(VKResponse response) {
                        super.onComplete(response);
                        try {
                            JSONArray jsonArray = response.json.getJSONArray("response");
                            JSONObject jsonObject =  jsonArray.getJSONObject(0);
                            System.out.println(String.valueOf(jsonObject));
                            String owner_id = jsonObject.getString("id");
                            String first_name = jsonObject.getString("first_name");
                            String last_name = jsonObject.getString("last_name");
                            String photo_max_orig = jsonObject.getString("photo_max_orig");
                            Intent intent = new Intent(VKLoginActivity.this, VKProfileActivity.class);
                            intent.putExtra("owner_id", owner_id);
                            intent.putExtra("name", first_name);
                            intent.putExtra("surname", last_name);
                            intent.putExtra("photo_max_orig", photo_max_orig);
                            startActivity(intent);
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
            @Override
            public void onError(VKError error) {
            // Произошла ошибка авторизации (например, пользователь запретил авторизацию)
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
