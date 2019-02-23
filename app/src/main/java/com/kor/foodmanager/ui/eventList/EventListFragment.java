package com.kor.foodmanager.ui.eventList;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.kor.foodmanager.R;
import com.kor.foodmanager.data.auth.AuthRepository;
import com.kor.foodmanager.data.model.EventDto;
import com.kor.foodmanager.data.model.EventListDto;
import com.kor.foodmanager.data.model.UserDto;
import com.kor.foodmanager.data.provider.web.Api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EventListFragment extends Fragment implements EventListAdapter.MyClickListener{
    private RecyclerView recyclerView;
    private EventListAdapter adapter;
    private Button filtersBtn;
    private FloatingActionButton addBtn;
    private ProgressBar progressBar;
//    private boolean listExists;


    public EventListFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_list, container, false);
        filtersBtn = view.findViewById(R.id.filter_btn);
        progressBar = view.findViewById(R.id.progressBar);
        addBtn=view.findViewById(R.id.add_btn);
        // TODO addBtn is visible only if user is family

        recyclerView = view.findViewById(R.id.eventList_rv);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new EventListAdapter();
        adapter.setListener(this);
        recyclerView.setAdapter(adapter);
        Log.d("MY_TAG", "onCreateView: "+adapter.toString());
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
         new LoadingList().execute();

    }

    @Override
    public void onItemClick(int position) {
        //TODO
    }


    private class LoadingList extends AsyncTask<Void, Void, List<EventDto>> {

        private static final String BASE_URL = "https://mishpahug-java221-team-a.herokuapp.com";
        private Api api;
        private List<EventDto> tmp = new ArrayList<>();
        private String token = getActivity().getSharedPreferences("AUTH", Context.MODE_PRIVATE).getString("CURR", null);

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            filtersBtn.setVisibility(View.GONE);
            addBtn.setClickable(false);
        }

        @Override
        protected List<EventDto> doInBackground(Void... voids) {
            try {
                // To be deleted TODO
//                for (int i=0; i<8; i++){
//                    EventDto event = new EventDto();
//                    UserDto owner = new UserDto();
//                    owner.setFullName("Zuz");
//                    event.setOwner(owner);
//                    event.setTitle("title");
//                    event.setDate("15.03.2019");
//                    tmp.add(event);
//                    Log.d("MY_TAG", "EventListAdapter: "+event.toString());
//                    Log.d("MY_TAG", "EventListAdapter: "+tmp.size());
//
//                }

                Retrofit retrofit = new Retrofit.Builder().client(new OkHttpClient()).baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create()).build();
                api = retrofit.create(Api.class);
                Log.d("MY_TAG", "doInBackground: 2");
                Call<EventListDto> call = api.getMyEventList(token);
                Log.d("MY_TAG", "doInBackground: 3");
                retrofit2.Response<EventListDto> response = call.execute();
                Log.d("MY_TAG", "doInBackground: 4");
                if(response.isSuccessful()){
                    Log.d("MY_TAG", "doInBackground: 5");
                    EventListDto eventListDto = response.body();
                    Log.d("MY_TAG", "doInBackground: "+eventListDto);
                    tmp = eventListDto.getEvents();
                    Log.d("MY_TAG", "doInBackground: ");
                    return tmp;
                } else {
                    throw new Exception(response.errorBody().string());
                }
//
            } catch (Exception e) {
                Log.d("MY_TAG", "doInBackground: "+e.getMessage());;
            }
                return tmp;
        }

        @Override
        protected void onPostExecute(List<EventDto> list) {
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            filtersBtn.setVisibility(View.VISIBLE);
            addBtn.setClickable(true);

//            listExists = true;
                Log.d("MY_TAG", "onPostExecute: ");
//                adapter.removeAll();
                if (list!=null) {
                    Log.d("MY_TAG", "onPostExecute: "+list);
                    for(int i=0; i<list.size(); i++){
                        adapter.addEvent(list.get(i));
                    }

                }
             else {
                    Log.d("MY_TAG", "onPostExecute: "+list);
                Toast.makeText(getActivity(), "Error - empty list", Toast.LENGTH_SHORT).show();
            }
        }
    }



}