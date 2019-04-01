package com.kor.foodmanager.data.userData;


import android.util.Log;

import com.kor.foodmanager.data.auth.IAuthRepository;
import com.kor.foodmanager.data.model.StaticfieldsDto;
import com.kor.foodmanager.data.model.UserDto;
import com.kor.foodmanager.data.provider.web.Api;

import retrofit2.Call;
import retrofit2.Response;

public class UserDataRepository implements IUserDataRepository {

    private Api api;
    private IAuthRepository authRepository;

    public UserDataRepository(Api api, IAuthRepository authRepository) {
        this.api = api;
        this.authRepository = authRepository;
    }

    @Override
    public StaticfieldsDto staticFields() throws Exception {
        Call<StaticfieldsDto> call = api.getStaticFields();
        Response<StaticfieldsDto> response = call.execute();
        if (response.isSuccessful()){
            return response.body();
        }else {
            String error = response.errorBody().string();
            throw new Exception(error);
        }

    }

    @Override
    public UserDto updateUserProfile(UserDto user) throws Exception {
       String token = authRepository.getToken();
       Call<UserDto> call = api.updateUserProfile(token,user);
       Response<UserDto> response = call.execute();
        Log.d("Registration", "updateUserProfile: "+response.isSuccessful());
       if (response.isSuccessful()){
           authRepository.saveUser(user);
           return response.body();
       }else{
           String error = response.errorBody().string();
           throw new Exception(error);
       }
    }
}
