package com.kosi0917.textandfacerecognitionapp.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kosi0917.textandfacerecognitionapp.Application.Application;
import com.kosi0917.textandfacerecognitionapp.Common.manager.MyFragmentManager;
import com.kosi0917.textandfacerecognitionapp.Common.manager.NetworkManager;
import com.kosi0917.textandfacerecognitionapp.Model.VK.CurrentUser;
import com.kosi0917.textandfacerecognitionapp.Model.VK.Profile;
import com.kosi0917.textandfacerecognitionapp.mvp.view.MainView;
import com.kosi0917.textandfacerecognitionapp.rest.api.UsersApi;
import com.kosi0917.textandfacerecognitionapp.rest.model.request.UsersGetRequestModel;
import com.kosi0917.textandfacerecognitionapp.ui.Fragment.BaseFragment;
import com.kosi0917.textandfacerecognitionapp.ui.Fragment.BoardFragment;
import com.kosi0917.textandfacerecognitionapp.ui.Fragment.InfoFragment;
import com.kosi0917.textandfacerecognitionapp.ui.Fragment.MembersFragment;
import com.kosi0917.textandfacerecognitionapp.ui.Fragment.MyPostsFragment;
import com.kosi0917.textandfacerecognitionapp.ui.Fragment.NewsFeedFragment;

import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmObject;

/**
 * Created by vibo0917 on 1/24/2018.
 */

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    @Inject
    UsersApi mUserApi;

    @Inject
    NetworkManager mNetworkManager;

    @Inject
    MyFragmentManager myFragmentManager;

    public MainPresenter() {
        Application.getApplicationComponent().inject(this);
    }

    public void checkAuth() {
        if (!CurrentUser.isAuthorized()) {
            getViewState().startSignIn();
        } else {
            getCurrentUser();
            getViewState().signedIn();
        }
    }

    public Observable<Profile> getProfileFromNetwork() {
        return mUserApi.get(new UsersGetRequestModel(CurrentUser.getId()).toMap())
                .flatMap(listFull -> Observable.fromIterable(listFull.response))
                .doOnNext(this::saveToDb);
    }

    private Observable<Profile> getProfileFromDb() {
        return Observable.fromCallable(getListFromRealmCallable());
    }

    public void saveToDb(RealmObject item) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> realm1.copyToRealmOrUpdate(item));
    }

    public Callable<Profile> getListFromRealmCallable() {
        return () -> {
            Realm realm = Realm.getDefaultInstance();
            Profile realmResults = realm.where(Profile.class)
                    .equalTo("id", Integer.parseInt(CurrentUser.getId()))
                    .findFirst();
            return realm.copyFromRealm(realmResults);
        };
    }

    private void getCurrentUser() {
        mNetworkManager.getNetworkObservable()
                .flatMap(aBoolean -> {
                    if (!CurrentUser.isAuthorized()) {
                        getViewState().startSignIn();
                    }

                    return aBoolean
                            ? getProfileFromNetwork()
                            : getProfileFromDb();
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(profile -> {
                    getViewState().showCurrentUser(profile);
                }, error -> {
                    error.printStackTrace();
                });
    }

    public void drawerItemClick(int id) {
        BaseFragment fragment = null;

        switch (id) {
            case 1:
                fragment = new NewsFeedFragment();
                break;
            case 2:
                fragment = new MyPostsFragment();
                break;
            case 4:
                fragment = new MembersFragment();
                break;
            case 5:
                fragment = new BoardFragment();
                break;
            case 6:
                fragment = new InfoFragment();
                break;
        }

        if (fragment != null && !myFragmentManager.isAlreadyContains(fragment)) {
            getViewState().showFragmentFromDrawer(fragment);
        }
    }
}