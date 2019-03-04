package com.kor.foodmanager.ui.eventInfo.GuestEventInfo;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.kor.foodmanager.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class GuestEventInfoFragment extends Fragment {
@BindView(R.id.event_img) ImageView eventImg;
@BindView(R.id.family_name) TextView familyname;
@BindView(R.id.event_title) TextView eventTitle;
@BindView(R.id.event_date) TextView eventDate;
@BindView(R.id.event_address) TextView eventAddress;
@BindView(R.id.ratingBar) RatingBar ratingBar;
@BindView(R.id.event_description) TextView eventDescription;
@BindView(R.id.join_btn) Button joinBtn;
private Unbinder unbinder;


    public GuestEventInfoFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.guest_fragment_event_info, container, false);
        unbinder = ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }
}
