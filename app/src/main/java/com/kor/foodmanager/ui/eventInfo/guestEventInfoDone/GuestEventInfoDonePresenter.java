package com.kor.foodmanager.ui.eventInfo.guestEventInfoDone;

import android.os.AsyncTask;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kor.foodmanager.App;
import com.kor.foodmanager.buissness.eventInfo.IGuestEventInfoInteractor;
import com.kor.foodmanager.data.event.ServerException;

import java.io.IOException;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class GuestEventInfoDonePresenter extends MvpPresenter<IGuestEventInfoDone> {
    @Inject Router router;
    @Inject IGuestEventInfoInteractor interactor;
    private double rate;

    public GuestEventInfoDonePresenter() {
        App.get().guestEventInfoComponent().inject(this);
    }

    @Override
    public void onDestroy() {
        App.get().clearGuestEventInfoComponent();
        super.onDestroy();
    }

    public void voteForEvent(long eventId, double rate){
        this.rate = rate;
        new VoteForEventTask().execute(eventId);
    }


    private class VoteForEventTask extends AsyncTask<Long, Void, String> {
        private String res;
        private boolean isSuccess;
        private double voteRate = rate;

        @Override
        protected void onPreExecute() {
            getViewState().showProgressFrame();
            isSuccess = true;
        }

        @Override
        protected String doInBackground(Long... longs) {
            try {
                res = interactor.voteForEvent(longs[0], voteRate);
            } catch (IOException e) {
                isSuccess = false;
                res = "Connection failed!";
            } catch (ServerException e) {
                isSuccess = false;
                res = e.getMessage();
            }
            return res;
        }

        @Override
        protected void onPostExecute(String s) {
            getViewState().hideProgressFrame();
            if (isSuccess){
                getViewState().showSuccessDialog(s);
                getViewState().hideVoteBtn();
            } else {
                router.showSystemMessage(s);
            }
        }
    }





}
