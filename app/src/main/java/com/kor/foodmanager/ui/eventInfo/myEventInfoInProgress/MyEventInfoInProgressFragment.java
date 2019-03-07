package com.kor.foodmanager.ui.eventInfo.myEventInfoInProgress;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.kor.foodmanager.R;
import com.kor.foodmanager.data.model.EventDto;
import com.kor.foodmanager.ui.myEventList.TitleRow;


public class MyEventInfoInProgressFragment extends MvpAppCompatFragment {
    TitleRow event;

    public MyEventInfoInProgressFragment() {
        // Required empty public constructor
    }
    public static MyEventInfoInProgressFragment getNewInstance(TitleRow event) {
        MyEventInfoInProgressFragment myEventInfoInProgressFragment=new MyEventInfoInProgressFragment();
        myEventInfoInProgressFragment.event=event;
        return myEventInfoInProgressFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_event_info_in_progress, container, false);
    }

}
