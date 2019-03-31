package com.kor.foodmanager.ui.login;

import android.os.AsyncTask;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kor.foodmanager.App;
import com.kor.foodmanager.buissness.login.ILoginInteractor;
import com.kor.foodmanager.data.login.LoginException;
import com.kor.foodmanager.buissness.login.validator.EmailValidException;
import com.kor.foodmanager.buissness.login.validator.PasswordValidException;
import java.io.IOException;
import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

import static com.kor.foodmanager.ui.MainActivity.EVENT_LIST_SCREEN;
import static com.kor.foodmanager.ui.MainActivity.PERSONALPROFILE_FRAGMENT_NEW;
import static com.kor.foodmanager.ui.MainActivity.REGISTRATION_FRAGMENT;

@InjectViewState
public class LoginPresenter extends MvpPresenter<ILogin> {
    @Inject ILoginInteractor interactor;
    @Inject Router router;


    public LoginPresenter(){
        App.get().loginComponent().inject(this);
    }


    public void login(String email, String password) {
        try {
            interactor.validate(email,password);
            new LoginTask(email,password).execute();
        } catch (EmailValidException e) {
            getViewState().showEmailError(e.getMessage());
        } catch (PasswordValidException e) {
            getViewState().showPasswordError(e.getMessage());
        }
    }

    public void registration(){
        router.navigateTo(PERSONALPROFILE_FRAGMENT_NEW);
    }



    @Override
    public void onDestroy() {
        App.get().clearLoginComponent();
        super.onDestroy();
    }

    private class LoginTask extends AsyncTask<Void,Void,String> {
        private String email, password;
        private Boolean isSuccess;

        public LoginTask(String email, String password) {
            this.email = email;
            this.password = password;
        }

        @Override
        protected void onPreExecute() {
            getViewState().showProgressFrame();
        }

        @Override
        protected String doInBackground(Void... voids) {
            String res = "OK";
            try {
                interactor.login(email, password);
                isSuccess = true;
            } catch (IOException e) {
                res = "Connection failed!";
                isSuccess = false;
            } catch (LoginException e){
                res = e.getMessage();
                isSuccess = false;
            }
            return res;
        }

        @Override
        protected void onPostExecute(String s) {
            getViewState().hideProgressFrame();
            if(isSuccess){
                router.newRootScreen(EVENT_LIST_SCREEN);
            }else{
                getViewState().showError(s);
            }

        }
    }
}
