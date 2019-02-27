package com.kor.foodmanager.ui.eventInfo;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kor.foodmanager.R;


public class GuestEvenInfoPendingFragment extends Fragment {


    public GuestEvenInfoPendingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_guest_even_info_pending, container, false);
    }

}
