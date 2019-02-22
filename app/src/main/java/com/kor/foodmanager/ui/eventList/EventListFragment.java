package com.kor.foodmanager.ui.eventList;


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

    public EventListFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_list, container, false);
        filtersBtn = view.findViewById(R.id.filter_btn);
        addBtn=view.findViewById(R.id.add_btn);
        // TODO addBtn is visible only if user is family

        recyclerView = view.findViewById(R.id.eventList_rv);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);


        // To be deleted TODO
        ArrayList<EventDto> events = new ArrayList<>();
        for (int i=0; i<8; i++){
            EventDto event = new EventDto();
            UserDto owner = new UserDto();
            owner.setFullName("Zuz");
            event.setOwner(owner);
            event.setTitle("title");
            event.setDate(new Date());
            events.add(event);
            Log.d("MY_TAG", "EventListAdapter: "+event.toString());
            Log.d("MY_TAG", "EventListAdapter: "+events.size());

        }
        //
        adapter = new EventListAdapter(events);
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

    }

    @Override
    public void onItemClick(int position) {
        //TODO
    }
}