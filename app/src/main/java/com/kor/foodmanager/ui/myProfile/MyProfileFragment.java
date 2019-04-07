package com.kor.foodmanager.ui.myProfile;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Binder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
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
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.kor.foodmanager.R;
import com.kor.foodmanager.data.model.StaticfieldsDto;
import com.kor.foodmanager.data.model.UserDto;
import com.kor.foodmanager.ui.IToolbar;
import com.kor.foodmanager.ui.userInfo.UserInfo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ru.terrakok.cicerone.Router;


public class MyProfileFragment extends MvpAppCompatFragment implements IMyProfileFragment, AdapterView.OnItemSelectedListener {

    @InjectPresenter
    MyProfilePresenter presenter;


    private UserDto user;
    private StaticfieldsDto staticFields;
    private Boolean inputModeOn;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.avatar)
    ImageView avatar;
    @BindView(R.id.change_btn)
    TextView changeBtn;
    @BindView(R.id.wrapperForInput)
    ConstraintLayout wrapperForInput;
    @BindView(R.id.firstName)
    EditText firstName;
    @BindView(R.id.secondName)
    EditText secondName;
    @BindView(R.id.confession)
    EditText confession;
    @BindView(R.id.gender)
    EditText gender;
    @BindView(R.id.telephone_number)
    EditText telephoneNumber;
    @BindView(R.id.marital_status)
    EditText maritalStatus;
    @BindView(R.id.food_preferences)
    EditText foodPreferences;
    @BindView(R.id.allergy)
    EditText allergy;
    @BindView(R.id.spinnerConfession)
    Spinner spinnerConfession;
    @BindView(R.id.spinnerGender)
    Spinner spinnerGender;
    @BindView(R.id.spinnerMaritalStatus)
    Spinner spinnerMaritalStatus;
    @BindView(R.id.spinnerFoodPreferences)
    Spinner spinnerFoodPreferences;

    @BindView(R.id.wrapperForView)
    ConstraintLayout wrapperForView;
    @BindView(R.id.firstName_view)
    TextView firstName_view;
    @BindView(R.id.secondName_view)
    TextView secondName_view;
    @BindView(R.id.confession_view)
    TextView confession_view;
    @BindView(R.id.gender_view)
    TextView gender_view;
    @BindView(R.id.telephone_number_view)
    TextView telephoneNumber_view;
    @BindView(R.id.marital_status_view)
    TextView maritalStatus_view;
    @BindView(R.id.food_preferences_view)
    TextView foodPreferences_view;
    @BindView(R.id.allergy_view)
    TextView allergy_view;
    private IToolbar iToolbar;


    Unbinder unbinder;

    public static MyProfileFragment getNewInstance(UserDto user) {
        MyProfileFragment fragment = new MyProfileFragment();
        fragment.user = user;
        return fragment;
    }

