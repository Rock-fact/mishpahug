package com.kor.foodmanager.ui.eventInfo.guestEventInfoPending;


import android.content.Intent;
import android.net.Uri;
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
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.kor.foodmanager.R;
import com.kor.foodmanager.data.model.EventDto;
import com.kor.foodmanager.ui.IToolbar;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class GuestEventInfoPendingFragment extends MvpAppCompatFragment implements IGuestEventInfoPending{
    @InjectPresenter GuestEventInfoPendingPresenter presenter;
    private EventDto event;
    private Unbinder unbinder;
    private IToolbar iToolbar;
    @BindView(R.id.event_img) ImageView eventImg;
    @BindView(R.id.short_info) ConstraintLayout shortInfo;
    @BindView(R.id.family_name) TextView familyName;
    @BindView(R.id.event_date) TextView eventDate;
    @BindView(R.id.status) TextView eventStatus;
    @BindView(R.id.ratingBar) RatingBar ratingBar;
    @BindView(R.id.event_description) TextView eventDescription;
    @BindView(R.id.call_owner_btn) Button callOwnerBtn;
    @BindView(R.id.addres_txt) TextView eventAddress;
    @BindView(R.id.owner_phone) TextView ownerPhone;
    @BindView(R.id.show_on_te_map_btn) Button showOnMapBtn;
    @BindView(R.id.progressBar) ProgressBar progressBar;


    public GuestEventInfoPendingFragment() {

    }

    public static GuestEventInfoPendingFragment getNewInstance(EventDto data){
        GuestEventInfoPendingFragment fragment = new GuestEventInfoPendingFragment();
        fragment.event = data;
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guest_even_info_pending, container, false);
        unbinder = ButterKnife.bind(this, view);
        progressBar.setVisibility(View.GONE);
        if (event!=null){
            iToolbar=(IToolbar) getActivity();
            iToolbar.setTitleToolbarEnable(event.getTitle(),false,true,false);
            familyName.setText(event.getOwner().getFullName());
            eventDate.setText(event.getDate());
            eventAddress.setText(event.getAddress().showFullEventAddres());
            ratingBar.setRating(new Float(event.getOwner().getRate())); //TODO
            eventDescription.setText(event.getDescription());
            eventStatus.setText(event.getStatus().toUpperCase());
            ownerPhone.setText(event.getOwner().getPhoneNumber());
            if(event.getOwner().getPictureLink()!=null) {
                //Picasso.get().load(event.getOwner().getPictureLink().get(1)).into(eventImg); //TODO get 1 img
                Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(eventImg);
            }
        }
        return view;
    }

    @OnClick(R.id.call_owner_btn)
    void callOwner(){
        if (ownerPhone.getText()!=null){
       // presenter.callOwner(ownerPhone.getText().toString());
            Uri uri = Uri.parse("tel:"+event.getOwner().getPhoneNumber());
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(uri);
            Intent chooser = Intent.createChooser(intent,"Select app");
            startActivity(chooser);
        }
        else {
            Toast.makeText(getActivity(), "This owner has not entered his phone number",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.show_on_te_map_btn)
    void showOnMap(){
        //presenter.showOnMap(event.getAddress().getPlace_id());
//        Log.d("MY_TAG", "placeId: "+event.getAddress().getPlace_id());
//        Log.d("MY_TAG", "Location: "+event.getAddress().getLocation());
        Uri uri = Uri.parse("geo:"+event.getAddress().getLocation().getLat()+","
        +event.getAddress().getLocation().getLng());
        Log.d("MY_TAG", "showOnMap: "+uri);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        Intent chooser = Intent.createChooser(intent,"Select app");
        startActivity(chooser);
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
        eventAddress.setVisibility(View.GONE);
        ownerPhone.setVisibility(View.GONE);
        callOwnerBtn.setVisibility(View.GONE);
        showOnMapBtn.setVisibility(View.GONE);
    }

    @Override
    public void hideProgressFrame() {
        progressBar.setVisibility(View.GONE);
        eventImg.setVisibility(View.VISIBLE);
        shortInfo.setVisibility(View.VISIBLE);
        ratingBar.setVisibility(View.VISIBLE);
        eventDescription.setVisibility(View.VISIBLE);
        eventAddress.setVisibility(View.VISIBLE);
        ownerPhone.setVisibility(View.VISIBLE);
        callOwnerBtn.setVisibility(View.VISIBLE);
        showOnMapBtn.setVisibility(View.VISIBLE);

    }


}
