package com.kor.foodmanager.ui.eventList;



import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.kor.foodmanager.App;
import com.kor.foodmanager.R;


public class EventListFragment extends MvpAppCompatFragment implements EventListAdapter.MyClickListener, IEventList {
    @InjectPresenter EventListPresenter presenter;
    private RecyclerView recyclerView;
    private EventListAdapter adapter;
    private Button filtersBtn;
    private FloatingActionButton addBtn;
    private ProgressBar progressBar;



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
        presenter.eventInfo();
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

}