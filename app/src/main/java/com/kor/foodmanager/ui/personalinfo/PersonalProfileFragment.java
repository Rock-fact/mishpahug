package com.kor.foodmanager.ui.personalinfo;


import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
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
import com.facebook.AccessToken;
import com.kor.foodmanager.R;
import com.kor.foodmanager.data.model.StaticfieldsDto;
import com.kor.foodmanager.data.model.UserDto;
import com.kor.foodmanager.ui.CropCircleTransformation;
import com.kor.foodmanager.ui.IToolbar;
import com.kor.foodmanager.ui.contactinfo.UserDtoWithEmail;
import com.kor.foodmanager.ui.userInfo.UserInfo;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;


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

    private UserDtoWithEmail userDtoWithEmail;
    private UserDto user;
    private Boolean isFacebook = AccessToken.getCurrentAccessToken()!=null;
    private StaticfieldsDto staticFields;
    private DatePickerDialog datePickerDialog;
    private IToolbar iToolbar;


    @BindView(R.id.firstName_txt)
    EditText firstName;
    @BindView(R.id.secondName_txt)
    EditText lastName;
    @BindView(R.id.dateOfBirth)
    EditText dateOfBirth;
    @BindView(R.id.confession)
    EditText confession;
    @BindView(R.id.gender)
    EditText gender;
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
        userDtoWithEmail = new UserDtoWithEmail();
        user=userDtoWithEmail.getUser();
    }

    public static PersonalProfileFragment getNewInstance(UserDtoWithEmail user, Boolean isFacebook) {
        PersonalProfileFragment fragment = new PersonalProfileFragment();
        fragment.userDtoWithEmail = user;
        fragment.user = user.getUser();
        fragment.isFacebook = true;
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        staticFields = new StaticfieldsDto();
        presenter.clearNonLoadedList();
        if (savedInstanceState != null) {
            userDtoWithEmail = (UserDtoWithEmail) savedInstanceState.getSerializable("user");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("user", userDtoWithEmail);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.personal_profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        updateSpinersValues();

        if (isFacebook) fillFields();

        spinnerConfession.setOnItemSelectedListener(this);
        spinnerGender.setOnItemSelectedListener(this);

        iToolbar = (IToolbar) getActivity();
        iToolbar.setTitleToolbarEnable("Personal Info", false, true, false);

        presenter.setPics();
        return view;
    }

    public void fillFields() {
        firstName.setText(user.getFirstName());
        lastName.setText(user.getLastName());
        dateOfBirth.setText(user.getDateOfBirth());
        confession.setText(user.getConfession());
        gender.setText(user.getGender());
        if(user.getPictureLink().size()>0) {
            Picasso.get().invalidate(user.getPictureLink().get(0));
            Picasso.get().load(user.getPictureLink().get(0))
                    .transform(new CropCircleTransformation())
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE).error(R.drawable.logo).into(avatar);
        } else {
            Picasso.get().load(R.drawable.logo).memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE).error(R.drawable.logo).into(avatar);
        }
        //TODO picture link
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

    @Override
    public void setUserPics(List<String> notLoadedUriList) {
        user.setPictureLink(notLoadedUriList);
        if(notLoadedUriList.size()>0) {
            Picasso.get().invalidate(user.getPictureLink().get(0));
            Picasso.get().load(user.getPictureLink().get(0))
                    .transform(new CropCircleTransformation()).memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE).error(R.drawable.logo).into(avatar);
        }
    }

    private void updateSpinersValues() {
        staticFields.getConfession().add(0, "");
        staticFields.getGender().add(0, "");

        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(getContext(), R.layout.my_spinner_dropdown_item, staticFields.getGender());
        genderAdapter.setDropDownViewResource(R.layout.my_spinner_dropdown_item);
        spinnerGender.setAdapter(genderAdapter);

        ArrayAdapter<String> confessionAdapter = new ArrayAdapter<>(getContext(), R.layout.my_spinner_dropdown_item, staticFields.getConfession());
        confessionAdapter.setDropDownViewResource(R.layout.my_spinner_dropdown_item);
        spinnerConfession.setAdapter(confessionAdapter);

        spinnerGender.setSelection(0);
        spinnerConfession.setSelection(0);
    }

    @OnClick(R.id.change_btn)
    public void onClickChangePicture() {
        presenter.editPicture(user);
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
        if (confession.getText().toString().equals("")) {
            list.add("Confession");
        }
        if (gender.getText().toString().equals("")) {
            list.add("Gender");
        }


        str = UserInfo.inLine(list);

        if (str.equals("")) {
            user.setFirstName(firstName.getText().toString());
            user.setLastName(lastName.getText().toString());
            user.setDateOfBirth(dateOfBirth.getText().toString());
            user.setGender(gender.getText().toString());
            user.setConfession(confession.getText().toString());
            Log.d("PICS", "onClickNextBtn: "+user);
            presenter.startContactInfo(userDtoWithEmail);
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