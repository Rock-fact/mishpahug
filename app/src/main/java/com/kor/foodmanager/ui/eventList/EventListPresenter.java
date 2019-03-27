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
import com.kor.foodmanager.data.model.EventsInProgressRequestDto;
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

    EventsInProgressRequestDto listFilters;

private EventListAdapter adapter;

    public EventListPresenter() {
        App.get().guestEventInfoComponent().inject(this);
        adapter = new EventListAdapter();
    }

    public void loadEventList(int currentPage, int totalPage){
        new LoadingList(currentPage,totalPage).execute();
    }

    public void loadEventList(EventsInProgressRequestDto filters,int currentPage, int totalPage){
        new LoadingList(filters,currentPage,totalPage).execute();
    }

    public EventListAdapter getAdapter(){
        return adapter;
    }

    public void addEvent() {router.navigateTo(MainActivity.ADD_EVENT_SCREEN);}
    public void eventInfo(int position){
        EventDto tmp = adapter.getEvents().get(position);
        router.navigateTo(MainActivity.EVENT_INFO_SCREEN, tmp); }

        public void filters(){
        if(listFilters!=null){
            Log.d("MY_TAG", "listFilters!=null "+listFilters.toString());
            router.navigateTo(MainActivity.FILTERS_SCREEN, listFilters);
        } else {
            router.navigateTo(MainActivity.FILTERS_SCREEN);
        }
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
        EventsInProgressRequestDto filters;
        int currentPage;
        int totalPage;

        public LoadingList(int currentPage,int totalPage) {
            this.currentPage=currentPage;
            this.totalPage=totalPage;
        }

        public LoadingList(EventsInProgressRequestDto filters,int currentPage,int totalPage) {
            this.filters = filters;
            this.currentPage=currentPage;
            this.totalPage=totalPage;
        }

        @Override
        protected void onPreExecute() {
           if (currentPage==0)getViewState().showProgressFrame();
        }

        @Override
        protected List<EventDto> doInBackground(Void... voids) {
            try {
                if (tmpRepository.getToken()!=null){
                    if(filters==null) {
                        call = api.getLoginedListOfEventsInProgress(tmpRepository.getToken(), currentPage, totalPage);
                    } else {
                        call = api.getLoginedListOfEventsInProgress(tmpRepository.getToken(), currentPage, totalPage, filters); //TODO
                    }
                } else {
                    call = api.getListOfEventsInProgress(currentPage, totalPage);
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
          if (currentPage==0)getViewState().hideProgressFrame();
           if(filters!=null){
               listFilters=filters;
           }
            Log.d("VOVA", "onPostExecute: listsize"+list.size());
            if (list!=null) {
                if (currentPage != EventListFragment.PAGE_START) adapter.removeLoading();
                else
                    {
                        adapter.clear();
                        getViewState().isLastPage(false);
                    }
                adapter.addAll(list);
                getViewState().swipeRefresh();
                if (!(list.size() < totalPage)) adapter.addLoading();
                else getViewState().isLastPage(true);
                getViewState().isLoading();
            }
        }
    }
}
