package com.kor.foodmanager.buissness.login;

import android.os.Build;
import android.support.annotation.RequiresApi;
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
import java.util.Base64;

import okhttp3.Credentials;
import okio.ByteString;

import static com.kor.foodmanager.ui.MainActivity.TAG;
import static okhttp3.internal.Util.ISO_8859_1;

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
        authRepository.saveUser(userDto);
    }
    @Override
    public void login(String token) throws LoginException, IOException {
        String encoded=encodedToken(token);
        UserDto userDto = loginRepository.login(encoded);
        authRepository.saveToken(encoded);
        authRepository.saveUser(userDto);
    }

    @Override
    public void registration(String email, String password) throws LoginException, IOException {
        String token = Credentials.basic(email, password);
        Log.d(TAG, "login: "+token);
        StaticfieldsDto staticfieldsDto = loginRepository.registration(token);
        authRepository.saveToken(token);
    }
    public String encodedToken(String token){
      String email=token.substring(1,15).toLowerCase()+"@mail.ru";
      String password="qweqwe";
      String encoded=email+":"+password;
      return "Basic " +ByteString.encodeString(encoded, ISO_8859_1).base64();
    }
    @Override
    public void registration(String token) throws LoginException, IOException {
        String encoded=encodedToken(token);
        StaticfieldsDto staticfieldsDto = loginRepository.registration(encoded);
        authRepository.saveToken(encoded);
    }


    @Override
    public void validate(String email, String password) throws EmailValidException, PasswordValidException {
        validator.checkEmail(email);
        validator.checkPassword(password);
    }

    @Override
    public void validate(String email) throws EmailValidException {
        validator.checkEmail(email);
    }
}
