package com.kor.foodmanager.ui.eventInfo.guestEventInfoDone;


import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.kor.foodmanager.R;
import com.kor.foodmanager.data.model.EventDto;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class GuestEventInfoDoneFragment extends Fragment implements IGuestEventInfoDone {
    private EventDto event;
    private Unbinder unbinder;
    @BindView(R.id.event_img) ImageView eventImg;
    @BindView(R.id.short_info) ConstraintLayout shortInfo;
    @BindView(R.id.family_name) TextView familyName;
    @BindView(R.id.event_title) TextView eventTitle;
    @BindView(R.id.event_date) TextView eventDate;
    @BindView(R.id.status) TextView eventStatus;
    @BindView(R.id.ratingBar) RatingBar ratingBar;
    @BindView(R.id.event_description) TextView eventDescription;
    @BindView(R.id.vote_btn) Button voteBtn;
    @BindView(R.id.progressBar) ProgressBar progressBar;

    public GuestEventInfoDoneFragment() {

    }

    public static GuestEventInfoDoneFragment getNewInstance(EventDto data){
        GuestEventInfoDoneFragment fragment = new GuestEventInfoDoneFragment();
        fragment.event = data;
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guest_even_info_done, container, false);
        unbinder= ButterKnife.bind(this, view);
        if (event!=null) {
            eventTitle.setText(event.getTitle());
            familyName.setText(event.getOwner().getLastName());
            Log.d("MY_TAG", "FamilyName: " + event.getOwner().getLastName());
            eventDate.setText(event.getDate());
            ratingBar.setRating(new Float(event.getOwner().getRate())); //TODO
            eventDescription.setText(event.getDescription());
            eventStatus.setText(event.getStatus().toUpperCase());
            if (event.getOwner().getPictureLink() != null) {
                //Picasso.get().load(event.getOwner().getPictureLink().get(1)).into(eventImg); //TODO get 1 img
                Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(eventImg);
            }
        }
        return view;
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    @Override
    public void showProgressFrame() {
        progressBar.setVisibility(View.VISIBLE);
        eventImg.setVisibility(View.GONE);
        shortInfo.setVisibility(View.GONE);
        ratingBar.setVisibility(View.GONE);
        eventDescription.setVisibility(View.GONE);
        voteBtn.setVisibility(View.GONE);
    }

    @Override
    public void hideProgressFrame() {
        progressBar.setVisibility(View.GONE);
        eventImg.setVisibility(View.VISIBLE);
        shortInfo.setVisibility(View.VISIBLE);
        ratingBar.setVisibility(View.VISIBLE);
        eventDescription.setVisibility(View.VISIBLE);
        voteBtn.setVisibility(View.VISIBLE);
    }
}
