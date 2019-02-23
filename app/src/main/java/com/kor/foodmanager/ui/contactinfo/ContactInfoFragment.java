package com.kor.foodmanager.ui.contactinfo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.kor.foodmanager.R;
import com.kor.foodmanager.data.model.UserDto;


public class ContactInfoFragment extends MvpAppCompatFragment implements IContactInfoFragment, View.OnClickListener {

    @InjectPresenter
    ContactInfoPresenter presenter;

    private UserDto user;
    private boolean isNew;
    private EditText emailInput, phoneInput;
    Button nextBtn;

    public static ContactInfoFragment getNewInstance(UserDto user, boolean isNew){
        ContactInfoFragment fragment = new ContactInfoFragment();
        fragment.user = user;
        fragment.isNew = isNew;
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contact_info, container, false);

        emailInput = view.findViewById(R.id.email);
        phoneInput = view.findViewById(R.id.phoneNumber);
        nextBtn = view.findViewById(R.id.next_btn);
        nextBtn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.next_btn){
            user.setPhoneNumber(phoneInput.getText().toString());
            presenter.startAboutMyself(user);
        }
    }
}