package com.kor.foodmanager.ui.eventInfo;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kor.foodmanager.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyEventInfoDoneFragment extends Fragment {


    public MyEventInfoDoneFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_event_info_done, container, false);
    }

}
