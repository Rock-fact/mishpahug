package com.kor.foodmanager.ui.aboutmyself;

import android.os.AsyncTask;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kor.foodmanager.App;
import com.kor.foodmanager.buissness.login.ILoginInteractor;
import com.kor.foodmanager.data.login.LoginException;
import com.kor.foodmanager.data.model.StaticfieldsDto;
import com.kor.foodmanager.data.model.UserDto;
import com.kor.foodmanager.data.userData.IUserDataRepository;
import com.kor.foodmanager.ui.MainActivity;
import com.kor.foodmanager.ui.login.LoginPresenter;

import java.io.IOException;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class AboutMyselfPresenter extends MvpPresenter<IAboutMyselfFragment> {

    @Inject
    Router router;

    @Inject
    IUserDataRepository userDataRepository;

    ILoginInteractor interactor;

    public AboutMyselfPresenter(){
        App.get().mainComponent().inject(this);
        interactor=App.get().loginComponent().getLoginInteractor();
    }

    public void toEventList(){
        router.navigateTo(MainActivity.EVENT_LIST_SCREEN);
    }
    public void registration(String email, String password) {
        new RegistrationTask(email,password).execute();
    }

    public void updateStaticFields(){
        new GetStaticFieldsTask().execute();
    }



    public void updateUserProfile(UserDto user){
        new UpdateUserProfileTask(user).execute();
    }

    private class GetStaticFieldsTask extends AsyncTask<Void, Void, String> {

        private boolean successful;
        private StaticfieldsDto staticFields;

        public GetStaticFieldsTask() {
            successful = true;
        }

        @Override
        protected String doInBackground(Void... voids) {
            try{
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
            if(successful){
                getViewState().updateStaticFields(staticFields);
            }else{
                router.showSystemMessage(s);
            }
        }
    }

    private class UpdateUserProfileTask extends AsyncTask<Void, Void, String>{

        private boolean successful;
        private UserDto user;

        public UpdateUserProfileTask(UserDto user) {
            this.user = user;
            successful = true;
        }

        @Override
        protected String doInBackground(Void... voids) {
            try{
                user = userDataRepository.updateUserProfile(user);
                return "Done";
            } catch (Exception e) {
                e.printStackTrace();
                successful = false;
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            if(successful){

            }else{
                router.showSystemMessage(s);
            }
        }
    }

    private class RegistrationTask extends AsyncTask<Void,Void,String> {
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
            if(!isSuccess){
                getViewState().showError(s);
            }

        }
    }

}
