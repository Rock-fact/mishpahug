package com.kor.foodmanager.data.eventInfo;

import com.kor.foodmanager.data.event.ServerException;

import java.io.IOException;

public interface IGuestEventInfoRepository {
    String joinEvent(String token, long eventId) throws IOException, ServerException;
}
