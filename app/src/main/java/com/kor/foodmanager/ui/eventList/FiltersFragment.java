package com.kor.foodmanager.ui.eventList;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.kor.foodmanager.R;
import com.kor.foodmanager.data.model.StaticfieldsDto;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class FiltersFragment extends Fragment {
private Unbinder unbinder;
private StaticfieldsDto staticfields;

@BindView(R.id.confession_spinner)
    Spinner confessionSpinner;

    public FiltersFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filters, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }
}
