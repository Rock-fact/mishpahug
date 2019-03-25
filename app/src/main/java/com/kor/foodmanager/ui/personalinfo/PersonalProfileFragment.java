package com.kor.foodmanager.ui.personalinfo;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.kor.foodmanager.R;
import com.kor.foodmanager.data.model.StaticfieldsDto;
import com.kor.foodmanager.data.model.UserDto;


import java.util.Calendar;


public class PersonalProfileFragment extends MvpAppCompatFragment implements IPersonalProfileFragment, View.OnClickListener {

    @InjectPresenter
    PersonalProfilePresenter presenter;

    private UserDto user;
    private StaticfieldsDto staticFields;
    private boolean isNew;

    EditText firstName, lastName, dateOfBirth, confession, gender;
    Spinner spinnerConfession, spinnerGender;
    FrameLayout dateFrame;
    DatePicker dataPicker;
    ImageView calendarBtn;
    DatePickerDialog datePickerDialog;
    Button nextBtn;

    public static PersonalProfileFragment getNewInstance(UserDto user, boolean isNew){
        PersonalProfileFragment fragment = new PersonalProfileFragment();
        fragment.user = user;
        fragment.isNew = isNew;
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        staticFields = new StaticfieldsDto();
        if (savedInstanceState!=null){
            user=(UserDto) savedInstanceState.getSerializable("user");
            isNew=savedInstanceState.getBoolean("isNew");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("user",user);
        outState.putBoolean("isNew",isNew);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.personal_profile, container, false);
        firstName = view.findViewById(R.id.firstName_txt);
        lastName = view.findViewById(R.id.secondName_txt);
        dateOfBirth = view.findViewById(R.id.dateOfBirth);
        confession = view.findViewById(R.id.confession);
        gender = view.findViewById(R.id.gender);
        spinnerConfession = view.findViewById(R.id.spinnerConfession);
        spinnerGender = view.findViewById(R.id.spinnerGender);
        dateFrame = view.findViewById(R.id.date_frame);
        dataPicker = view.findViewById(R.id.date_picker);
        calendarBtn = view.findViewById(R.id.calendar_btn);

        nextBtn = view.findViewById(R.id.next_btn);
        nextBtn.setOnClickListener(this);

        calendarBtn.setOnClickListener(this);

        updateSpinersValues();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.updateStaticFields();
    }

    public void updateStaticFields(StaticfieldsDto staticFields){
        this.staticFields = staticFields;
        updateSpinersValues();
    }

    private void updateSpinersValues(){
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, staticFields.getGender());
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(genderAdapter);

        ArrayAdapter<String> confessionAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, staticFields.getConfession());
        confessionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerConfession.setAdapter(confessionAdapter);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.calendar_btn) {
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR); // current year
            int mMonth = c.get(Calendar.MONTH); // current month
            int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

            datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    dateOfBirth.setText("" + i + "-" + (i1 < 10 ? "0" + i1 : i1)  + "-" + i2);
                }
            }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }else if(view.getId() == R.id.next_btn){
            user.setFirstName(firstName.getText().toString());
            user.setLastName(lastName.getText().toString());
            user.setDateOfBirth(dateOfBirth.getText().toString());
            user.setGender(spinnerGender.getSelectedItem().toString());
            user.setConfession(spinnerConfession.getSelectedItem().toString());
            presenter.startContactInfo(user);
        }
    }

}