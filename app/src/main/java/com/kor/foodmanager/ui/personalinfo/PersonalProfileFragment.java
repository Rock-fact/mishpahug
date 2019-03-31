package com.kor.foodmanager.ui.personalinfo;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.kor.foodmanager.R;
import com.kor.foodmanager.data.model.StaticfieldsDto;
import com.kor.foodmanager.data.model.UserDto;
import com.kor.foodmanager.ui.IToolbar;
import com.kor.foodmanager.ui.userInfo.UserInfo;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class PersonalProfileFragment extends MvpAppCompatFragment implements IPersonalProfileFragment, AdapterView.OnItemSelectedListener {

    @InjectPresenter
    PersonalProfilePresenter presenter;

    private UserDto user;
    private StaticfieldsDto staticFields;
    private boolean isNew=true;
    private DatePickerDialog datePickerDialog;
    private IToolbar iToolbar;


    @BindView(R.id.firstName_txt)
    EditText firstName;
    @BindView(R.id.secondName_txt)
    EditText lastName;
    @BindView(R.id.dateOfBirth)
    TextView dateOfBirth;
    @BindView(R.id.confession)
    TextView confession;
    @BindView(R.id.gender)
    TextView gender;
    @BindView(R.id.spinnerConfession)
    Spinner spinnerConfession;
    @BindView(R.id.spinnerGender)
    Spinner spinnerGender;
    @BindView(R.id.date_frame)
    FrameLayout dateFrame;
    @BindView(R.id.date_picker)
    DatePicker dataPicker;
    @BindView(R.id.calendar_btn)
    ImageView calendarBtn;
    @BindView(R.id.next_btn)
    Button nextBtn;
    @BindView(R.id.avatar)
    ImageView avatar;
    @BindView(R.id.change_btn)
    TextView changePicture;

    Unbinder unbinder;

    public PersonalProfileFragment() {
        user=new UserDto();
    }

    public static PersonalProfileFragment getNewInstance(UserDto user, boolean isNew) {
        PersonalProfileFragment fragment = new PersonalProfileFragment();
        fragment.user = user;
        fragment.isNew = isNew;
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        staticFields = new StaticfieldsDto();
        if (savedInstanceState != null) {
            user = (UserDto) savedInstanceState.getSerializable("user");
            isNew = savedInstanceState.getBoolean("isNew");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("user", user);
        outState.putBoolean("isNew", isNew);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.personal_profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        updateSpinersValues();

        Log.d("Spinner", "onCreateView: "+staticFields.getGender().toString());

        spinnerConfession.setOnItemSelectedListener(this);
        spinnerGender.setOnItemSelectedListener(this);

        iToolbar=(IToolbar) getActivity();
        iToolbar.setTitleToolbarEnable("Personal Info",false,true,false);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.updateStaticFields();
    }

    public void updateStaticFields(StaticfieldsDto staticFields) {
        this.staticFields = staticFields;
        updateSpinersValues();
    }

    private void updateSpinersValues() {
        staticFields.getConfession().add(0, "");
        staticFields.getGender().add(0, "");

        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, staticFields.getGender());
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(genderAdapter);

        ArrayAdapter<String> confessionAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, staticFields.getConfession());
        confessionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerConfession.setAdapter(confessionAdapter);

        spinnerGender.setSelection(0);
        spinnerConfession.setSelection(0);
    }

    @OnClick(R.id.change_btn)
    public void onClickChangePicture() {
        Toast.makeText(getActivity(), "GoToEditPicture", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.calendar_btn)
    public void onClickCalendarBtn() {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // current year
        int mMonth = c.get(Calendar.MONTH); // current month
        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

        datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                dateOfBirth.setText("" + i + "-" + (i1 < 10 ? "0" + i1 : i1) + "-" + i2);
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    @OnClick(R.id.next_btn)
    public void onClickNextBtn() {
        String str = "";
        List<String> list = new ArrayList<>();

//        if (user.getPictureLink() == null || user.getPictureLink().isEmpty()) {
//            list.add("Edit Picture");
//        }
        //TODO edit Picture
        if (firstName.getText().toString().equals("")) {
            list.add("First Name");
        }
        if (lastName.getText().toString().equals("")) {
            list.add("Last Name");
        }
        if (dateOfBirth.getText().toString().equals("")) {
            list.add("Date of birth");
        }
        if (spinnerConfession.getSelectedItem().toString().equals("")) {
            list.add("Confession");
        }
        if (spinnerGender.getSelectedItem().toString().equals("")) {
            list.add("Gender");
        }


        str = UserInfo.inLine(list);

        if (str.equals("")) {
            user.setFirstName(firstName.getText().toString());
            user.setLastName(lastName.getText().toString());
            user.setDateOfBirth(dateOfBirth.getText().toString());
            user.setGender(spinnerGender.getSelectedItem().toString());
            user.setConfession(spinnerConfession.getSelectedItem().toString());
            presenter.startContactInfo(user);
        } else {
            new AlertDialog.Builder(getActivity())
                    .setTitle("Fill the further fields")
                    .setMessage(str)
                    .setPositiveButton("Ok", null)
                    .create()
                    .show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == spinnerConfession.getId()) {
            if (position != 0) {
                confession.setText(spinnerConfession.getSelectedItem().toString());
                spinnerConfession.setSelection(0);
            }
        } else if (parent.getId() == spinnerGender.getId()) {
            if (position != 0) {
                gender.setText(spinnerGender.getSelectedItem().toString());
                spinnerGender.setSelection(0);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }
}