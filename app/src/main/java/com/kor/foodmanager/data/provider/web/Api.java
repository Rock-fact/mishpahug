package com.kor.foodmanager.data.provider.web;

import com.kor.foodmanager.data.model.EventDto;
import com.kor.foodmanager.data.model.ErrorDto;
import com.kor.foodmanager.data.model.EventDto;
import com.kor.foodmanager.data.model.EventListDto;
import com.kor.foodmanager.data.model.InvitationStatusDto;
import com.kor.foodmanager.data.model.NotificationDto;
import com.kor.foodmanager.data.model.NotificationListDto;
import com.kor.foodmanager.data.model.StaticfieldsDto;
import com.kor.foodmanager.data.model.MessageDto;
import com.kor.foodmanager.data.model.UserDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {
    //Unauthorized requests
    //---------------------------------------------------------------------------------------------
    @GET("/user/staticfields")
    Call<StaticfieldsDto> getStaticFields();


    //List of Events in progress  -- TODO WTF?
    @POST("/event/allprogresslist?page={page}&size={size}")
    Call<EventListDto> getListOfEventsInProgress(@Path("page") int page, @Path("size") int size);


    //Authorized requests
    //---------------------------------------------------------------------------------------------
    @POST("/user/registration")
    Call<StaticfieldsDto> registration(@Header("Authorization") String token);

    @POST("/user/login")
    Call<UserDto> login(@Header("Authorization") String token);

    //Update User Profile
    Call<UserDto> updateUserProfile(@Header("Authorization") String token, @Body UserDto user); //TODO auto parsing?

    //Client receives list of his notifications.
    @GET("/notification/list")
    Call<NotificationListDto> getNotificatonList(@Header("Authorization") String token);

    //Returns Event List for Calendar
    @GET("/event/calendar/{month}")
    Call<EventListDto> getListForCalendar(@Header("Authorization") String token, @Path("month") int month);

    //Create new event
    @POST("/event/creation")
    Call<ErrorDto> createNewEvent(@Header("Authorization") String token, @Body EventDto event);

    //User receives the event that he created.
    @GET("/event/own/{eventId}")
    Call<EventDto> getMyEvent(@Header("Authorization") String token, @Path("eventId") Long eventId);

    //User receives the event to he is subscribed.
    @GET("/event/subscribed/{eventId}")
    Call<EventDto> getSubscribedEvent(@Header("Authorization") String token, @Path("eventId") Long eventId);

    //User receives list of events at status "in progress" and "pending" which this user created.
    @GET("/event/currentlist")
    Call<EventListDto> getMyEventList(@Header("Authorization") String token);

    //User receives list of all events at status "done" that he created.
    @GET("/event/historylist")
    Call<EventListDto> getDoneEvents(@Header("Authorization") String token);

    //User receives list of events which user subscribed or will participate.
    // Events at status "done" in which user participated includes into list only if user didn’t vote them.
    @GET("/event/participationlist")
    Call<EventListDto> getParticipationList(@Header("Authorization") String token); //TODO specify EventListDto

    //User receives number of unread notifications
    @GET("/notification/count")
    Call<Integer> getUnreadNotificationsCount(@Header("Authorization") String token);

    //Subscribe to event
    @PUT("/event/subscription/{eventId}")
    Call<MessageDto> subscribeToEvent(@Header("Authorization") String token, @Path("eventId") Long eventId);

    //Unsubscribe from event
    @PUT("/event/unsubscription/{eventId}")
    Call<MessageDto> unsubscribeFromEvent(@Header("Authorization") String token, @Path("eventId") Long eventId);

    //Vote for event
    @PUT("/event/vote/{eventId}/{voteCount}")
    Call<MessageDto> voteForEvent(@Header("Authorization") String token, @Path("eventId") Long eventId,
                                  @Path("voteCount") Double voteCount);

    //Invite to event
    @PUT("/event/invitation/{eventId}/{userId}")
    Call<InvitationStatusDto> inviteToEvent(@Header("Authorization") String token, @Path("eventId") Long eventId,
                                            @Path("userId") Long userId); //TODO maybe UserDto

    //Change event status
    @PUT("/event/pending/{eventId}")
    Call<EventDto> changeEventStatus(@Header("Authorization") String token, @Path("eventId") Long eventId);

    //Notification is read
    @PUT("/notification/isRead/{notificationId}")
    Call<MessageDto> notificetionIsRead(@Header("Authorization") String token,
                                             @Path("notificationId") Long notificationId);


}
