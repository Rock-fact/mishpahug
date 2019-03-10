package com.kor.foodmanager.data.eventInfo;

import com.google.gson.Gson;
import com.kor.foodmanager.data.event.ServerException;
import com.kor.foodmanager.data.model.ErrorDto;
import com.kor.foodmanager.data.model.MessageDto;
import com.kor.foodmanager.data.provider.web.Api;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GuestEventInfoRepository implements IGuestEventInfoRepository{
    private Api api;
    private Gson gson;

    public GuestEventInfoRepository(Api api, Gson gson) {
        this.api = api;
        this.gson = gson;
    }

    @Override
    public String joinEvent(String token, long eventId) throws IOException, ServerException {
        Call<MessageDto> call = api.subscribeToEvent(token, eventId);
        Response<MessageDto> response = call.execute();
        if(response.isSuccessful()){
           return response.body().getMessage();
        } else {
            if(response.code()==409){
                return "You are the owner of the event or already subscribed to it!";
            } else if (response.code()==401){
                return "Authorization needed";
            } else {
            ErrorDto errorDto = gson.fromJson(response.errorBody().string(), ErrorDto.class);
            throw new ServerException(errorDto.getCode()+ ": "+errorDto.getMessage());
            }
        }
    }

    @Override
    public String unsubscribeFromEvent(String token, long eventId) throws IOException, ServerException {
        Call<MessageDto> call = api.unsubscribeFromEvent(token, eventId);
        Response<MessageDto> response = call.execute();
        if (response.isSuccessful()){
            return response.body().getMessage();
        } else {
            if (response.code()==409){
                return "It's not possible to unsubscribe";
            } else if (response.code()==401){
                return "Authorization needed";
            } else {
                ErrorDto errorDto = gson.fromJson(response.errorBody().string(), ErrorDto.class);
                throw new ServerException(errorDto.getCode()+ ": "+errorDto.getMessage());
            }
        }
    }


}
