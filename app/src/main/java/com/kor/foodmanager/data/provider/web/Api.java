package com.kor.foodmanager.data.provider.web;

import com.kor.foodmanager.data.model.EventListDto;
import com.kor.foodmanager.data.model.NotificationListDto;
import com.kor.foodmanager.data.model.StaticfieldsDto;
import com.kor.foodmanager.data.model.UserDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Api {
    //Unauthorized requests
    @GET("/user/staticfields")
    Call<StaticfieldsDto> getStaticFields();

    //Authorized requests
    @POST("/user/registration")
    Call<StaticfieldsDto> registration(@Header("Authorization") String token);

    @POST("/user/login")
    Call<UserDto> login(@Header("Authorization") String token);

    //Client receives list of his notifications.
    @GET ("/notification/list")
    Call<NotificationListDto> getNotificatonList(@Header("Authorization") String token);

    //Returns Event List for Calendar
    @GET("/event/calendar/{month}")
    Call<EventListDto> getListForCalendar (@Header("Authorization") String token, @Path("month") int month);


}
