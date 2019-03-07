package com.kor.foodmanager.ui.myEventList;


import android.os.AsyncTask;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kor.foodmanager.App;
import com.kor.foodmanager.buissness.myEventListInteractor.IMyEventListInteractor;
import com.kor.foodmanager.data.auth.IAuthRepository;
import com.kor.foodmanager.data.event.ServerException;
import com.kor.foodmanager.data.model.EventDto;
import com.kor.foodmanager.data.model.EventListDto;
import com.kor.foodmanager.data.provider.web.Api;
import com.kor.foodmanager.ui.MainActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
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
        TitleRow tmp = adapter.getEvents().get(position);
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
            ArrayList<TitleRow> events = new ArrayList<>();
            getViewState().hideProgressFrame();
            Log.d("MY_TAG", "onPostExecute: " + list.size());
            if (list != null & list.size() > 0) {
                int j = 0;
                events.add(new TitleRow(list.get(j).getStatus()));
                for (int i = 0; i < list.size(); i++) {
                    if (!list.get(i).getStatus().equals(list.get(j).getStatus())) {
                        j = i;
                        events.add(new TitleRow(list.get(i).getStatus()));
                        events.add(new TitleRow(list.get(i)));
                    } else {
                        events.add(new TitleRow(list.get(i)));
                    }
                }
            } else if (list == null) {
                router.showSystemMessage(res);
            }
            adapter.setEvents(events);
        }
    }

    @Override
    public void onDestroy() {
        App.get().clearMyEventListComponent();
        super.onDestroy();
    }
}

