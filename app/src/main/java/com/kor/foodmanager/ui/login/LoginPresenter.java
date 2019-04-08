package com.kor.foodmanager.ui.login;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.kor.foodmanager.App;
import com.kor.foodmanager.buissness.login.ILoginInteractor;
import com.kor.foodmanager.data.login.LoginException;
import com.kor.foodmanager.buissness.login.validator.EmailValidException;
import com.kor.foodmanager.buissness.login.validator.PasswordValidException;
import com.kor.foodmanager.data.model.UserDto;
import com.kor.foodmanager.ui.MainActivity;
import com.kor.foodmanager.ui.contactinfo.UserDtoWithEmail;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

import static com.kor.foodmanager.ui.MainActivity.EVENT_LIST_SCREEN;
import static com.kor.foodmanager.ui.MainActivity.PERSONALPROFILE_FRAGMENT_NEW;
import static com.kor.foodmanager.ui.MainActivity.REGISTRATION_FRAGMENT;

@InjectViewState
public class LoginPresenter extends MvpPresenter<ILogin> {
    @Inject
    ILoginInteractor interactor;
    @Inject
    Router router;
    Boolean isFacebook=false;


    public LoginPresenter() {
        App.get().loginComponent().inject(this);
    }


    public void login(String email, String password) {
        isFacebook=false;
        try {
            interactor.validate(email, password);
            new LoginTask(email, password).execute();
        } catch (EmailValidException e) {
            getViewState().showEmailError(e.getMessage());
        } catch (PasswordValidException e) {
            getViewState().showPasswordError(e.getMessage());
        }
    }

    public void login(String token) {
        isFacebook=true;
        new LoginTask(token).execute();
    }

    public void registration() {
        if (!isFacebook)
        router.navigateTo(PERSONALPROFILE_FRAGMENT_NEW);
        else getUser();
    }

    private void getUser() {
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                UserDtoWithEmail userDtoWithEmail=new UserDtoWithEmail();
                UserDto user = userDtoWithEmail.getUser();
                try {
                    String userId = object.getString("id");
                    List<String> tmp = new ArrayList<>();
                    tmp.add("https://graph.facebook.com/" + userId + "/picture?width=500&height=500");
                    user.setPictureLink(tmp);
                    if (object.has("first_name"))
                    user.setFirstName(object.getString("first_name"));
                    if (object.has("last_name"))
                        user.setLastName(object.getString("last_name"));
                            if (object.has("email"))
                                userDtoWithEmail.setEmail(object.getString("email")) ;
                    if (object.has("birthday"))
                        user.setDateOfBirth(object.getString("birthday"));
                    if (object.has("gender"))
                        user.setGender(object.getString("gender"));
                    Log.d("Facebook", "onCompleted: " +user.getFirstName()+" "+user.getLastName()+" "+user.getDateOfBirth()+" "+user.getGender()+" "+userDtoWithEmail.getEmail());

                    router.navigateTo(PERSONALPROFILE_FRAGMENT_NEW,userDtoWithEmail);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        //Here we put the requested fields to be returned from the JSONObject
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id, first_name, last_name, email, birthday, gender");
        request.setParameters(parameters);
        request.executeAsync();
    }


@Override
public void onDestroy(){
        App.get().clearLoginComponent();
        super.onDestroy();
        }

private class LoginTask extends AsyncTask<Void, Void, String> {
    private String email, password;
    private String token;
    private Boolean isSuccess;

    public LoginTask(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public LoginTask(String token) {
        this.token = token;
    }

    @Override
    protected void onPreExecute() {
        getViewState().showProgressFrame();
    }

    @Override
    protected String doInBackground(Void... voids) {
        String res = "OK";
        try {
            if (!isFacebook) interactor.login(email, password);
            else interactor.login(token);
            isSuccess = true;
        } catch (IOException e) {
            res = "Connection failed!";
            isSuccess = false;
        } catch (LoginException e) {
            res = e.getMessage();
            isSuccess = false;
        }
        return res;
    }

    @Override
    protected void onPostExecute(String s) {
        getViewState().hideProgressFrame();
        if (isSuccess) {
            router.newRootScreen(EVENT_LIST_SCREEN);
        } else {
            if (!isFacebook) getViewState().showError(s);
            else registration();
        }
    }
}
}