public static MyProfileFragment getNewInstance(List<String> picLincs) {
        MyProfileFragment fragment = new MyProfileFragment();
       if (fragment.user!=null){
           fragment.user.setPictureLink(picLincs);
       }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inputModeOn = false;
        staticFields = new StaticfieldsDto();
        if (savedInstanceState != null) {
            user = (UserDto) savedInstanceState.getSerializable("user");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("user", user);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        firstName_view.setText(user.getFirstName());
        secondName_view.setText(user.getLastName());
        confession_view.setText(user.getConfession());
        gender_view.setText(user.getGender());
        telephoneNumber_view.setText(user.getPhoneNumber());
        maritalStatus_view.setText(user.getMaritalStatus());
        foodPreferences_view.setText(UserInfo.inLine(user.getFoodPreferences()));
        allergy_view.setText("Allergy"); //TODO add allergy

        Picasso.get().load(user.getPictureLink().get(0)).error(R.drawable.logo).into(avatar);

        spinnerMaritalStatus.setOnItemSelectedListener(this);
        spinnerGender.setOnItemSelectedListener(this);
        spinnerFoodPreferences.setOnItemSelectedListener(this);
        spinnerConfession.setOnItemSelectedListener(this);

        iToolbar = (IToolbar) getActivity();
        iToolbar.setTitleToolbarEnable("My profile", false, true, false);


        changeBtn.setOnClickListener(v -> {
            if (wrapperForInput.getVisibility() == View.GONE) {
                presenter.updateStaticFields();
                presenter.fillInput();
            } else {
                presenter.fillView();
            }
        });

        updateSpinersValues();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public void updateStaticFields(StaticfieldsDto staticFields) {
        this.staticFields = staticFields;
        updateSpinersValues();
    }

    @Override
    public void viewMode() {
        wrapperForInput.setVisibility(View.GONE);
        wrapperForView.setVisibility(View.VISIBLE);
        inputModeOn = false;
    }

    @Override
    public void inputMode() {
        wrapperForInput.setVisibility(View.VISIBLE);
        wrapperForView.setVisibility(View.GONE);
        inputModeOn = true;
    }

    @Override
    public void fillInput() {
        firstName.setText(firstName_view.getText().toString());
        secondName.setText(secondName_view.getText().toString());
        confession.setText(confession_view.getText().toString());
        gender.setText(gender_view.getText().toString());
        telephoneNumber.setText(telephoneNumber_view.getText().toString());
        maritalStatus.setText(maritalStatus_view.getText().toString());
        foodPreferences.setText(foodPreferences_view.getText().toString());
        allergy.setText(allergy_view.getText().toString());
    }

    @Override
    public void fillView() {
        String str = "";
        List<String> list = new ArrayList<>();
        if (firstName.getText().toString().equals("")) {
            list.add("firstName");
        }
        if (secondName.getText().toString().equals("")) {
            list.add("secondName");
        }
        if (confession.getText().toString().equals("")) {
            list.add("confession");
        }
        if (gender.getText().toString().equals("")) {
            list.add("gender");
        }
        if (telephoneNumber.getText().toString().equals("")) {
            list.add("telephoneNumber");
        }
        if (maritalStatus.getText().toString().equals("")) {
            list.add("maritalStatus");
        }
        if (foodPreferences.getText().toString().equals("")) {
            list.add("foodPreferences");
        }
        if (allergy.getText().toString().equals("")) {
            list.add("allergy");
        }
        str = UserInfo.inLine(list);
        if (str.equals("")) {

            firstName_view.setText(firstName.getText().toString());
            secondName_view.setText(secondName.getText().toString());
            confession_view.setText(confession.getText().toString());
            gender_view.setText(gender.getText().toString());
            telephoneNumber_view.setText(telephoneNumber.getText().toString());
            maritalStatus_view.setText(maritalStatus.getText().toString());
            foodPreferences_view.setText(foodPreferences.getText().toString());
            allergy_view.setText(allergy.getText().toString());
            presenter.viewMode();

            user.setFirstName(firstName_view.getText().toString());
            user.setLastName(secondName_view.getText().toString());
            user.setConfession(confession_view.getText().toString());
            user.setGender(gender_view.getText().toString());
            user.setPhoneNumber(telephoneNumber_view.getText().toString());
            user.setMaritalStatus(maritalStatus_view.getText().toString());
            user.setFoodPreferences(UserInfo.inList(foodPreferences_view.getText().toString()));
            user.setPictureLink(presenter.getPictureLincs());
            presenter.updateUserProfile(user);
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
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }


    private void updateSpinersValues() {
        staticFields.getConfession().add(0, "");
        staticFields.getFoodPreferences().add(0, "");
        staticFields.getMaritalStatus().add(0, "");
        staticFields.getGender().add(0, "");

        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, staticFields.getGender());
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(genderAdapter);

        ArrayAdapter<String> confessionAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, staticFields.getConfession());
        confessionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerConfession.setAdapter(confessionAdapter);

        ArrayAdapter<String> maritalStatusAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, staticFields.getMaritalStatus());
        maritalStatusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMaritalStatus.setAdapter(maritalStatusAdapter);


        ArrayAdapter<String> foodPreferencesAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, staticFields.getFoodPreferences());
        foodPreferencesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFoodPreferences.setAdapter(foodPreferencesAdapter);

        spinnerMaritalStatus.setSelection(0);
        spinnerFoodPreferences.setSelection(0);
        spinnerGender.setSelection(0);
        spinnerConfession.setSelection(0);
    }


    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    @OnClick(R.id.avatar)
    public void editAvatar() {
        if(inputModeOn) {
            presenter.editAvatar();
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
        } else if (parent.getId() == spinnerMaritalStatus.getId()) {
            if (position != 0) {
                maritalStatus.setText(spinnerMaritalStatus.getSelectedItem().toString());
                spinnerMaritalStatus.setSelection(0);
            }
        } else if (parent.getId() == spinnerFoodPreferences.getId()) {
            if (position != 0) {
                if (foodPreferences.getText().toString().equals("") || foodPreferences.getText() == null) {
                    foodPreferences.setText(spinnerFoodPreferences.getSelectedItem().toString());
                } else {
                    if (foodPreferences.getText().toString().contains(spinnerFoodPreferences.getSelectedItem().toString())) {
                        foodPreferences.setText(spinnerFoodPreferences.getSelectedItem().toString());
                    } else {
                        foodPreferences.setText(foodPreferences.getText().toString() + ", " + spinnerFoodPreferences.getSelectedItem().toString());
                    }
                }
                spinnerFoodPreferences.setSelection(0);
            }
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
//        if (parent.getId() == spinnerConfession.getId()) {
//            confession.setText("");
//        } else if (parent.getId() == spinnerGender.getId()) {
//            gender.setText("");
//        } else if (parent.getId() == spinnerMaritalStatus.getId()) {
//            maritalStatus.setText("");
//        } else if (parent.getId() == spinnerFoodPreferences.getId()) {
//            foodPreferences.setText("");
//        }
    }

}