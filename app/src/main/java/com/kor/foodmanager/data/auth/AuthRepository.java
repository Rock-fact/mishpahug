package com.kor.foodmanager.data.auth;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.kor.foodmanager.data.model.UserDto;

public class AuthRepository implements IAuthRepository{
    private static final String SP_AUTH = "AUTH";
    private static final String AUTH_CURRENT = "CURR";
    private static final String USER_INFO = "USER_INFO";
    private Context context;
    private Gson gson;

    public AuthRepository(Context context, Gson gson){
        this.context = context;
        this.gson = gson;
    }

    @Override
    public void saveToken(String token) {
        context.getSharedPreferences(SP_AUTH, context.MODE_PRIVATE)
                .edit()
                .putString(AUTH_CURRENT, token)
                .apply();
    }

    @Override
    public String getToken() {
        return context.getSharedPreferences(SP_AUTH, context.MODE_PRIVATE)
                .getString(AUTH_CURRENT, null);
    }

    @Override
    public void clearToken() {
        context.getSharedPreferences(SP_AUTH, context.MODE_PRIVATE)
                .edit()
                .remove(AUTH_CURRENT)
                .apply();
    }

    @Override
    public void saveUser(UserDto userDto) {
        String data = gson.toJson(userDto);
        context.getSharedPreferences(SP_AUTH, context.MODE_PRIVATE)
                .edit()
                .putString(USER_INFO, data)
                .apply();
    }

    @Override
    public UserDto getUser() {
        String data = context.getSharedPreferences(SP_AUTH, context.MODE_PRIVATE)
                .getString(USER_INFO, null);
        return gson.fromJson(data,UserDto.class);
    }
}
