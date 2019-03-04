package com.kor.foodmanager.ui.eventInfo;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kor.foodmanager.R;
import com.kor.foodmanager.ui.IToolbar;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventInfoFragment extends Fragment {
    private IToolbar iToolbar;

    public EventInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        iToolbar = (IToolbar) getActivity();
        iToolbar.setTitleToolbarEnable("Event Info",true);

        return inflater.inflate(R.layout.fragment_event_info, container, false);
    }

}