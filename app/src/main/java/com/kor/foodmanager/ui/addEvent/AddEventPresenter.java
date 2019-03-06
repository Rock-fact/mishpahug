package com.kor.foodmanager.ui.addEvent;

import android.os.AsyncTask;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.google.android.gms.common.api.Status;
import com.kor.foodmanager.App;
import com.kor.foodmanager.buissness.event.IEventInteractor;
import com.kor.foodmanager.data.event.ServerException;
import com.kor.foodmanager.data.model.EventDto;

import java.io.IOException;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

import static com.kor.foodmanager.ui.MainActivity.HIDE_PROGRESS;
import static com.kor.foodmanager.ui.MainActivity.SHOW_PROGRESS;

@InjectViewState
public class AddEventPresenter extends MvpPresenter<IAddEvent> {
    @Inject Router router;
    @Inject IEventInteractor interactor;

    public AddEventPresenter(){
        App.get().eventComponent().inject(this);
    }

    public void addNewEvent(EventDto event) {   // TODO: 05.03.2019 good fields validation 
        event.setConfession(interactor.getUser().getConfession());
        event.setFood(interactor.getUser().getFoodPreferences());       // TODO: 05.03.2019 maybe it wrong buissness decision 
        if(event.getDuration()>0){
            new AddEventTask(event).execute();
        }else {
            router.showSystemMessage("Please, choose duration of your event!");
        }
    }

    @Override
    public void onDestroy() {
        App.get().clearEventComponent();
        super.onDestroy();
    }

    public void showError(String string) {
        router.showSystemMessage(string);
    }

    private class AddEventTask extends AsyncTask<Void,Void,String> {
        private EventDto event;
        private Boolean isSuccess;

        public AddEventTask(EventDto event) {
            this.event = event;
        }

        @Override
        protected void onPreExecute() {
            router.showSystemMessage(SHOW_PROGRESS);
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
            router.showSystemMessage(HIDE_PROGRESS);
            if(isSuccess){
                router.showSystemMessage("EVENT ADDED");
            }else{
                router.showSystemMessage(s);
            }

        }
    }
}
