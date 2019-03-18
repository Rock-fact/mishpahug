package com.kor.foodmanager.data.calendar;

import com.google.gson.Gson;
import com.kor.foodmanager.data.event.ServerException;
import com.kor.foodmanager.data.model.ErrorDto;
import com.kor.foodmanager.data.model.EventListDto;
import com.kor.foodmanager.data.provider.web.Api;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class CalendarRepository implements ICalendarRepository {
    private Api api;
    private Gson gson;

    public CalendarRepository(Api api, Gson gson) {
        this.api = api;
        this.gson = gson;
    }

    @Override
    public EventListDto getEventsForCalendar(String token, int month) throws ServerException, IOException {
        Call<EventListDto> call = api.getListForCalendar(token,month);
        Response<EventListDto> response = call.execute();
        if(response.isSuccessful()){
            EventListDto eventListDto = response.body();
            return eventListDto;
        }else {
            ErrorDto errorDto = gson.fromJson(response.errorBody().string(),ErrorDto.class);
            throw new ServerException(errorDto.getCode()+ ": "+errorDto.getMessage());
        }
    }

}
