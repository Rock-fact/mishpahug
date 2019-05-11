package com.kor.foodmanager.ui.personalinfo;

import android.os.AsyncTask;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kor.foodmanager.App;
import com.kor.foodmanager.data.model.StaticfieldsDto;
import com.kor.foodmanager.data.model.UserDto;
import com.kor.foodmanager.data.pictureEditor.IEditPictureRepository;
import com.kor.foodmanager.data.userData.IUserDataRepository;
import com.kor.foodmanager.ui.MainActivity;
import com.kor.foodmanager.ui.contactinfo.UserDtoWithEmail;


import javax.inject.Inject;

import ru.terrakok.cicerone.Router;


@InjectViewState
public class PersonalProfilePresenter extends MvpPresenter<IPersonalProfileFragment> {

    @Inject
    Router router;

    @Inject
    IUserDataRepository userDataRepository;
    @Inject
    IEditPictureRepository editPictureRepository;

    public PersonalProfilePresenter(){
        App.get().mainComponent().inject(this);
    }

    public void updateStaticFields(){
        new GetStaticFieldsTask().execute();
    }

    public void startContactInfo(UserDtoWithEmail user){
        router.navigateTo(MainActivity.CONTACTINFO_FRAGMENT_NEW, user);
    }

    public void editPicture(UserDto user) {
        router.navigateTo(MainActivity.EDIT_PIC_FRAGMENT_SCREEN);
    }

    public void setPics() {
        if(editPictureRepository.getNotLoadedUriList()!=null){
            Log.d("PICS", "setPics: "+ editPictureRepository.getNotLoadedUriList());
            getViewState().setUserPics(editPictureRepository.getNotLoadedUriList());
        }
    }

    public void clearNonLoadedList() {
        editPictureRepository.clearNonLoadedList();
    }

    private class GetStaticFieldsTask extends AsyncTask<Void, Void, String>{

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

}
