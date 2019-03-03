package com.kor.foodmanager.data.notification;

import android.util.Log;

import com.google.gson.Gson;
import com.kor.foodmanager.data.event.ServerException;
import com.kor.foodmanager.data.model.ErrorDto;
import com.kor.foodmanager.data.model.MessageDto;
import com.kor.foodmanager.data.model.NotificationListDto;
import com.kor.foodmanager.data.provider.web.Api;

import java.io.IOException;
import java.io.StreamCorruptedException;

import retrofit2.Call;
import retrofit2.Response;

import static com.kor.foodmanager.ui.MainActivity.TAG;

public class NotificationRepository implements INotificationRepository {
    private Api api;
    private Gson gson;

    public NotificationRepository(Api api, Gson gson) {
        this.api = api;
        this.gson = gson;
    }

    @Override
    public NotificationListDto getNotificationList(String token) throws IOException, ServerException {
        Call<NotificationListDto> call = api.getNotificatonList(token);
        Response<NotificationListDto> response = call.execute();
        if(response.isSuccessful()){
            NotificationListDto list = response.body();
            return list;
        }else {
            ErrorDto errorDto = gson.fromJson(response.errorBody().string(), ErrorDto.class);
            throw new ServerException(errorDto.getCode()+ ": "+errorDto.getMessage());

        }
    }

    @Override
    public void notificationIsRead(String token, Long notificationId) throws IOException, ServerException {
        Call<MessageDto> call = api.notificetionIsRead(token, notificationId);
        Response<MessageDto> response = call.execute();
        if(response.isSuccessful()){
            MessageDto messageDto = response.body();
            Log.d(TAG, "notificationIsRead: "+messageDto.getMessage());
        }else {
            ErrorDto errorDto = gson.fromJson(response.errorBody().string(), ErrorDto.class);
            throw new ServerException(errorDto.getCode()+": "+errorDto.getMessage());
        }
    }
}
