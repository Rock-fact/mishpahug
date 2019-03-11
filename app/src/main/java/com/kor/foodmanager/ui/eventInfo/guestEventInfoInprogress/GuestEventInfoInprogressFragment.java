package com.kor.foodmanager.ui.eventInfo.guestEventInfoInprogress;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
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
import com.kor.foodmanager.data.model.EventDto;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class GuestEventInfoInprogressFragment extends MvpAppCompatFragment implements IGuestEventInfoInprogress{
    @InjectPresenter GuestEventInfoInprogressPresenter presenter;
    private EventDto event;
    private Unbinder unbinder;
    @BindView(R.id.event_img)
    ImageView eventImg;
    @BindView(R.id.short_info)
    ConstraintLayout shortInfo;
    @BindView(R.id.family_name)
    TextView familyName;
    @BindView(R.id.event_title) TextView eventTitle;
    @BindView(R.id.event_date) TextView eventDate;
    @BindView(R.id.status) TextView eventStatus;
    @BindView(R.id.ratingBar)
    RatingBar ratingBar;
    @BindView(R.id.event_description) TextView eventDescription;
    @BindView(R.id.unsubscribe_btn) Button unsubscribeBtn;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;


    public GuestEventInfoInprogressFragment() {

    }

    public static GuestEventInfoInprogressFragment getNewInstance(EventDto data){
        GuestEventInfoInprogressFragment fragment = new GuestEventInfoInprogressFragment();
        fragment.event = data;
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guest_even_info_inprogress, container, false);
        unbinder = ButterKnife.bind(this, view);
        progressBar.setVisibility(View.GONE);
        if (event!=null) {
            eventTitle.setText(event.getTitle());
            familyName.setText(event.getOwner().getLastName());
            Log.d("MY_TAG", "FamilyName: " + event.getOwner().getFullName());
            eventDate.setText(event.getDate());
            ratingBar.setRating(new Float(event.getOwner().getRate())); //TODO
            eventDescription.setText(event.getDescription());
            eventStatus.setText(event.getStatus().toUpperCase());
            if (event.getOwner().getPictureLink() != null) {
                Picasso.get().load(event.getOwner().getPictureLink().get(1)).into(eventImg); //TODO get 1 img
//                Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(eventImg);
            }
        }
        return view;
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    @OnClick(R.id.unsubscribe_btn)
    @Override
    public void showDialogToUnsubscribe() {
        new AlertDialog.Builder(getActivity())
                .setMessage("Are you sure, that you want to unsubscribe?")
                .setTitle("Unsubscribe")
                .setPositiveButton("Yes", (dialog, which) -> presenter.unsubscribe(event.getEventId()))
                .setNegativeButton("No", null)
                .setCancelable(false)
                .create()
                .show();
       }

    @Override
    public void showSuccessDialog(String s) {
        new android.app.AlertDialog.Builder(getActivity()).setMessage(s)
                .setPositiveButton("ok", null)
                .create()
                .show();
    }


    @Override
    public void showProgressFrame() {
        progressBar.setVisibility(View.VISIBLE);
        eventImg.setVisibility(View.GONE);
        shortInfo.setVisibility(View.GONE);
        ratingBar.setVisibility(View.GONE);
        eventDescription.setVisibility(View.GONE);
        unsubscribeBtn.setVisibility(View.GONE);
    }

    @Override
    public void hideProgressFrame() {
        progressBar.setVisibility(View.GONE);
        eventImg.setVisibility(View.VISIBLE);
        shortInfo.setVisibility(View.VISIBLE);
        ratingBar.setVisibility(View.VISIBLE);
        eventDescription.setVisibility(View.VISIBLE);
        unsubscribeBtn.setVisibility(View.GONE);
    }
}
