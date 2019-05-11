package com.kor.foodmanager.data.MyEventInProgressRepository;

import com.google.gson.Gson;
import com.kor.foodmanager.data.event.ServerException;
import com.kor.foodmanager.data.model.ErrorDto;
import com.kor.foodmanager.data.model.EventDto;
import com.kor.foodmanager.data.model.InvitationStatusDto;
import com.kor.foodmanager.data.provider.web.Api;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class MyEventInProgressRepository implements IMyEventInProgressRepository {
    Api api;
    Gson gson;

    public MyEventInProgressRepository(Api api, Gson gson) {
        this.api = api;
        this.gson = gson;
    }

    @Override
    public InvitationStatusDto inviteToEvent(String token, Long eventId, Long userId) throws ServerException, IOException {
        InvitationStatusDto invitationStatusDto;
        Call<InvitationStatusDto> call = api.inviteToEvent(token,eventId,userId);
        Response<InvitationStatusDto> response=call.execute();
        if (response.isSuccessful()){
            invitationStatusDto=response.body();
        } else {
            ErrorDto error=gson.fromJson(response.errorBody().string(),ErrorDto.class);
            throw new ServerException(error.getCode()+" "+error.getMessage());
        }
        return invitationStatusDto;
    }

    @Override
    public EventDto changeEventStatus(String token, Long eventId) throws ServerException, IOException {
        EventDto event;
        Call<EventDto> call = api.changeEventStatus(token,eventId);
        Response<EventDto> response=call.execute();
        if (response.isSuccessful()){
            event=response.body();
        } else {
            throw new ServerException(response.errorBody().toString());
        }
        return event;
    }
}
