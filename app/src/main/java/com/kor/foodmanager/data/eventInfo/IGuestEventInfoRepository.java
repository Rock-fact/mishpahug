package com.kor.foodmanager.data.eventInfo;

import com.kor.foodmanager.data.event.ServerException;

import java.io.IOException;

public interface IGuestEventInfoRepository {
    String joinEvent(String token, long eventId) throws IOException, ServerException;
    String unsubscribeFromEvent(String token, long eventId) throws IOException, ServerException;
    String voteForEvent(String token, long eventId, double rate) throws IOException, ServerException;

}
