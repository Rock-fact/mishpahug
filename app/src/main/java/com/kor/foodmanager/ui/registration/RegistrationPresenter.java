package com.kor.foodmanager.ui.registration;

import android.os.AsyncTask;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kor.foodmanager.App;
import com.kor.foodmanager.buissness.login.ILoginInteractor;
import com.kor.foodmanager.data.model.UserDto;
import com.kor.foodmanager.data.userData.IUserDataRepository;
import com.kor.foodmanager.ui.MainActivity;


import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

import static com.kor.foodmanager.ui.MainActivity.EVENT_LIST_SCREEN;

@InjectViewState
public class RegistrationPresenter extends MvpPresenter<IRegistrationFragment> {

    @Inject
    Router router;

    ILoginInteractor loginInteractor;

    @Inject
    IUserDataRepository userDataRepository;


    public RegistrationPresenter(){
        App.get().mainComponent().inject(this);
        loginInteractor = App.get().loginComponent().getLoginInteractor();
    }

    public void startPersonalProf(UserDto user){
        router.navigateTo(MainActivity.PERSONALPROFILE_FRAGMENT_NEW, user);
    }

    public void registration(String email, String password, UserDto user){
        new RegistrationTask(email, password, user).execute();
    }

    private class RegistrationTask extends AsyncTask<Void, Void, String>{

        private String email, password;
        private boolean successful;
        private UserDto user;

        public RegistrationTask(String email, String password, UserDto user) {
            this.email = email;
            this.password = password;
            this.user = user;
            successful = true;
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                loginInteractor.registration(email, password);
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
                new UpdateUserProfileTask(user).execute();
            }else{
                router.showSystemMessage(s);
            }
        }
    }

    private class UpdateUserProfileTask extends AsyncTask<Void, Void, String> {

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
                router.newRootScreen(EVENT_LIST_SCREEN);
            }else{
                router.showSystemMessage(s);
            }
        }
    }

}