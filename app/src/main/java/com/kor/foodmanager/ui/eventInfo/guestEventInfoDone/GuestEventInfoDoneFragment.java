package com.kor.foodmanager.ui.eventInfo.guestEventInfoDone;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kor.foodmanager.R;


public class GuestEventInfoDoneFragment extends Fragment {


    public GuestEventInfoDoneFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_guest_even_info_done, container, false);
    }

}
