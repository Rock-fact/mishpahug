package com.kor.foodmanager.ui.eventInfo.guestEventInfoPending;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kor.foodmanager.R;
import com.kor.foodmanager.data.model.EventDto;


public class GuestEventInfoPendingFragment extends Fragment {
    private EventDto event;


    public GuestEventInfoPendingFragment() {

    }

    public static GuestEventInfoPendingFragment getNewInstance(EventDto data){
        GuestEventInfoPendingFragment fragment = new GuestEventInfoPendingFragment();
        fragment.event = data;
        return fragment;
    }

//    public static android.support.v4.app.Fragment getNewInstance(EventDto data) {
//        GuestEventInfoPendingFragment fragment = new GuestEventInfoPendingFragment();
//        fragment.event = data;
//        return fragment;
//    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_guest_even_info_pending, container, false);
    }

}
