package com.kor.foodmanager.ui.userInfo;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
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

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.kor.foodmanager.R;
import com.kor.foodmanager.data.model.EventDto;
import com.kor.foodmanager.data.model.StaticfieldsDto;
import com.kor.foodmanager.data.model.UserDto;
import com.kor.foodmanager.ui.eventInfo.guestEventInfoInprogress.GuestEventInfoInprogressPresenter;
import com.kor.foodmanager.ui.eventInfo.guestEventInfoInprogress.IGuestEventInfoInprogress;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class UserInfo extends MvpAppCompatFragment {
    private UserDto user;
    private Boolean isPending;
    private Unbinder unbinder;
    @BindView(R.id.fullname_txt)
    TextView fullName;
    @BindView(R.id.confession_txt)
    TextView confession;
    @BindView(R.id.gender_txt)
    TextView gender;
    @BindView(R.id.telephone_number_txt)
    TextView telephoneNumber;
    @BindView(R.id.marital_status_txt)
    TextView maritalStatus;
    @BindView(R.id.food_preferences_txt)
    TextView foodPreferences;
    @BindView(R.id.allergy_txt)
    TextView allergy;
    @BindView(R.id.ratingBar)
    RatingBar ratingBar;
    @BindView(R.id.user_img)
    ImageView userImage;


    public UserInfo() {
    }

    public static UserInfo getNewInstance(UserDto user, boolean isPending) {
        UserInfo userInfo = new UserInfo();
        userInfo.isPending = isPending;
        userInfo.user = user;
        return userInfo;
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState!=null){
            user=(UserDto) savedInstanceState.getSerializable("user");
            isPending=savedInstanceState.getBoolean("isPending");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("user",user);
        outState.putBoolean("isNew",isPending);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_info, container, false);
        unbinder = ButterKnife.bind(this, view);

        if (user != null) {
            fullName.setText(user.getFullName());
            confession.setText(user.getConfession());
            gender.setText(user.getGender());
            if (isPending) {
                telephoneNumber.setText(user.getPhoneNumber());
            } else {
                telephoneNumber.setVisibility(View.GONE);
            }
            maritalStatus.setText(user.getMaritalStatus());
            foodPreferences.setText(inLine(user.getFoodPreferences()));
            //TODO allergy

            ratingBar.setRating(new Float(user.getRate()));


            if (user.getPictureLink() != null) {
//                Picasso.get().load(user.getPictureLink().get(0)).into(userImage);
                Picasso.get().load("https://i.imgur.com/vaZKZcz.jpg").into(userImage); //TODO get 1 img
            }
        }
        return view;
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    public static String inLine(List<String> list) {
        String res = "";
        for (String item : list) {
            if (res == "") {
                res = res + item;
            } else if (item!=""){
                res = res + ", " + item;
            }
        }
        return res;
    }

    public static List<String> inList(String str) {
        List<String> list = new ArrayList<>();
        String[] tmp;
        tmp = str.split(", ");
        for (String food : tmp
        ) {
            list.add(food);
        }
        return list;
    }
}
