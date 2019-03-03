package com.kor.foodmanager.ui.notificationInfo;

import android.os.AsyncTask;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kor.foodmanager.App;
import com.kor.foodmanager.buissness.notification.INotificationInteractor;
import com.kor.foodmanager.data.event.ServerException;

import java.io.IOException;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class NotificationInfoPresenter extends MvpPresenter<INotificationInfo>{
    @Inject INotificationInteractor interactor;
    @Inject Router router;

    public NotificationInfoPresenter() {
        App.get().notificationComponent().inject(this);
    }

    @Override
    public void onDestroy() {
        App.get().clearNotificationComponent();
        super.onDestroy();
    }

    public void startWork(Long notificationId) {
        new NotificationIsReadTask().execute(notificationId);
    }


    private class NotificationIsReadTask extends AsyncTask<Long,Void,String>{
        private Boolean isSuccess;
        String res = "OK";

        @Override
        protected String doInBackground(Long... longs) {
            try {
                isSuccess = true;
                interactor.notificationIsRead(longs[0]);
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
            if(!isSuccess){
                router.showSystemMessage(res);
            }
        }
    }
}
