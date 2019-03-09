package com.kor.foodmanager.ui.participationList;

import android.os.AsyncTask;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kor.foodmanager.App;
import com.kor.foodmanager.buissness.participationList.IParticipationListInteractor;
import com.kor.foodmanager.data.event.ServerException;
import com.kor.foodmanager.data.model.EventDto;
import com.kor.foodmanager.data.model.EventListDto;
import com.kor.foodmanager.ui.MainActivity;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class ParticipationListPresenter extends MvpPresenter<IParticipationList> {
    @Inject Router router;
    @Inject IParticipationListInteractor interactor;
    public static final String IN_PROGRESS = "In progress";
    public static final String PENDING ="Pending";
    public static final String DONE = "Done";

    private ParticipationListAdapter adapter;

    public ParticipationListPresenter() {
        App.get().participationListComponent().inject(this);
        adapter = new ParticipationListAdapter();
    }

    @Override
    public void onDestroy() {
        App.get().clearParticipationListComponent();
        super.onDestroy();
    }

    public ParticipationListAdapter getAdapter(){
        return adapter;
    }

    public void loadParticipationList(){
        new LoadingParticipationList().execute();
    }

    public void eventInfo(int position){
        EventDto tmp = adapter.getParticipationList().get(position);
        switch (tmp.getStatus()){
            case IN_PROGRESS:
                router.navigateTo(MainActivity.GUEST_EVENT_INFO_INPROGRESS_SCREEN, tmp);
                break;
            case PENDING:
                router.navigateTo(MainActivity.GUEST_EVENT_INFO_PENDING_SCREEN, tmp);
                break;
            case DONE:
                //router.navigateTo(); TODO
                break;
        }

    }

    private class LoadingParticipationList extends AsyncTask<Void, Void, List<EventDto>> {
        private String res;
        private List<EventDto> tmpList;

        @Override
        protected void onPreExecute() {
            getViewState().showProgressFrame();
        }

        @Override
        protected List<EventDto> doInBackground(Void... voids) {
            tmpList=null;
            try {
                tmpList = interactor.getParticipationList();
            } catch (IOException e) {
                res = "Server error!";
            } catch (ServerException e) {
                res = e.getMessage();
            }
            return tmpList;
        }

        @Override
        protected void onPostExecute(List<EventDto> list) {
            getViewState().hideProgressFrame();
            if(list!=null){
                adapter.removeAll();
                for(int i=0;i<list.size();i++){
                    adapter.addEvent(list.get(i));
                }
            } else {
                router.showSystemMessage(res);
            }
        }
    }



}
