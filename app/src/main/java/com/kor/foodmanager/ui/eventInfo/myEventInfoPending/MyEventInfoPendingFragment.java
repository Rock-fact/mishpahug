package com.kor.foodmanager.ui.eventInfo.myEventInfoPending;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.kor.foodmanager.R;
import com.kor.foodmanager.data.model.EventDto;
import com.kor.foodmanager.ui.IToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class MyEventInfoPendingFragment extends MvpAppCompatFragment implements MyEventInfoPendingAdapter.MyClickListener, IMyEventInfoPending {
    @InjectPresenter
    MyEventInfoPendingPresenter presenter;
    @BindView(R.id.subscribers_list)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.date_txt)
    TextView date;
    @BindView(R.id.event_description)
    TextView eventDescription;

    private MyEventInfoPendingAdapter adapter;
    private IToolbar iToolbar;
    private Unbinder unbinder;
    private EventDto event;


    public MyEventInfoPendingFragment() {
    }

    public static MyEventInfoPendingFragment getNewInstance(EventDto event){
        MyEventInfoPendingFragment myEventInfoPendingFragment=new MyEventInfoPendingFragment();
        myEventInfoPendingFragment.event=event;
        return myEventInfoPendingFragment;
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
        View view = inflater.inflate(R.layout.fragment_my_event_info_pending, container, false);
        unbinder = ButterKnife.bind(this, view);
        date.setText(event.getDate());
        eventDescription.setText(event.getDescription());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = presenter.getAdapter();
        adapter.setSubscribers(event.getParticipants());
        adapter.setListener(this);
        recyclerView.setAdapter(adapter);
        iToolbar = (IToolbar) getActivity();
        iToolbar.setTitleToolbarEnable(event.getTitle(), false,true,false);
        return view;
    }




    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

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
