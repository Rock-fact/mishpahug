package com.kor.foodmanager.di.calendar;

import com.google.gson.Gson;
import com.kor.foodmanager.buissness.calendar.CalendarInteractor;
import com.kor.foodmanager.buissness.calendar.ICalendarInteractor;
import com.kor.foodmanager.data.auth.IAuthRepository;
import com.kor.foodmanager.data.calendar.CalendarRepository;
import com.kor.foodmanager.data.calendar.ICalendarRepository;
import com.kor.foodmanager.data.exteral.ExternalRepository;
import com.kor.foodmanager.data.exteral.IExternalRepository;
import com.kor.foodmanager.data.provider.web.Api;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

@Module
public class CalendarModule {

    @Provides @CalendarScope
    ICalendarInteractor provideCalendarInteractor(IAuthRepository authRepository, ICalendarRepository calendarRepository, IExternalRepository externalRepository){
        return new CalendarInteractor(authRepository, calendarRepository, externalRepository);

    };

    @Provides @CalendarScope
    ICalendarRepository provideCalendarRepository(Api api, Gson gson){
        return new CalendarRepository(api,gson);
    }

    @Provides @CalendarScope
    IExternalRepository provideExternalRepository(Gson gson, OkHttpClient okHttpClient){
        return new ExternalRepository(gson, okHttpClient);
    }
}
