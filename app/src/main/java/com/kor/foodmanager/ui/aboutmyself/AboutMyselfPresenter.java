package com.kor.foodmanager.ui.aboutmyself;

import android.os.AsyncTask;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kor.foodmanager.App;
import com.kor.foodmanager.buissness.login.ILoginInteractor;
import com.kor.foodmanager.data.login.LoginException;
import com.kor.foodmanager.data.model.StaticfieldsDto;
import com.kor.foodmanager.data.model.UserDto;
import com.kor.foodmanager.data.userData.IUserDataRepository;
import com.kor.foodmanager.ui.MainActivity;

import java.io.IOException;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

import static com.kor.foodmanager.ui.MainActivity.EVENT_LIST_SCREEN;

@InjectViewState
public class AboutMyselfPresenter extends MvpPresenter<IAboutMyselfFragment> {

    @Inject
    Router router;

    @Inject
    IUserDataRepository userDataRepository;

    ILoginInteractor interactor;

    UserDto user;
    String email;
    String password;


    public AboutMyselfPresenter() {
        App.get().mainComponent().inject(this);
        interactor = App.get().loginComponent().getLoginInteractor();
    }

    public void registrationAndUpdateUserProfile(String email, String password, UserDto user) {
        this.user=user;
        this.email=email;
        this.password=password;
        new RegistrationTask(email, password).execute();
    }

    public void updateStaticFields() {
        new GetStaticFieldsTask().execute();
    }


    private class GetStaticFieldsTask extends AsyncTask<Void, Void, String> {

        private boolean successful;
        private StaticfieldsDto staticFields;

        public GetStaticFieldsTask() {
            successful = true;
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                staticFields = userDataRepository.staticFields();
                return "Done";
            } catch (Exception e) {
                e.printStackTrace();
                successful = false;
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            if (successful) {
                getViewState().updateStaticFields(staticFields);
            } else {
                router.showSystemMessage(s);
            }
        }
    }



    private class RegistrationTask extends AsyncTask<Void, Void, String> {
        private String email, password;
        private Boolean isSuccess;

        public RegistrationTask(String email, String password) {
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
                interactor.registration(email, password);
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
                Log.d("Registration", "Registration is "+isSuccess);
                new UpdateUserProfileTask(user).execute();
            } else getViewState().showError(s);

        }
    }

    private class UpdateUserProfileTask extends AsyncTask<Void, Void, String> {

        private boolean isSuccess;
        private UserDto user;

        public UpdateUserProfileTask(UserDto user) {
            this.user = user;
            isSuccess = true;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("Registration", "onPreExecute: Pre");
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                user = userDataRepository.updateUserProfile(user);
                return "Done";
            } catch (Exception e) {
                e.printStackTrace();
                isSuccess = false;
                Log.d("Registration", "Update profile is "+isSuccess);
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            if (isSuccess) {
                Log.d("Registration", "Update is"+isSuccess);
                new LoginTask(email, password).execute();
            } else {
                router.showSystemMessage(s);
            }
        }
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
