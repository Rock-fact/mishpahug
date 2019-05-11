package com.kor.foodmanager.ui.registration;

import android.os.Bundle;

import android.support.annotation.NonNull;

import android.support.annotation.Nullable;

import android.util.Log;

import android.view.LayoutInflater;

import android.view.View;

import android.view.ViewGroup;

import android.widget.Button;

import android.widget.EditText;

import android.widget.ImageView;
import android.widget.TextView;


import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.kor.foodmanager.R;
import com.kor.foodmanager.data.model.UserDto;
import com.kor.foodmanager.ui.IToolbar;


public class RegistrationFragment extends MvpAppCompatFragment implements IRegistrationFragment, View.OnClickListener {

    @InjectPresenter
    RegistrationPresenter presenter;
    private UserDto user;

    private TextView nameTxt, changeName;
    private EditText emailInput, phoneInput, passwordInput;
    private Button saveBtn;
    private ImageView avatar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = new UserDto();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.registration_fragment, container, false);

        nameTxt = view.findViewById(R.id.name_txt);
        changeName = view.findViewById(R.id.change_name);
        changeName.setOnClickListener(this);

        emailInput = view.findViewById(R.id.email_input);
        phoneInput = view.findViewById(R.id.phone_tinput);
        passwordInput = view.findViewById(R.id.password_input);

        saveBtn = view.findViewById(R.id.save_btn);
        saveBtn.setOnClickListener(this);

        avatar = view.findViewById(R.id.imageView);


        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        nameTxt.setText(user.getFirstName() + " " + user.getLastName());
    }

    @Override

    public void onClick(View v) {
        if(v.getId() == R.id.change_name){
            presenter.startPersonalProf(user);
        }else if(v.getId() == R.id.save_btn){
            presenter.registration(emailInput.getText().toString(), passwordInput.getText().toString(), user);
        }
    }

}