package com.kor.foodmanager.data.participationList;

import com.google.gson.Gson;
import com.kor.foodmanager.data.model.EventListDto;
import com.kor.foodmanager.data.provider.web.Api;

public class ParticipationListRepository implements IParticipationListRepository {
    private Api api;
    private Gson gson;

    public ParticipationListRepository(Api api, Gson gson) {
        this.api = api;
        this.gson = gson;
    }

    @Override
    public EventListDto loadParticipationList(String token) {
        return null;
    }
}
