package com.kor.foodmanager.ui.eventList;



import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.kor.foodmanager.App;
import com.kor.foodmanager.R;
import com.kor.foodmanager.data.model.FiltersDto;
import com.kor.foodmanager.ui.IToolbar;


public class EventListFragment extends MvpAppCompatFragment implements EventListAdapter.MyClickListener, IEventList, View.OnClickListener {
    @InjectPresenter EventListPresenter presenter;
    private RecyclerView recyclerView;
    private EventListAdapter adapter;
    private Button filtersBtn;
    private FloatingActionButton addBtn;
    private ProgressBar progressBar;
    private IToolbar iToolbar;
    private FiltersDto filters;


    public EventListFragment() {

    }

    public static EventListFragment getNewInstance(FiltersDto filters) {
        EventListFragment eventListFragment = new EventListFragment();
        eventListFragment.filters = filters;
        return eventListFragment;
    }


        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_list, container, false);
        filtersBtn = view.findViewById(R.id.filter_btn);
        filtersBtn.setOnClickListener(this);
        progressBar = view.findViewById(R.id.progressBar);
        addBtn=view.findViewById(R.id.add_btn);
        addBtn.setOnClickListener(v -> {
            if(v.getId()==R.id.add_btn) {
                //Toast.makeText(App.get(), "Yeey", Toast.LENGTH_SHORT).show();
                presenter.addEvent();
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
        presenter.eventInfo(position);
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

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.filter_btn){

        }
    }
}