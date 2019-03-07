package com.kor.foodmanager.ui.eventInfo.myEventInfoPending;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.kor.foodmanager.R;
import com.kor.foodmanager.data.model.EventDto;
import com.kor.foodmanager.ui.myEventList.TitleRow;


public class MyEventInfoPendingFragment extends MvpAppCompatFragment {
TitleRow event;

    public MyEventInfoPendingFragment() {
        // Required empty public constructor
    }

    public static MyEventInfoPendingFragment getNewInstance(TitleRow event){
        MyEventInfoPendingFragment myEventInfoPendingFragment=new MyEventInfoPendingFragment();
        myEventInfoPendingFragment.event=event;
        return myEventInfoPendingFragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_event_info_pending, container, false);
    }

}
