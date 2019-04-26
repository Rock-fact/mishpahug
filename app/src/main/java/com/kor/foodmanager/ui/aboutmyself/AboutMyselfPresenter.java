package com.kor.foodmanager.ui.aboutmyself;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.facebook.AccessToken;
import com.kor.foodmanager.App;
import com.kor.foodmanager.buissness.login.ILoginInteractor;
import com.kor.foodmanager.data.login.LoginException;
import com.kor.foodmanager.data.model.StaticfieldsDto;
import com.kor.foodmanager.data.model.UserDto;
import com.kor.foodmanager.data.pictureEditor.IEditPictureRepository;
import com.kor.foodmanager.data.userData.IUserDataRepository;
import com.kor.foodmanager.ui.MainActivity;
import com.kor.foodmanager.ui.editPicture.EditPictureFragment;

import java.io.IOException;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

import static com.kor.foodmanager.ui.MainActivity.EVENT_LIST_SCREEN;

@InjectViewState
public class AboutMyselfPresenter extends MvpPresenter<IAboutMyselfFragment> {

    @Inject
    Router router;

    @Inject
    IEditPictureRepository editPictureRepository;

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

    public void registrationAndUpdateUserProfile(String email, String password, UserDto user, Boolean isFacebook) {
        this.user=user;
        this.email=email;
        this.password=password;

        new RegistrationTask(email, password, isFacebook).execute();
    }

    public void updateStaticFields() {
        new GetStaticFieldsTask().execute();
    }

    public void editPic() {
        router.navigateTo(MainActivity.EDIT_PIC_FRAGMENT_SCREEN);
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
        private Boolean isFacebook;
        private Boolean isSuccess;

        public RegistrationTask(String email, String password, Boolean isFacebook) {
                this.email = email;
                this.password = password;
                this.isFacebook=isFacebook;

        }
        @Override
        protected void onPreExecute() {
            getViewState().showProgressFrame();
        }
        @Override
        protected String doInBackground(Void... voids) {
            String res = "OK";
            try {
                if (!isFacebook)
                interactor.registration(email, password);
                else interactor.registration(AccessToken.getCurrentAccessToken().getToken());
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
            if (isSuccess) {
                Log.d("Registration", "Registration is "+isSuccess);
                new UpdateUserProfileTask(user, isFacebook).execute();
            } else getViewState().showError(s);

        }
    }

    private class UpdateUserProfileTask extends AsyncTask<Void, Void, String> {

        private boolean isSuccess;
        private UserDto user;
        private Boolean isFacebook;

        public UpdateUserProfileTask(UserDto user, Boolean isFacebook) {
            this.user = user;
            this.isFacebook=isFacebook;
            isSuccess = true;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            user.setPictureLink(editPictureRepository.getNotLoadedUriList());
            Log.d("Registration", "onPreExecute: Pre");
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                user = userDataRepository.updateUserProfile(user);
                editPictureRepository
                        .uploadPic(Uri.parse(user.getPictureLink().get(0)), MainActivity.AVATAR_PICTURE, 1);
                editPictureRepository
                        .uploadPic(Uri.parse(user.getPictureLink().get(1)), MainActivity.EVENT_BANNER_PICTURE, 2);
                editPictureRepository.clearNonLoadedList();
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
                Log.d("PICS", "onPostExecute: "+user);

                Log.d("Registration", "Update is"+isSuccess);

                if (!isFacebook) new LoginTask(email, password).execute();
                else new LoginTask(isFacebook).execute();
            } else {
                router.showSystemMessage(s);
            }
        }
    }
    private class LoginTask extends AsyncTask<Void,Void,String> {
        private String email, password;
        private String token;
        private Boolean isFacebook;
        private Boolean isSuccess;

        public LoginTask(String email, String password) {
            this.email = email;
            this.password = password;
            isFacebook=false;
        }
        public LoginTask(Boolean isFacebook) {
            this.token = AccessToken.getCurrentAccessToken().getToken();
            this.isFacebook=true;
        }

        @Override
        protected void onPreExecute() {
            getViewState().showProgressFrame();
        }

        @Override
        protected String doInBackground(Void... voids) {
            String res = "OK";
            try {
                if (!isFacebook)
                interactor.login(email, password);
                else interactor.login(token);
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
