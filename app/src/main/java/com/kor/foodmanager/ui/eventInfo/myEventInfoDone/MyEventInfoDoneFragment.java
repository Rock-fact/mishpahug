package com.kor.foodmanager.ui.eventInfo.myEventInfoDone;


import android.app.TimePickerDialog;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.kor.foodmanager.R;
import com.kor.foodmanager.data.model.EventDto;
import com.kor.foodmanager.ui.myEventList.TitleRow;


public class MyEventInfoDoneFragment extends MvpAppCompatFragment {
    TitleRow event;

    public MyEventInfoDoneFragment() {
        // Required empty public constructor
    }

    public static MyEventInfoDoneFragment getNewInstance(TitleRow event){
        MyEventInfoDoneFragment myEventInfoDoneFragment = new MyEventInfoDoneFragment();
        myEventInfoDoneFragment.event = event;
        return myEventInfoDoneFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_event_info_done, container, false);
    }

}
