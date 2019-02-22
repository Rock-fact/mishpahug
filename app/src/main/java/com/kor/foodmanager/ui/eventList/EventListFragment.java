package com.kor.foodmanager.ui.eventList;


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
import com.kor.foodmanager.data.model.EventDto;
import com.kor.foodmanager.data.model.UserDto;

import java.util.ArrayList;
import java.util.Date;

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


    private class LoadingList extends AsyncTask<Void, Void, String> {

        ArrayList<EventDto> tmp;

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            filtersBtn.setVisibility(View.GONE);
            addBtn.setClickable(false);
            tmp = new ArrayList<>();
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                tmp = new ArrayList<>();
                //TODO get list from server
                // To be deleted TODO
                for (int i=0; i<8; i++){
                    EventDto event = new EventDto();
                    UserDto owner = new UserDto();
                    owner.setFullName("Zuz");
                    event.setOwner(owner);
                    event.setTitle("title");
                    event.setDate(new Date());
                    tmp.add(event);
                    Log.d("MY_TAG", "EventListAdapter: "+event.toString());
                    Log.d("MY_TAG", "EventListAdapter: "+tmp.size());

                }
                wait(500);


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            filtersBtn.setVisibility(View.VISIBLE);
            addBtn.setClickable(true);
//            listExists = true;
                Log.d("MY_TAG", "onPostExecute: ");
//                adapter.removeAll();
                if (!tmp.isEmpty()) {
                    Log.d("MY_TAG", "onPostExecute: "+tmp.size());
                    for(int i=0; i<tmp.size(); i++){
                        adapter.addEvent(tmp.get(i));
                    }

                }
             else {
                Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
            }
        }
    }



}