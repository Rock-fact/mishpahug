package com.kor.foodmanager.ui.contactinfo;

import com.kor.foodmanager.data.model.UserDto;

public class UserDtoWithEmail {
    String email;
    String password;
    UserDto user;

    public UserDtoWithEmail(String email, String password, UserDto user) {
        this.email = email;
        this.password = password;
        this.user = user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }
}
