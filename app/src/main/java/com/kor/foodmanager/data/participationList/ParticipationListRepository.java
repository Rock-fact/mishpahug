package com.kor.foodmanager.data.participationList;

import com.google.gson.Gson;
import com.kor.foodmanager.data.event.ServerException;
import com.kor.foodmanager.data.model.EventDto;
import com.kor.foodmanager.data.model.EventListDto;
import com.kor.foodmanager.data.provider.web.Api;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class ParticipationListRepository implements IParticipationListRepository {
    private Api api;
    private Gson gson;

    public ParticipationListRepository(Api api, Gson gson) {
        this.api = api;
        this.gson = gson;
    }

    @Override
    public List<EventDto> loadParticipationList(String token) throws IOException, ServerException {
        Call<EventListDto> call = api.getParticipationList(token);
        Response<EventListDto> response = call.execute();
        if(response.isSuccessful()){
            return response.body().getEvents();
        } else {
            throw new ServerException(response.errorBody().toString());
        }
    }
}
