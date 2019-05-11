package com.kor.foodmanager.buissness.login;

import com.kor.foodmanager.buissness.login.validator.EmailValidException;
import com.kor.foodmanager.buissness.login.validator.PasswordValidException;
import com.kor.foodmanager.data.login.LoginException;

import java.io.IOException;

public interface ILoginInteractor {
    void login(String email, String password) throws LoginException, IOException;
    void login(String token) throws LoginException, IOException;
    void registration(String email, String password) throws LoginException, IOException;
    void registration(String token) throws LoginException, IOException;
    void validate(String email, String password) throws EmailValidException, PasswordValidException;
    void validate(String email) throws EmailValidException, PasswordValidException;
}
