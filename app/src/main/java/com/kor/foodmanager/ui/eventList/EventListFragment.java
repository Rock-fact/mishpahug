package com.kor.foodmanager.ui.eventList;


import android.os.Binder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.kor.foodmanager.R;
import com.kor.foodmanager.data.model.EventDto;
import com.kor.foodmanager.data.model.EventsInProgressRequestDto;
import com.kor.foodmanager.ui.IToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class EventListFragment extends MvpAppCompatFragment implements EventListAdapter.MyClickListener, SwipeRefreshLayout.OnRefreshListener, IEventList {
    @InjectPresenter
    EventListPresenter presenter;
    @BindView(R.id.eventList_rv)
    RecyclerView recyclerView;
    @BindView(R.id.filter_btn)
    Button filtersBtn;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.add_btn)
    FloatingActionButton addBtn;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;

    private EventListAdapter adapter;

    private IToolbar iToolbar;
    private EventsInProgressRequestDto filters;

    public static final int PAGE_START = 0;
    private int currentPage = PAGE_START;
    private boolean isLastPage = false;
    public static final int TOTAL_ON_PAGE = 2;
    private boolean isLoading = false;
    private Unbinder unbinder;

    public EventListFragment() {
        filters = null;
    }

    public static EventListFragment getNewInstance(EventsInProgressRequestDto filters) {
        EventListFragment eventListFragment = new EventListFragment();
        eventListFragment.filters = filters;
        return eventListFragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null){
            filters = (EventsInProgressRequestDto)savedInstanceState.getSerializable("filters");
        }
        setLoading(currentPage, TOTAL_ON_PAGE);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("event",filters);
    }

    public void setLoading(int currentPage, int totalPage) {
        Log.d("VOVA", "setLoading: current page"+currentPage);
        if (filters == null) {
            presenter.loadEventList(currentPage, totalPage);
        } else {
            presenter.loadEventList(filters, currentPage, totalPage);
        }
        this.currentPage++;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_list, container, false);
        unbinder=ButterKnife.bind(this, view);

        swipeRefresh.setOnRefreshListener(this);

        recyclerView.setHasFixedSize(true);
        android.support.v7.widget.LinearLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = presenter.getAdapter();
        adapter.setListener(this);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new PaginationScrollListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                Log.d("MY_TAG", "loadMoreItems: ");
                isLoading = true;

                setLoading(currentPage, TOTAL_ON_PAGE);
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

        iToolbar = (IToolbar) getActivity();
        iToolbar.setTitleToolbarEnable("Event list", true,false,false);
        return view;
    }

    @OnClick(R.id.filter_btn)
    public void onFilterClick() {
        presenter.filters();
    }

    @OnClick(R.id.add_btn)
    public void onAddBtnClick() {
        presenter.addEvent();
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
    public void swipeRefresh() {
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void isLastPage(boolean isLast) {
        isLastPage = isLast;
    }

    @Override
    public void isLoading() {
        isLoading = false;
    }


    @Override
    public void onRefresh() {
        Log.d("MY_TAG", "onRefresh: ");
        currentPage = PAGE_START;
        isLastPage = false;
        adapter.clear();
        setLoading(currentPage, TOTAL_ON_PAGE);
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }
}