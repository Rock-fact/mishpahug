package com.kor.foodmanager.ui.myEventList;


import android.os.AsyncTask;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kor.foodmanager.App;
import com.kor.foodmanager.buissness.myEventListInteractor.IMyEventListInteractor;
import com.kor.foodmanager.data.event.ServerException;
import com.kor.foodmanager.data.model.EventDto;
import com.kor.foodmanager.ui.MainActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class MyEventListPresenter extends MvpPresenter<IMyEventList> {
    @Inject
    Router router;
    @Inject
    IMyEventListInteractor myEventListInteractor;

    private MyEventListAdapter adapter;

    public MyEventListPresenter() {
        App.get().myEventListComponent().inject(this);
        adapter = new MyEventListAdapter();
    }

    public void loadEventList() {
        new LoadingList().execute();
    }

    public MyEventListAdapter getAdapter() {
        return adapter;
    }

    public void eventInfo(int position) {
        EventDto tmp = adapter.getEvents().get(position).getEvent();
        switch (tmp.getStatus()) {
            case MyEventListAdapter.INPROGRESS:
                router.navigateTo(MainActivity.MY_EVENT_INFO_INPROGRESS_SCREEN, tmp);
                break;
            case MyEventListAdapter.PENDING:
                router.navigateTo(MainActivity.MY_EVENT_INFO_PENDING_SCREEN, tmp);
                break;
            case MyEventListAdapter.DONE:
                router.navigateTo(MainActivity.MY_EVENT_INFO_DONE_SCREEN, tmp);
                break;
        }
    }

    private class LoadingList extends AsyncTask<Void, Void, List<EventDto>> {
        private List<EventDto> tmpList;
        String res;

        @Override
        protected void onPreExecute() {
            getViewState().showProgressFrame();
        }

        @Override
        protected List<EventDto> doInBackground(Void... voids) {
            tmpList = null;
            try {
                tmpList = myEventListInteractor.getMyEventList();
            } catch (
                    IOException e) {
                res = "Server error!";
            } catch (
                    ServerException e) {
                res = e.getMessage();
            }
            return tmpList;
        }

        @Override
        protected void onPostExecute(List<EventDto> list) {
            if (list!=null && list.size()>1)
                Collections.sort(list, (lhs, rhs) ->
                    getStatusPriority(lhs.getStatus()) > getStatusPriority(rhs.getStatus()) ? -1
                            : (getStatusPriority(lhs.getStatus()) < getStatusPriority(rhs.getStatus())) ? 1 : 0);
            getViewState().hideProgressFrame();
            if (list == null) {
                router.showSystemMessage(res);
            } else {
                adapter.setEvents(list);
            }
        }
    }

    @Override
    public void onDestroy() {
        App.get().clearMyEventListComponent();
        super.onDestroy();
    }

    private int getStatusPriority(String str){
        int res=-10;
        switch (str){
            case "In progress":
                res=3;
                break;
            case "Pending":
                res=2;
                break;
            case "Done":
                res=1;
        }
        return res;
    }
}

