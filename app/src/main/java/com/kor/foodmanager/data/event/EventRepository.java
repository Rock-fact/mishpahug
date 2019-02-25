package com.kor.foodmanager.data.event;

import com.google.gson.Gson;
import com.kor.foodmanager.data.model.ErrorDto;
import com.kor.foodmanager.data.model.EventDto;
import com.kor.foodmanager.data.model.MessageDto;
import com.kor.foodmanager.data.provider.web.Api;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class EventRepository implements IEventRepository {
    private Api api;
    private Gson gson;

    public EventRepository(Api api, Gson gson) {
        this.api = api;
        this.gson = gson;
    }


    @Override
    public MessageDto addNewEvent(String token, EventDto event) throws IOException, ServerException {
        Call<MessageDto> call = api.createNewEvent(token, event);
        Response<MessageDto> response = call.execute();
        if(response.isSuccessful()){
            MessageDto msg = response.body();
            return msg;
        }else {
            ErrorDto errorDto = gson.fromJson(response.errorBody().string(),ErrorDto.class);
            throw new ServerException(errorDto.getCode()+ ": "+errorDto.getMessage());
        }
    }
}
