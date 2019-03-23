package com.kor.foodmanager.ui.eventInfo.myEventInfoInProgress;

import android.os.AsyncTask;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kor.foodmanager.App;
import com.kor.foodmanager.buissness.MyEventInProgressInteractor.IMyEventInProgressInteractor;
import com.kor.foodmanager.data.event.ServerException;
import com.kor.foodmanager.data.model.EventDto;
import com.kor.foodmanager.data.model.InvitationStatusDto;
import com.kor.foodmanager.data.model.UserDto;
import com.kor.foodmanager.ui.MainActivity;

import java.io.IOException;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

import static com.kor.foodmanager.ui.MainActivity.TAG;

@InjectViewState
public class MyEventInfoInProgressPresenter extends MvpPresenter<IMyEventInfoInProgress> {
    @Inject
    Router router;
    @Inject
    IMyEventInProgressInteractor myEventInProgressInteractor;

    private MyEventInfoInProgressAdapter adapter;

    public MyEventInfoInProgressPresenter() {
        App.get().myEventInProgressComponent().inject(this);
        adapter = new MyEventInfoInProgressAdapter();
        Log.d(TAG, "MyEventInfoInProgressPresenter: ");
    }

    public void inviteToEvent(Long eventId, int adapterPosition) {
        new InviteToEvent(eventId, adapterPosition).execute();
    }
    public void changeStatusToEvent(EventDto event){
        new ChangeStatusToEvent(event).execute();
    }

    public MyEventInfoInProgressAdapter getAdapter() {
        return adapter;
    }

    public void userInfo(int adapterPosition){
        UserDto user=adapter.getListOfParticipants().get(adapterPosition);
        router.navigateTo(MainActivity.USER_INFO_SCREEN_PROGRESS, user);
    }

    private class InviteToEvent extends AsyncTask<Void, Void, InvitationStatusDto> {
        Long eventId;
        Long userId;
        String res;
        int adapterPosition;

        public InviteToEvent(Long eventId, int adapterPosition) {
            this.eventId = eventId;
            this.adapterPosition = adapterPosition;
        }

        @Override
        protected void onPreExecute() {
            getViewState().showProgressFrame();
        }

        @Override
        protected InvitationStatusDto doInBackground(Void... voids) {
            InvitationStatusDto invitationStatusDto = null;
            try {
                invitationStatusDto = myEventInProgressInteractor.inviteToEvent(eventId, adapter.getListOfParticipants().get(adapterPosition).getUserId());
                adapter.getListOfParticipants().get(adapterPosition).setInvited(true);
            } catch (
                    IOException e) {
                res = "Server error!";
            } catch (
                    ServerException e) {
                res = e.getMessage();
            }
            return invitationStatusDto;
        }

        @Override
        protected void onPostExecute(InvitationStatusDto invitationStatusDto) {
            getViewState().hideProgressFrame();
            if (invitationStatusDto == null) {
                router.showSystemMessage(res);
            }

        }
    }

    private class ChangeStatusToEvent extends AsyncTask<Void, Void, com.kor.foodmanager.data.model.EventDto> {
        EventDto event;
        String res;

        public ChangeStatusToEvent(EventDto event) {
            this.event=event;
        }
        @Override
        protected void onPreExecute() {
            getViewState().showProgressFrame();
        }

        @Override
        protected EventDto doInBackground(Void... voids) {
            EventDto eventDto = null;
            try {
                eventDto = myEventInProgressInteractor.changeEventStatus(event.getEventId());

            } catch (
                    IOException e) {
                res = "Server error!";
            } catch (
                    ServerException e) {
                res = e.getMessage();
            }
            return eventDto;
        }

        @Override
        protected void onPostExecute(EventDto eventDto) {
            getViewState().hideProgressFrame();
            if (eventDto == null) {
                router.showSystemMessage(res);
            } else router.replaceScreen(MainActivity.MY_EVENT_INFO_PENDING_SCREEN, event);
        }
    }

    @Override
    public void onDestroy() {
        App.get().clearMyEventInProgressComponent();
        super.onDestroy();
    }
}

