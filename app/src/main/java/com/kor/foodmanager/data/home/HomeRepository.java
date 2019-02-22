package com.kor.foodmanager.data.home;

import com.google.gson.Gson;
import com.kor.foodmanager.data.model.ErrorDto;
import com.kor.foodmanager.data.model.EventDto;
import com.kor.foodmanager.data.provider.web.Api;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class HomeRepository implements IHomeRepository {
    private Api api;
    private Gson gson;

    public HomeRepository(Api api, Gson gson) {
        this.api = api;
        this.gson = gson;
    }


    @Override
    public ErrorDto addNewEvent(String token, EventDto event) throws IOException, ServerException {
        Call<ErrorDto> call = api.createNewEvent(token, event);
        Response<ErrorDto> response = call.execute();
        if(response.isSuccessful()){
            ErrorDto msg = response.body();
            return msg;
        }else {
            ErrorDto errorDto = gson.fromJson(response.errorBody().string(),ErrorDto.class);
            throw new ServerException(errorDto.getCode()+ ": "+errorDto.getMessage());
        }
    }
}
