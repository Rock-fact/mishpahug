package com.kor.foodmanager.data.login;

import com.google.gson.Gson;
import com.kor.foodmanager.data.model.ErrorDto;
import com.kor.foodmanager.data.model.StaticfieldsDto;
import com.kor.foodmanager.data.model.UserDto;
import com.kor.foodmanager.data.provider.web.Api;
import java.io.IOException;
import retrofit2.Call;
import retrofit2.Response;
public class LoginRepository implements ILoginRepository {
    private Api api;
    private Gson gson;

    public LoginRepository(Api api, Gson gson) {
        this.api = api;
        this.gson = gson;
    }

    @Override
    public UserDto login(String token) throws LoginException, IOException {
        Call<UserDto> call = api.login(token);
        Response<UserDto> response =call.execute();
        if(response.isSuccessful()){
            UserDto userDto = response.body();
            return userDto;
        }else{
            ErrorDto errorDto = gson.fromJson(response.errorBody().string(),ErrorDto.class);
            throw new LoginException(errorDto.getCode()+ ": "+errorDto.getMessage());
        }
    }

    @Override
    public StaticfieldsDto registration(String token) throws LoginException, IOException {
        Call<StaticfieldsDto> call = api.registration(token);
        Response<StaticfieldsDto> response =call.execute();
        if(response.isSuccessful()){
            StaticfieldsDto staticfieldsDto = response.body();
            return staticfieldsDto;
        }else {
            ErrorDto errorDto = gson.fromJson(response.errorBody().string(),ErrorDto.class);
            throw new LoginException(errorDto.getCode()+ ": "+errorDto.getMessage());
        }
    }
}
