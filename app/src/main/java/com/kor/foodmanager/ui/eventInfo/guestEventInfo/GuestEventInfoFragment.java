package com.kor.foodmanager.ui.eventInfo.guestEventInfo;


import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.kor.foodmanager.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class GuestEventInfoFragment extends MvpAppCompatFragment implements IGuestEventInfo, View.OnClickListener {
@InjectPresenter GuestEventInfoPresenter presenter;
@BindView(R.id.event_img) ImageView eventImg;
@BindView(R.id.short_info) ConstraintLayout shortInfo;
@BindView(R.id.family_name) TextView familyname;
@BindView(R.id.event_title) TextView eventTitle;
@BindView(R.id.event_date) TextView eventDate;
@BindView(R.id.event_address) TextView eventAddress;
@BindView(R.id.ratingBar) RatingBar ratingBar;
@BindView(R.id.event_description) TextView eventDescription;
@BindView(R.id.join_btn) Button joinBtn;
@BindView(R.id.progressBar) ProgressBar progressBar;
private Unbinder unbinder;


    public GuestEventInfoFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.guest_fragment_event_info, container, false);
        unbinder = ButterKnife.bind(this,view);
        joinBtn.setOnClickListener(this);
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
        joinBtn.setVisibility(View.GONE);
    }

    @Override
    public void hideProgressFrame() {
        progressBar.setVisibility(View.GONE);
        eventImg.setVisibility(View.VISIBLE);
        shortInfo.setVisibility(View.VISIBLE);
        ratingBar.setVisibility(View.VISIBLE);
        eventDescription.setVisibility(View.VISIBLE);
        joinBtn.setVisibility(View.VISIBLE);
    }

    @Override
    public void showToast(String s) {
        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        presenter.joinEvent();
    }
}
