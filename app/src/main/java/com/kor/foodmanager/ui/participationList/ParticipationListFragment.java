package com.kor.foodmanager.ui.participationList;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.kor.foodmanager.R;
import com.kor.foodmanager.ui.IToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class ParticipationListFragment extends MvpAppCompatFragment implements IParticipationList, ParticipationListAdapter.MyClickListener {
    @InjectPresenter ParticipationListPresenter presenter;
    @BindView(R.id.rv_partisipation_list) RecyclerView recyclerView;
    @BindView(R.id.progressBar) ProgressBar progressBar;
    private Unbinder unbinder;
    private ParticipationListAdapter adapter;
    private IToolbar iToolbar;

    public ParticipationListFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_participation_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        iToolbar = (IToolbar) getActivity();
        iToolbar.setTitleToolbarEnable("Participation list",false, true, true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = presenter.getAdapter();
        adapter.setListener(this);
        recyclerView.setAdapter(adapter);

//        ItemTouchHelper helper = new ItemTouchHelper(new MyTouchCallBack());
//        helper.attachToRecyclerView(recyclerView);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.loadParticipationList();
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    @Override
    public void onItemClick(int position) {
        presenter.eventInfo(position);
    }

    @Override
    public void showProgressFrame() {
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressFrame() {
        recyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

//    class MyTouchCallBack extends ItemTouchHelper.Callback{
//
//        @Override
//        public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
//            return makeMovementFlags(0, ItemTouchHelper.END | ItemTouchHelper.START);
//        }
//
//        @Override
//        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
//            return false;
//        }
//
//        @Override
//        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
//            presenter.loadParticipationList();
//        }
//    }


}
