package com.kor.foodmanager.ui.eventInfo.guestEventInfo;

import android.os.AsyncTask;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kor.foodmanager.App;
import com.kor.foodmanager.data.provider.web.Api;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class GuestEventInfoPresenter extends MvpPresenter<IGuestEventInfo> {
    @Inject Router router;
    @Inject Api api;

    public GuestEventInfoPresenter() {App.get().mainComponent().inject(this);}

    public void joinEvent() {
        new JoinEventTask().execute();
    }

    private class JoinEventTask extends AsyncTask<Void, Void, String>{

        @Override
        protected void onPreExecute() {
            getViewState().showProgressFrame();
        }

        @Override
        protected String doInBackground(Void... voids) {

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            getViewState().hideProgressFrame();
            getViewState().showToast(s);
        }
    }
}
