package com.kor.foodmanager.ui.aboutmyself;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import com.kor.foodmanager.R;
import com.kor.foodmanager.data.model.StaticfieldsDto;
import com.kor.foodmanager.data.model.UserDto;


import java.util.ArrayList;
import java.util.List;


public class AboutMyselfFragment extends MvpAppCompatFragment implements IAboutMyselfFragment, View.OnClickListener {

    @InjectPresenter
    AboutMyselfPresenter presenter;

    private UserDto user;
    private boolean isNew;
    private StaticfieldsDto staticFields;

    EditText marital, food, allergy;
    Spinner spinnerMarital, spinnerFood, spinnerAllergy;
    Button saveBtn;

    public static AboutMyselfFragment getNewInstance(UserDto user,boolean isNew){
        AboutMyselfFragment fragment = new AboutMyselfFragment();
        fragment.user = user;
        fragment.isNew = isNew;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        staticFields = new StaticfieldsDto();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.about_myself, container, false);
        marital = view.findViewById(R.id.marital);
        food = view.findViewById(R.id.food);
        allergy = view.findViewById(R.id.allergy);
        spinnerMarital = view.findViewById(R.id.spinnerMarital);
        spinnerFood = view.findViewById(R.id.spinnerFood);
        spinnerAllergy = view.findViewById(R.id.spinnerAllergy);
        saveBtn = view.findViewById(R.id.save_btn);

        saveBtn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.updateStaticFields();
    }

    @Override
    public void updateStaticFields(StaticfieldsDto staticFields){
        this.staticFields = staticFields;
        updateSpinersValues();
    }

    private void updateSpinersValues(){
        ArrayAdapter<String> maritalAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,staticFields.getMaritalStatus() );
        maritalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMarital.setAdapter(maritalAdapter);

        ArrayAdapter<String> foodPreferenceAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, staticFields.getFoodPreferences());
        foodPreferenceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFood.setAdapter(foodPreferenceAdapter);

    }

    @Override
    public void onClick(View view){
        if(view.getId() == R.id.save_btn){
            user.getFoodPreferences().add(spinnerFood.getSelectedItem().toString());
            user.setMaritalStatus(spinnerMarital.getSelectedItem().toString());
            //presenter.updateUserProfile(user);
            presenter.backToRegistrationFragment();
        }
    }


}

