package com.kor.foodmanager.data.event;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.gson.Gson;
import com.kor.foodmanager.data.model.ErrorDto;
import com.kor.foodmanager.data.model.EventDto;
import com.kor.foodmanager.data.model.MessageDto;
import com.kor.foodmanager.data.model.PlaceDetailsComponentDto;
import com.kor.foodmanager.data.model.PlaceDetailsDto;
import com.kor.foodmanager.data.provider.web.Api;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Response;

public class EventRepository implements IEventRepository {
    private Api api;
    private Gson gson;
    private OkHttpClient okHttpClient;
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");


    public EventRepository(Api api, Gson gson, OkHttpClient okHttpClient) {
        this.api = api;
        this.gson = gson;
        this.okHttpClient = okHttpClient;
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

    @Override
    public String getCityName(String placeId, String API_KEY) throws IOException, ServerException {
        StringBuilder urlBuilder = new StringBuilder("https://maps.googleapis.com/maps/api/place/details/json?")
                .append("placeid=").append(placeId)
                .append("&fields=").append("address_component")
                .append("&key=").append(API_KEY);
        String url = urlBuilder.toString();
        Request request = new Request.Builder()
                .url(url)
                .build();
        okhttp3.Response response = okHttpClient.newCall(request).execute();
        PlaceDetailsDto placeDetailsDto = gson.fromJson(response.body().string(), PlaceDetailsDto.class);
        if(placeDetailsDto.getStatus().equals("OK")){
            PlaceDetailsComponentDto[] components = placeDetailsDto.getResult().getAddress_components();
            for (PlaceDetailsComponentDto comp: components
            ) {
                if (comp.getTypes().contains("locality")){
                    return comp.getLong_name();
                }
            }
            return "Unknown city";
        }else {
          throw new ServerException(placeDetailsDto.getError_message());
        }
    }
}
