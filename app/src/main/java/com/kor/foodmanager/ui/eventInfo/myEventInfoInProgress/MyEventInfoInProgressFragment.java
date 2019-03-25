package com.kor.foodmanager.ui.eventInfo.myEventInfoInProgress;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.kor.foodmanager.R;
import com.kor.foodmanager.data.model.EventDto;
import com.kor.foodmanager.ui.IToolbar;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class MyEventInfoInProgressFragment extends MvpAppCompatFragment implements MyEventInfoInProgressAdapter.MyClickListener, IMyEventInfoInProgress {
    @InjectPresenter
    MyEventInfoInProgressPresenter presenter;
    @BindView(R.id.subscribers_list)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.finishInvite_btn)
    Button finishInvite;
    @BindView(R.id.date_txt)
    TextView date;
    @BindView(R.id.event_description)
    TextView eventDescription;

    private MyEventInfoInProgressAdapter adapter;
    private IToolbar iToolbar;
    private Unbinder unbinder;
    private EventDto event;



    public MyEventInfoInProgressFragment() {
    }

    public static MyEventInfoInProgressFragment getNewInstance(EventDto event){
        MyEventInfoInProgressFragment myEventInfoInProgressFragment=new MyEventInfoInProgressFragment();
        myEventInfoInProgressFragment.event=event;
        return myEventInfoInProgressFragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null){
            event = (EventDto)savedInstanceState.getSerializable("event");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("event",event);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_event_info_in_progress, container, false);
        unbinder = ButterKnife.bind(this, view);



        finishInvite.setOnClickListener(v -> {
            presenter.changeStatusToEvent(event);
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = presenter.getAdapter();

        adapter.setSubscribers(event.getParticipants(),event.getEventId());
        adapter.setListener(this);
        recyclerView.setAdapter(adapter);
        iToolbar = (IToolbar) getActivity();
        iToolbar.setTitleToolbarEnable(event.getTitle(), false,true,false);
        date.setText(event.getDate());
        eventDescription.setText(event.getDescription());


        return view;
    }




    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    @Override
    public void onItemClick(Long eventId, int adapterPosition) {
        presenter.inviteToEvent(eventId, adapterPosition);
    }

    @Override
    public void userInfo(int adapterPosition) {
        presenter.userInfo(adapterPosition);
    }


    @Override
    public void showProgressFrame() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void hideProgressFrame() {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

}
