package com.kor.foodmanager.buissness.login;

import android.util.Log;

import com.kor.foodmanager.buissness.login.validator.EmailValidException;
import com.kor.foodmanager.buissness.login.validator.IValidator;
import com.kor.foodmanager.buissness.login.validator.PasswordValidException;
import com.kor.foodmanager.data.auth.IAuthRepository;
import com.kor.foodmanager.data.login.ILoginRepository;
import com.kor.foodmanager.data.login.LoginException;
import com.kor.foodmanager.data.model.StaticfieldsDto;
import com.kor.foodmanager.data.model.UserDto;

import java.io.IOException;

import okhttp3.Credentials;

import static com.kor.foodmanager.ui.MainActivity.TAG;

public class LoginInteractor implements ILoginInteractor{
    private ILoginRepository loginRepository;
    private IValidator validator;
    private IAuthRepository authRepository;

    public LoginInteractor(ILoginRepository loginRepository, IAuthRepository authRepository, IValidator validator) {
        this.loginRepository = loginRepository;
        this.authRepository = authRepository;
        this.validator = validator;
    }


    @Override
    public void login(String email, String password) throws LoginException, IOException {
        String token = Credentials.basic(email, password);
        Log.d(TAG, "login: "+token);
        UserDto userDto = loginRepository.login(token);
        authRepository.saveToken(token);
    }

    @Override
    public void registration(String email, String password) throws LoginException, IOException {
        String token = Credentials.basic(email, password);
        Log.d(TAG, "login: "+token);
        StaticfieldsDto staticfieldsDto = loginRepository.registration(token);
        authRepository.saveToken(token);
    }

    @Override
    public void validate(String email, String password) throws EmailValidException, PasswordValidException {
        validator.checkEmail(email);
        validator.checkPassword(password);
    }
}
