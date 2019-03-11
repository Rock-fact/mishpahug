package com.kor.foodmanager.ui.eventInfo.guestEventInfoInprogress;

import android.os.AsyncTask;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kor.foodmanager.App;
import com.kor.foodmanager.buissness.eventInfo.IGuestEventInfoInteractor;
import com.kor.foodmanager.data.event.ServerException;
import com.kor.foodmanager.ui.MainActivity;

import java.io.IOException;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class GuestEventInfoInprogressPresenter extends MvpPresenter<IGuestEventInfoInprogress> {
    @Inject
    Router router;
    @Inject
    IGuestEventInfoInteractor interactor;

    public GuestEventInfoInprogressPresenter() {
        App.get().guestEventInfoComponent().inject(this);
    }

    @Override
    public void onDestroy() {
        App.get().clearGuestEventInfoComponent();
        super.onDestroy();
    }

    public void unsubscribe(long eventId){
        new UnsubscribeFromEventTask().execute(eventId);
    }


    private class UnsubscribeFromEventTask extends AsyncTask<Long, Void, String> {
        private String res;
        private boolean isSuccess;

        @Override
        protected void onPreExecute() {
            getViewState().showProgressFrame();
            isSuccess = true;
        }

        @Override
        protected String doInBackground(Long... longs) {
            try {
                res = interactor.unsubscribeFromEvent(longs[0]);
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
                router.navigateTo(MainActivity.PARTICIPATION_LIST_SCREEN);
            } else {
                router.showSystemMessage(s);
            }
        }
    }



}
