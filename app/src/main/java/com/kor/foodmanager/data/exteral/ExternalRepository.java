package com.kor.foodmanager.data.exteral;

import com.google.gson.Gson;
import com.kor.foodmanager.data.model.HebcalDto;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ExternalRepository implements IExternalRepository {
    private Gson gson;
    private OkHttpClient okHttpClient;
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    public ExternalRepository(Gson gson, OkHttpClient okHttpClient) {
        this.gson = gson;
        this.okHttpClient = okHttpClient;
    }

    @Override
    public HebcalDto getIsrHolidays(int month) throws IOException {
        StringBuilder urlBuilder = new StringBuilder("https://www.hebcal.com/hebcal/?")
                .append("v=1&cfg=json&maj=on&min=off&mod=off&nx=on&year=now")
                .append("&month=").append(month)
                .append("&ss=off&mf=off&c=off&s=off&d=off");
        String url = urlBuilder.toString();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = okHttpClient.newCall(request).execute();
        HebcalDto hebcalDto = gson.fromJson(response.body().string(),HebcalDto.class);
        return hebcalDto;
    }
}
