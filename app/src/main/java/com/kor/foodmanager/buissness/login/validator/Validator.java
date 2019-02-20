package com.kor.foodmanager.buissness.login.validator;

public class Validator implements IValidator{

    public Validator() {
    }

    public void checkEmail(String email) throws EmailValidException {
        if(email.isEmpty()){
            throw new EmailValidException("Email can't be empty!");
        }

        int at = email.indexOf("@");
        if(at < 0) {
            throw new EmailValidException("Wrong email format! Example: name@mail.com");
        }

        if(email.lastIndexOf("@") != at) {
            throw new EmailValidException("Wrong email format! Example: name@mail.com");
        }

        int dot = email.lastIndexOf(".");
        if(dot < 0 || dot < at) {
            throw new EmailValidException("Wrong email format! Example: name@mail.com");
        }

        if(email.length() - 1 - dot <= 1) {
            throw new EmailValidException("Wrong email format! Example: name@mail.com");
        }
    }

    public void checkPassword(String password) throws PasswordValidException {
        if(password.length() < 8) {
            throw new PasswordValidException("Password length need be 8 or more symbols");
        }
        boolean[] tests = new boolean[4];
        char[] arr = password.toCharArray();
        for (char anArr : arr) {
            if (Character.isUpperCase(anArr)) {
                tests[0] = true;
            }

            if (Character.isLowerCase(anArr)) {
                tests[1] = true;
            }

            if (Character.isDigit(anArr)) {
                tests[2] = true;
            }

            if (isSpecSymbol(anArr)) {
                tests[3] = true;
            }
        }

        // TODO: 16.02.2019 Password validation was removed for testing purpose
//        if(!tests[0]){
//            throw new PasswordValidException("Password must contain at least one uppercase letter!");
//        }
//        if(!tests[1]){
//            throw new PasswordValidException("Password must contain at least one lowercase letter!");
//        }
        if(!tests[2]){
            throw new PasswordValidException("Password must contain at least one digit!");
        }
//        if(!tests[3]){
//            throw new PasswordValidException("Password must contain at least one special symbol from ['$','~','-','_']!");
//        }
    }

    private boolean isSpecSymbol(char c) {
        char[] arr = {'$','~','-','_'};
        for (char anArr : arr) {
            if (anArr == c) {
                return true;
            }
        }
        return false;
    }
}
