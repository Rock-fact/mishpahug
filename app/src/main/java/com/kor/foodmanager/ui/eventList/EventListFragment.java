package com.kor.foodmanager.ui.eventList;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.kor.foodmanager.App;
import com.kor.foodmanager.R;
import com.kor.foodmanager.data.auth.AuthRepository;
import com.kor.foodmanager.data.model.EventDto;
import com.kor.foodmanager.data.model.EventListDto;
import com.kor.foodmanager.data.model.UserDto;
import com.kor.foodmanager.data.provider.web.Api;
import com.kor.foodmanager.ui.IToolbar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.OnClick;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EventListFragment extends MvpAppCompatFragment implements EventListAdapter.MyClickListener, IEventList {
    @InjectPresenter EventListPresenter presenter;
    private RecyclerView recyclerView;
    private EventListAdapter adapter;
    private Button filtersBtn;
    private FloatingActionButton addBtn;
    private ProgressBar progressBar;
    private IToolbar iToolbar;


    public EventListFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_list, container, false);
        filtersBtn = view.findViewById(R.id.filter_btn);
        progressBar = view.findViewById(R.id.progressBar);
        addBtn=view.findViewById(R.id.add_btn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId()==R.id.add_btn) {
                    Toast.makeText(App.get(), "Yeey", Toast.LENGTH_SHORT).show();
                    presenter.addEvent();
                }
            }
        });
        recyclerView = view.findViewById(R.id.eventList_rv);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = presenter.getAdapter();
        adapter.setListener(this);
        recyclerView.setAdapter(adapter);

        iToolbar=(IToolbar) getActivity();
        iToolbar.setTitleToolbarEnable("Event list",true);


        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
         presenter.loadEventList();

    }

    @Override
    public void onItemClick(int position) {
        //TODO
    }


    @Override
    public void showProgressFrame() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        filtersBtn.setVisibility(View.GONE);
        addBtn.setClickable(false);
    }

    @Override
    public void hideProgressFrame() {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        filtersBtn.setVisibility(View.VISIBLE);
        addBtn.setClickable(true);
    }

//    @OnClick (R.id.add_btn)
//    public void addBtnClicked (){
////        presenter.addEvent();
//        Toast.makeText(App.get(), "Yeey", Toast.LENGTH_SHORT).show();
//    }



}