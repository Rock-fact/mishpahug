package com.kor.foodmanager.buissness.eventInfo;

import com.kor.foodmanager.data.event.ServerException;

import java.io.IOException;

public interface IGuestEventInfoInteractor {
    String joinEvent(long eventId) throws IOException, ServerException;
    String unsubscribeFromEvent(long eventId) throws IOException, ServerException;
    String voteForEvent(long eventId, double rate) throws IOException, ServerException;
}
