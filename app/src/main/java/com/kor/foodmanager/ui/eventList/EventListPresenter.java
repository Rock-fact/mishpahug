package com.kor.foodmanager.ui.eventList;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.kor.foodmanager.App;
import com.kor.foodmanager.data.auth.AuthRepository;
import com.kor.foodmanager.data.auth.IAuthRepository;
import com.kor.foodmanager.data.model.EventDto;
import com.kor.foodmanager.data.model.EventListDto;
import com.kor.foodmanager.data.model.FiltersDto;
import com.kor.foodmanager.data.provider.web.Api;
import com.kor.foodmanager.di.application.MainModule;
import com.kor.foodmanager.ui.MainActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.terrakok.cicerone.Router;

@InjectViewState
public class EventListPresenter extends MvpPresenter<IEventList> {
    @Inject Router router;
    @Inject Api api;
    @Inject IAuthRepository authRepository;

private EventListAdapter adapter;

    public EventListPresenter() {
        App.get().guestEventInfoComponent().inject(this);
        adapter = new EventListAdapter();
    }

    public void loadEventList(){
        new LoadingList().execute();
    }

    public EventListAdapter getAdapter(){
        return adapter;
    }

    public void addEvent() {router.navigateTo(MainActivity.ADD_EVENT_SCREEN);}
    public void eventInfo(int position){
        EventDto tmp = adapter.getEvents().get(position);
        router.navigateTo(MainActivity.EVENT_INFO_SCREEN, tmp); }

        public void filters(){
        router.navigateTo(MainActivity.FILTERS_SCREEN);
        }

    @Override
    public void onDestroy() {
        App.get().clearGuestEventInfoComponent();
        super.onDestroy();
    }


    private class LoadingList extends AsyncTask<Void, Void, List<EventDto>> {
        private List<EventDto> tmp = new ArrayList<>();
        IAuthRepository tmpRepository = authRepository;
        Call<EventListDto> call;
        FiltersDto filters = null;


        @Override
        protected void onPreExecute() {
            getViewState().showProgressFrame();
        }

        @Override
        protected List<EventDto> doInBackground(Void... voids) {
            try {
                if (tmpRepository.getToken()!=null){
                    if(filters==null) {
                        call = api.getLoginedListOfEventsInProgress(tmpRepository.getToken(), 0, 10);
                    } else {
                        call = api.getLoginedListOfEventsInProgress(tmpRepository.getToken(), 0, 10); //TODO
                    }
                } else {
                    call = api.getListOfEventsInProgress(0, 10);
                }
                retrofit2.Response<EventListDto> response = call.execute();
                if(response.isSuccessful()){
                    EventListDto eventListDto = response.body();
                    tmp = eventListDto.getContent();
                    return tmp;
                } else {
                    throw new Exception(response.errorBody().string());
                }
            } catch (Exception e) {
                Log.d("MY_TAG", "doInBackground: "+e.getMessage());;
            }
            return tmp;
        }

        @Override
        protected void onPostExecute(List<EventDto> list) {
           getViewState().hideProgressFrame();
            if (list!=null) {
                adapter.removeAll();
                for(int i=0; i<list.size(); i++){
                        adapter.addEvent(list.get(i));

                }

            }
        }
    }
}
