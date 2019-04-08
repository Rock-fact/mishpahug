package com.kor.foodmanager.ui.login;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.kor.foodmanager.App;
import com.kor.foodmanager.R;
import com.kor.foodmanager.ui.IToolbar;
import com.kor.foodmanager.ui.MainActivity;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.support.v4.content.ContextCompat.getSystemService;

public class LoginFragment extends MvpAppCompatFragment implements ILogin {
    @InjectPresenter LoginPresenter presenter;
    @BindView(R.id.inputEmail) TextInputEditText inputEmail;
    @BindView(R.id.inputPassword) TextInputEditText inputPassword;
    @BindView(R.id.loginBtn) Button loginBtn;
    @BindView(R.id.regBtn) Button regBtn;
    @BindView(R.id.progressFrame) FrameLayout progressFrame;
    @BindView(R.id.login_facebook_button)
    com.facebook.login.widget.LoginButton loginFacebookBtn;
    private Unbinder unbinder;
    private IToolbar iToolbar;
    private CallbackManager callbackManager;

    public LoginFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        unbinder = ButterKnife.bind(this,view);
        progressFrame.setOnClickListener(null);

        iToolbar = (IToolbar) getActivity();
        iToolbar.setTitleToolbarEnable("Mishpahug",false,false,false);

        callbackManager=CallbackManager.Factory.create();

        loginFacebookBtn.setFragment(this);
        loginFacebookBtn.setReadPermissions("email", "user_birthday","user_posts");

        loginFacebookBtn.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("Facebook", "loginFacebookBtn: "+loginResult.getAccessToken().getToken());
                presenter.login(loginResult.getAccessToken().getToken());
            }

            @Override
            public void onCancel() {
                Log.d("Facebook", "loginFacebookBtn: cancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("Facebook", "loginFacebookBtn: error"+error.getMessage());
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }



    @Override
    public void showError(String error) {
        new AlertDialog.Builder(getActivity())
                .setMessage(error)
                .setTitle("Error!")
                .setPositiveButton("Ok", null)
                .setCancelable(false)
                .create()
                .show();
    }

    @OnClick(R.id.loginBtn)
    public void loginBtnClicked(){
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        hideKeyboard();
        presenter.login(email,password);
    }

    public void hideKeyboard(){
        InputMethodManager imm = (InputMethodManager) getActivity().
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(loginBtn.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @OnClick(R.id.regBtn)
    public void regBtnClicked(){
        hideKeyboard();
        presenter.registration();
    }


//    @OnClick(R.id.regBtn)
//    public void regBtnClicked(){
//        String email = inputEmail.getText().toString();
//        String password = inputPassword.getText().toString();
//        presenter.registration(email,password);
//    }

    @Override
    public void showEmailError(String error) {
        inputEmail.setError(error);
    }

    @Override
    public void showPasswordError(String error) {
        inputPassword.setError(error);
    }

    @Override
    public void showProgressFrame() {
        progressFrame.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressFrame() {
        progressFrame.setVisibility(View.GONE);
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }
}
