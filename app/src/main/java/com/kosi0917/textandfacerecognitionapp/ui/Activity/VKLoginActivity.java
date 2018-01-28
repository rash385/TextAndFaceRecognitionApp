package com.kosi0917.textandfacerecognitionapp.ui.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.kosi0917.textandfacerecognitionapp.Application.Application;
import com.kosi0917.textandfacerecognitionapp.Model.VK.CurrentUser;
import com.kosi0917.textandfacerecognitionapp.R;
import com.kosi0917.textandfacerecognitionapp.consts.ApiConstants;
import com.kosi0917.textandfacerecognitionapp.mvp.presenter.MainPresenter;
import com.kosi0917.textandfacerecognitionapp.mvp.view.MainView;
import com.kosi0917.textandfacerecognitionapp.ui.Fragment.NewsFeedFragment;
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
import com.vk.sdk.util.VKUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

/**
 * Created by Lawrence on 06.12.2017.
 */

public class VKLoginActivity  extends BaseActivity implements MainView {

    @InjectPresenter
    MainPresenter mPresenter;

    //private String[] scope = new String[] {VKScope.WALL, VKScope.FRIENDS, VKScope.MESSAGES, VKScope.PHOTOS};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        String[] fingerprints = VKUtil.getCertificateFingerprint(this, this.getPackageName());
//        Log.d("MainActivity", "Fingerprint: " + Arrays.toString(fingerprints));
        Application.getApplicationComponent().inject(this);
        mPresenter.checkAuth();
    }

    @Override
    protected int getMainContentLayout() {
        return R.layout.vk_fragment_feed;
    }

    @Override
    public void startSignIn() {
        VKSdk.login(this, ApiConstants.scope);
    }

    @Override
    public void signedIn() {
        Toast.makeText(this, "Current user id: " + CurrentUser.getId(), Toast.LENGTH_LONG).show();
        setContent(new NewsFeedFragment());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                // Пользователь успешно авторизовался
                mPresenter.checkAuth();
            }

            @Override
            public void onError(VKError error) {
                // Произошла ошибка авторизации (например, пользователь запретил авторизацию)
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    //    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
//            @Override
//            public void onResult(VKAccessToken res) {
//            // Пользователь успешно авторизовался
//                mPresenter.checkAuth();
//               // Toast.makeText(getApplicationContext(), "Good", Toast.LENGTH_LONG).show();
//                String token = VKAccessToken.currentToken().accessToken;
//                VKParameters parameters = VKParameters.from(VKParameters.from(VKApiConst.ACCESS_TOKEN, token));
//                parameters.put(VKApiConst.FIELDS, "photo_max_orig");
//                parameters.put(VKApiConst.NAME_CASE, "nom");
//                VKRequest request = VKApi.users().get(parameters);
//                request.useSystemLanguage = true;
//                request.executeWithListener(new VKRequest.VKRequestListener()
//                {
//                    @Override
//                    public void onComplete(VKResponse response) {
//                        super.onComplete(response);
//                        try {
//                            JSONArray jsonArray = response.json.getJSONArray("response");
//                            JSONObject jsonObject =  jsonArray.getJSONObject(0);
//                            System.out.println(String.valueOf(jsonObject));
//                            String owner_id = jsonObject.getString("id");
//                            String first_name = jsonObject.getString("first_name");
//                            String last_name = jsonObject.getString("last_name");
//                            String photo_max_orig = jsonObject.getString("photo_max_orig");
//                            Intent intent = new Intent(VKLoginActivity.this, VKProfileActivity.class);
//                            intent.putExtra("owner_id", owner_id);
//                            intent.putExtra("name", first_name);
//                            intent.putExtra("surname", last_name);
//                            intent.putExtra("photo_max_orig", photo_max_orig);
//                            startActivity(intent);
//                            finish();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                });
//            }
//
//            @Override
//            public void onError(VKError error) {
//            // Произошла ошибка авторизации (например, пользователь запретил авторизацию)
//                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
//            }
//        })) {
//            super.onActivityResult(requestCode, resultCode, data);
//        }
//    }
}
