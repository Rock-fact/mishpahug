package com.kor.foodmanager.ui.myProfile;

import android.os.AsyncTask;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kor.foodmanager.App;
import com.kor.foodmanager.data.model.StaticfieldsDto;
import com.kor.foodmanager.data.model.UserDto;
import com.kor.foodmanager.data.userData.IUserDataRepository;
import com.kor.foodmanager.ui.MainActivity;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;


@InjectViewState
public class MyProfilePresenter extends MvpPresenter<IMyProfileFragment> {

    @Inject
    Router router;

    @Inject
    IUserDataRepository userDataRepository;

    public MyProfilePresenter(){
        App.get().mainComponent().inject(this);
    }

    public void updateStaticFields(){
        new GetStaticFieldsTask().execute();
    }
    public void updateUserProfile(UserDto user){
        new UpdateUserPofileTask(user).execute();
    }

    public  void back(){
        router.backTo(MainActivity.EVENT_LIST_SCREEN);
    }

    public void fillInput(){
        getViewState().fillInput();
        getViewState().inputMode();
    }
    public void fillView(){
        getViewState().fillView();

    }
    public void viewMode(){
        getViewState().viewMode();
    }

    public void editAvatar() {
        router.navigateTo(MainActivity.EDIT_PIC_FRAGMENT_SCREEN);
    }

    private class GetStaticFieldsTask extends AsyncTask<Void, Void, String>{

        private boolean successful;
        private StaticfieldsDto staticFields;

        public GetStaticFieldsTask() {
            successful = true;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            getViewState().showProgressBar();
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
            getViewState().hideProgressBar();
            if(successful){
                getViewState().updateStaticFields(staticFields);
            }else{
                router.showSystemMessage(s);
            }
        }
    }

    private class UpdateUserPofileTask extends AsyncTask<Void, Void, String>{

        private boolean successful;
        private UserDto user;

        public UpdateUserPofileTask(UserDto user) {
            successful = true;
            this.user=user;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            getViewState().showProgressBar();
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
            getViewState().hideProgressBar();
            if(!successful){
                router.showSystemMessage(s);
            }
        }
    }

}
