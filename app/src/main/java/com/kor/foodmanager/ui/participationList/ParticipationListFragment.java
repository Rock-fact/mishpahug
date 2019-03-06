package com.kor.foodmanager.ui.participationList;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kor.foodmanager.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class ParticipationListFragment extends Fragment {
@BindView(R.id.rv_partisipation_list) RecyclerView recyclerView;
private Unbinder unbinder;


    public ParticipationListFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_partisipation_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }
}
