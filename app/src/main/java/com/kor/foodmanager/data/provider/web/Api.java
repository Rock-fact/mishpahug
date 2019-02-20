package com.kor.foodmanager.data.provider.web;

import com.kor.foodmanager.data.model.StaticfieldsDto;
import com.kor.foodmanager.data.model.UserDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface Api {
    //Unauthorized requests
    @GET("/user/staticfields")
    Call<StaticfieldsDto> getStaticFields();

    //Authorized requests
    @POST("/user/registration")
    Call<StaticfieldsDto> registration(@Header("Authorization") String token);

    @POST("/user/login")
    Call<UserDto> login(@Header("Authorization") String token);




}
