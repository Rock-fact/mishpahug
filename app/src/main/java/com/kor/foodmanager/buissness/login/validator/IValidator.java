package com.kor.foodmanager.buissness.login.validator;

public interface IValidator {
    void checkEmail(String email) throws EmailValidException;
    void checkPassword(String password) throws PasswordValidException;
}
