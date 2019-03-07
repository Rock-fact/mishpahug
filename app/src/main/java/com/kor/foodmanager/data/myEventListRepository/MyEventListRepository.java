package com.kor.foodmanager.data.myEventListRepository;

import com.google.gson.Gson;
import com.kor.foodmanager.data.event.ServerException;
import com.kor.foodmanager.data.model.EventDto;
import com.kor.foodmanager.data.model.EventListDto;
import com.kor.foodmanager.data.provider.web.Api;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class MyEventListRepository implements IMyEventListRepository {
    Api api;
    Gson gson;

    public MyEventListRepository(Api api, Gson gson) {
        this.api = api;
        this.gson = gson;
    }

    @Override
    public List<EventDto> loadMyEventList(String token) throws ServerException, IOException {
        List<EventDto> list;
        Call<EventListDto> call = api.getMyEventList(token);
        Response<EventListDto> response=call.execute();
        if (response.isSuccessful()){
            list=response.body().getEvents();
        } else {
            throw new ServerException(response.errorBody().toString());
        }

        call=api.getDoneEvents(token);
        response=call.execute();
        if (response.isSuccessful()){
            list.addAll(response.body().getEvents());
        } else {
            throw new ServerException(response.errorBody().toString());
        }
        return list;
    }
}
