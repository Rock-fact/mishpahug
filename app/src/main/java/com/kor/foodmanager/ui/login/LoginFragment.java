package com.kor.foodmanager.ui.login;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.kor.foodmanager.App;
import com.kor.foodmanager.R;
import com.kor.foodmanager.ui.IToolbar;
import com.kor.foodmanager.ui.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.support.v4.content.ContextCompat.getSystemService;

public class LoginFragment extends MvpAppCompatFragment implements ILogin {
    @InjectPresenter LoginPresenter presenter;
    @BindView(R.id.inputEmail) TextInputEditText inputEmail;
    @BindView(R.id.inputPassword) TextInputEditText inputPassword;
    @BindView(R.id.loginBtn) Button loginBtn;
    @BindView(R.id.regBtn) Button regBtn;
    @BindView(R.id.progressFrame) FrameLayout progressFrame;
    private Unbinder unbinder;
    private IToolbar iToolbar;

    public LoginFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        unbinder = ButterKnife.bind(this,view);
        progressFrame.setOnClickListener(null);

        iToolbar = (IToolbar) getActivity();
        iToolbar.setTitleToolbarEnable("Mishpahug",false,false,false);

        return view;
    }

    @Override
    public void showError(String error) {
        new AlertDialog.Builder(getActivity())
                .setMessage(error)
                .setTitle("Error!")
                .setPositiveButton("Ok", null)
                .setCancelable(false)
                .create()
                .show();
    }

    @OnClick(R.id.loginBtn)
    public void loginBtnClicked(){
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        hideKeyboard();
        presenter.login(email,password);
    }

    public void hideKeyboard(){
        InputMethodManager imm = (InputMethodManager) getActivity().
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(loginBtn.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @OnClick(R.id.regBtn)
    public void regBtnClicked(){
        hideKeyboard();
        presenter.registration();
    }

//    @OnClick(R.id.regBtn)
//    public void regBtnClicked(){
//        String email = inputEmail.getText().toString();
//        String password = inputPassword.getText().toString();
//        presenter.registration(email,password);
//    }

    @Override
    public void showEmailError(String error) {
        inputEmail.setError(error);
    }

    @Override
    public void showPasswordError(String error) {
        inputPassword.setError(error);
    }

    @Override
    public void showProgressFrame() {
        progressFrame.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressFrame() {
        progressFrame.setVisibility(View.GONE);
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }
}
