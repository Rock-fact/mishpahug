package com.kor.foodmanager.ui.aboutmyself;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.kor.foodmanager.ui.login.LoginPresenter;
import com.kor.foodmanager.ui.userInfo.UserInfo;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class AboutMyselfFragment extends MvpAppCompatFragment implements IAboutMyselfFragment, AdapterView.OnItemSelectedListener {

    @InjectPresenter
    AboutMyselfPresenter presenter;


    private UserDto user;
    private StaticfieldsDto staticFields;
    private Boolean isFacebook= AccessToken.getCurrentAccessToken()!=null;

    @BindView(R.id.editPictures)
    TextView editPicture;
    @BindView(R.id.avatar)
    ImageView avatar;
    @BindView(R.id.marital)
    EditText marital;
    @BindView(R.id.food)
    EditText food;
    @BindView(R.id.languages)
    EditText languages;
    @BindView(R.id.wordsAbout)
    EditText description;
    @BindView(R.id.spinnerMarital)
    Spinner spinnerMarital;
    @BindView(R.id.spinnerFood)
    Spinner spinnerFood;
    @BindView(R.id.spinnerLanguages)
    Spinner spinnerLanguages;
    @BindView(R.id.save_btn)
    Button saveBtn;
    @BindView(R.id.progressFrame)
    FrameLayout progressFrame;
    Unbinder unbinder;

    private IToolbar iToolbar;
    String email;
    String password;

    public static AboutMyselfFragment getNewInstance(UserDtoWithEmail user) {
        AboutMyselfFragment fragment = new AboutMyselfFragment();
        fragment.user = user.getUser();
        fragment.email = user.getEmail();
        fragment.password = user.getPassword();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        staticFields = new StaticfieldsDto();
        if (savedInstanceState != null) {
            user = (UserDto) savedInstanceState.getSerializable("user");
            email = savedInstanceState.getString("email");
            password = savedInstanceState.getString("password");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("user", user);
        outState.putString("email", email);
        outState.putString("password", password);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.about_myself, container, false);
        unbinder = ButterKnife.bind(this, view);

        if (isFacebook && user.getDescription()!=null)
            description.setText(user.getDescription());

        spinnerFood.setOnItemSelectedListener(this);
        spinnerMarital.setOnItemSelectedListener(this);
        spinnerLanguages.setOnItemSelectedListener(this);

        if(user.getPictureLink().size()>0){
            Picasso.get().invalidate(user.getPictureLink().get(0));
            Picasso.get().load(user.getPictureLink().get(0))
                    .transform(new CropCircleTransformation()).memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE).error(R.drawable.logo).into(avatar);
        }

        iToolbar = (IToolbar) getActivity();
        iToolbar.setTitleToolbarEnable("About myself", false, true, false);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.updateStaticFields();
    }

    @Override
    public void updateStaticFields(StaticfieldsDto staticFields) {
        this.staticFields = staticFields;
        updateSpinersValues();
    }


    private void updateSpinersValues() {
        staticFields.getFoodPreferences().add(0, "");
        staticFields.getMaritalStatus().add(0, "");
        staticFields.getLanguages().add(0,"");

        ArrayAdapter<String> maritalAdapter = new ArrayAdapter<>(getContext(), R.layout.my_spinner_dropdown_item, staticFields.getMaritalStatus());
        maritalAdapter.setDropDownViewResource(R.layout.my_spinner_dropdown_item);
        spinnerMarital.setAdapter(maritalAdapter);

        ArrayAdapter<String> foodPreferenceAdapter = new ArrayAdapter<>(getContext(), R.layout.my_spinner_dropdown_item, staticFields.getFoodPreferences());
        foodPreferenceAdapter.setDropDownViewResource(R.layout.my_spinner_dropdown_item);
        spinnerFood.setAdapter(foodPreferenceAdapter);

        ArrayAdapter<String> languagesPreferenceAdapter = new ArrayAdapter<>(getContext(), R.layout.my_spinner_dropdown_item, staticFields.getLanguages());
        languagesPreferenceAdapter.setDropDownViewResource(R.layout.my_spinner_dropdown_item);
        spinnerLanguages.setAdapter(languagesPreferenceAdapter);

        spinnerMarital.setSelection(0);
        spinnerFood.setSelection(0);
        spinnerLanguages.setSelection(0);
    }

    @OnClick(R.id.editPictures)
    public void onClickEditPicture() {
        presenter.editPic();
        //Toast.makeText(getActivity(), "Go to edit picture", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.save_btn)
    public void onClickSaveBtn() {
        String str = "";
        List<String> list = new ArrayList<>();
        if (marital.getText().toString().equals("")) {
            list.add("Marital status");
        }
        if (food.getText().toString().equals("")) {
            list.add("Food preferences");
        }
        if (languages.getText().toString().equals("")) {
            list.add("Languages");
        }
        if (description.getText().toString().equals("")) {
            list.add("Few words about myself");
        }

        str = UserInfo.inLine(list);
        if (str.equals("")) {
            user.setFoodPreferences(UserInfo.inList(food.getText().toString()));
            user.setMaritalStatus(marital.getText().toString());
            user.setDescription(description.getText().toString());
            user.setLanguages(UserInfo.inList(languages.getText().toString()));


            List<String> picture =new ArrayList<>();
            picture.add("https://i.imgur.com/vaZKZcz.jpg");
            user.setPictureLink(picture);
            //TODO picture link
            presenter.registrationAndUpdateUserProfile(email, password,user,isFacebook);

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
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == spinnerMarital.getId()) {
            if (position != 0) {
                marital.setText(spinnerMarital.getSelectedItem().toString());
                spinnerMarital.setSelection(0);
            }
        } else if (parent.getId() == spinnerFood.getId()) {
            if (position != 0) {
                if (food.getText().toString().equals("") || food.getText() == null) {
                    food.setText(spinnerFood.getSelectedItem().toString());
                } else {
                    if (food.getText().toString().contains(spinnerFood.getSelectedItem().toString())) {
                        food.setText(spinnerFood.getSelectedItem().toString());
                    } else {
                        food.setText(food.getText().toString() + ", " + spinnerFood.getSelectedItem().toString());
                    }
                }
                spinnerFood.setSelection(0);
            }
        } else if (parent.getId() == spinnerLanguages.getId()) {
            if (position != 0) {
                if (languages.getText().toString().equals("") || languages.getText() == null) {
                    languages.setText(spinnerLanguages.getSelectedItem().toString());
                    Log.d("Registration", "onItemSelected: "+languages.getText());
                } else {
                    if (languages.getText().toString().contains(spinnerLanguages.getSelectedItem().toString())) {
                        languages.setText(spinnerLanguages.getSelectedItem().toString());
                    } else {
                        languages.setText(languages.getText().toString() + ", " + spinnerLanguages.getSelectedItem().toString());
                    }
                }
                spinnerLanguages.setSelection(0);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    @Override
    public void showError(String error) {
        new android.support.v7.app.AlertDialog.Builder(getActivity())
                .setMessage(error)
                .setTitle("Error!")
                .setPositiveButton("Ok", null)
                .setCancelable(false)
                .create()
                .show();
    }

    @Override
    public void showProgressFrame() {
        progressFrame.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressFrame() {
        progressFrame.setVisibility(View.GONE);
    }
}

