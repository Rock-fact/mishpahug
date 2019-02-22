package com.kor.foodmanager.ui.addEvent;

import android.os.AsyncTask;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kor.foodmanager.App;
import com.kor.foodmanager.buissness.home.HomeInteractor;
import com.kor.foodmanager.buissness.home.IHomeInteractor;
import com.kor.foodmanager.data.home.ServerException;
import com.kor.foodmanager.data.model.EventDto;

import java.io.IOException;

import javax.inject.Inject;

@InjectViewState
public class AddEventPresenter extends MvpPresenter<IAddEvent> {

    @Inject IHomeInteractor interactor;

    public AddEventPresenter(){
        App.get().homeComponent().inject(this);
    }

    public void addNewEvent(EventDto event) {
        new AddEventTask(event).execute();
    }

    @Override
    public void onDestroy() {
        App.get().clearHomeComponent();
        super.onDestroy();
    }

    private class AddEventTask extends AsyncTask<Void,Void,String> {
        private EventDto event;
        private Boolean isSuccess;

        public AddEventTask(EventDto event) {
            this.event = event;
        }

        @Override
        protected void onPreExecute() {
            getViewState().showProgressFrame();
        }

        @Override
        protected String doInBackground(Void... voids) {
            String res = "OK";
            try {
                interactor.addNewEvent(event);
                isSuccess = true;
            } catch (IOException e) {
                res = "Connection failed!";
                isSuccess = false;
            } catch (ServerException e) {
                res = e.getMessage();
                isSuccess = false;
            }
            return res;
        }

        @Override
        protected void onPostExecute(String s) {
            getViewState().hideProgressFrame();
            if(isSuccess){
                // TODO: 22.02.2019
                getViewState().showError("EVENT ADDED");
            }else{
                getViewState().showError(s);
            }

        }
    }
}
