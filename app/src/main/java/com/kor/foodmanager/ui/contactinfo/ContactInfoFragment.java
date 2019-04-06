package com.kor.foodmanager.ui.contactinfo;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.facebook.AccessToken;
import com.kor.foodmanager.R;
import com.kor.foodmanager.buissness.login.ILoginInteractor;
import com.kor.foodmanager.buissness.login.validator.EmailValidException;
import com.kor.foodmanager.buissness.login.validator.PasswordValidException;
import com.kor.foodmanager.data.model.StaticfieldsDto;
import com.kor.foodmanager.data.model.UserDto;
import com.kor.foodmanager.ui.IToolbar;
import com.kor.foodmanager.ui.login.LoginPresenter;
import com.kor.foodmanager.ui.userInfo.UserInfo;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class ContactInfoFragment extends MvpAppCompatFragment implements IContactInfoFragment {

    @InjectPresenter
    ContactInfoPresenter presenter;

    private UserDtoWithEmail userDtoWithEmail;
    @BindView(R.id.editPictures)
    TextView editPicture;
    @BindView(R.id.email)
    TextInputEditText emailInput;
    @BindView(R.id.phoneNumber)
    EditText phoneInput;
    @BindView(R.id.password)
    TextInputEditText password;
    @BindView(R.id.next_btn)
    Button nextBtn;
    Unbinder unbinder;
    Boolean isFacebook=false;

    private IToolbar iToolbar;

    public static ContactInfoFragment getNewInstance(UserDtoWithEmail user) {
        ContactInfoFragment fragment = new ContactInfoFragment();
        fragment.userDtoWithEmail = user;
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        View view = inflater.inflate(R.layout.contact_info, container, false);
        unbinder = ButterKnife.bind(this, view);

        if (AccessToken.getCurrentAccessToken().getToken()!=null) isFacebook=true;
        if (isFacebook&& userDtoWithEmail.getEmail()!=null&& !userDtoWithEmail.getEmail().isEmpty())
        emailInput.setText(userDtoWithEmail.getEmail());

        iToolbar = (IToolbar) getActivity();
        iToolbar.setTitleToolbarEnable("Contact Info", false, true, false);

        return view;
    }


    @OnClick(R.id.editPictures)
    public void onClickEditPicture() {
        Toast.makeText(getActivity(), "Go to edit picture", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.next_btn)
    public void onClickNextBtn() {
        String str = "";
        List<String> list = new ArrayList<>();
        if (emailInput.getText().toString().equals("")) {
            list.add("email");
        }
        if (password.getText().toString().equals("") && !isFacebook) {
            list.add("password");
        }
        if (phoneInput.getText().toString().equals("")) {
            list.add("telephone number");
        }

        str = UserInfo.inLine(list);

        if (str.equals("")) {
            try {
                presenter.validate(emailInput.getText().toString(),password.getText().toString());
                userDtoWithEmail.getUser().setPhoneNumber(phoneInput.getText().toString());
                userDtoWithEmail.setEmail(emailInput.getText().toString());
                userDtoWithEmail.setPassword(password.getText().toString());
                presenter.startAboutMyself(userDtoWithEmail);
            } catch (EmailValidException e) {
                showEmailError(e.getMessage());
            } catch (PasswordValidException e) {
                showPasswordError(e.getMessage());
            }
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
    public void showEmailError(String error) {
        emailInput.setError(error);
    }

    @Override
    public void showPasswordError(String error) {
        password.setError(error);
    }
}