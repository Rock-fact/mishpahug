package com.kor.foodmanager.ui.eventInfo.guestEventInfo;

import android.os.AsyncTask;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kor.foodmanager.App;
import com.kor.foodmanager.buissness.eventInfo.IGuestEventInfoInteractor;
import com.kor.foodmanager.data.event.ServerException;
import com.kor.foodmanager.data.model.MessageDto;
import com.kor.foodmanager.data.provider.web.Api;

import java.io.IOException;

import javax.inject.Inject;

import retrofit2.Call;
import ru.terrakok.cicerone.Router;

@InjectViewState
public class GuestEventInfoPresenter extends MvpPresenter<IGuestEventInfo> {
    @Inject Router router;
    @Inject IGuestEventInfoInteractor interactor;

    public GuestEventInfoPresenter() {App.get().guestEventInfoComponent().inject(this);}

    public void joinEvent(long eventId) {
        new JoinEventTask().execute(eventId);
    }

    @Override
    public void onDestroy() {
        App.get().clearGuestEventInfoComponent();
        super.onDestroy();
    }

    private class JoinEventTask extends AsyncTask<Long, Void, String>{
        private String res;

        @Override
        protected void onPreExecute() {
            getViewState().showProgressFrame();
        }

        @Override
        protected String doInBackground(Long... longs) {
            try {
                res = interactor.joinEvent(longs[0]);
            } catch (IOException e) {
                res = "Connection failed!";
            } catch (ServerException e) {
                res = e.getMessage();
            }
            return res;
        }

        @Override
        protected void onPostExecute(String s) {
            getViewState().hideProgressFrame();
            //getViewState().showToast(s);
            router.showSystemMessage(s);
        }
    }
}
