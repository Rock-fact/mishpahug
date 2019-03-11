package com.kor.foodmanager.ui.eventInfo.myEventInfoInProgress;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kor.foodmanager.App;
import com.kor.foodmanager.R;
import com.kor.foodmanager.buissness.MyEventInProgressInteractor.IMyEventInProgressInteractor;
import com.kor.foodmanager.data.event.ServerException;
import com.kor.foodmanager.data.model.EventDto;
import com.kor.foodmanager.data.model.InvitationStatusDto;
import com.kor.foodmanager.data.model.UserDto;
import com.kor.foodmanager.ui.MainActivity;
import com.kor.foodmanager.ui.myEventList.IMyEventList;
import com.kor.foodmanager.ui.myEventList.MyEventListAdapter;
import com.kor.foodmanager.ui.myEventList.TitleRow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

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
    }

    public void inviteToEvent(Long eventId, Long userId, View itemView) {
        new InviteToEvent(eventId, userId, itemView).execute();
    }
    public void changeStatusToEvent(Long eventId){
        new ChangeStatusToEvent(eventId).execute();
    }

    public MyEventInfoInProgressAdapter getAdapter() {
        return adapter;
    }

    public void userInfo(UserDto user){
        router.showSystemMessage("GoToUserInfoFragment");
    }

    private class InviteToEvent extends AsyncTask<Void, Void, InvitationStatusDto> {
        Long eventId;
        Long userId;
        String res;
        View itemView;

        public InviteToEvent(Long eventId, Long userId, View itemView) {
            this.eventId = eventId;
            this.userId = userId;
            this.itemView=itemView;
        }

        @Override
        protected void onPreExecute() {
            getViewState().showProgressFrame();
        }

        @Override
        protected InvitationStatusDto doInBackground(Void... voids) {
            InvitationStatusDto invitationStatusDto = null;
            try {
                invitationStatusDto = myEventInProgressInteractor.inviteToEvent(eventId, userId);
                Button inviteBtn =itemView.findViewById(R.id.invite_btn);
                inviteBtn.setText("+");
                inviteBtn.setEnabled(false);
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

    private class ChangeStatusToEvent extends AsyncTask<Void, Void, EventDto> {
        Long eventId;
        String res;

        public ChangeStatusToEvent(Long eventId) {
            this.eventId = eventId;
        }
        @Override
        protected void onPreExecute() {
            getViewState().showProgressFrame();
        }

        @Override
        protected EventDto doInBackground(Void... voids) {
            EventDto event = null;
            try {
                event = myEventInProgressInteractor.changeEventStatus(eventId);
            } catch (
                    IOException e) {
                res = "Server error!";
            } catch (
                    ServerException e) {
                res = e.getMessage();
            }
            return event;
        }

        @Override
        protected void onPostExecute(EventDto event) {
            getViewState().hideProgressFrame();
            if (event == null) {
                router.showSystemMessage(res);
            }
        }
    }

    @Override
    public void onDestroy() {
        App.get().clearMyEventInProgressComponent();
        super.onDestroy();
    }
}

