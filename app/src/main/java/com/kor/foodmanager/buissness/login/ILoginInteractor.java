package com.kor.foodmanager.buissness.login;

import com.kor.foodmanager.buissness.login.validator.EmailValidException;
import com.kor.foodmanager.buissness.login.validator.PasswordValidException;

import java.io.IOException;

import io.reactivex.Completable;

public interface ILoginInteractor {
    void login(String email, String password) throws LoginException, IOException;
    void registration(String email, String password) throws LoginException, IOException;
    void validate(String email, String password) throws EmailValidException, PasswordValidException;
}
