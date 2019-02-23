package com.kor.foodmanager.di.application;

import android.content.Context;

import com.google.gson.Gson;
import com.kor.foodmanager.data.auth.AuthRepository;
import com.kor.foodmanager.data.auth.IAuthRepository;
import com.kor.foodmanager.data.provider.web.Api;
import com.kor.foodmanager.data.userData.IUserDataRepository;
import com.kor.foodmanager.data.userData.UserDataRepository;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;

@Module
public class MainModule {
    private static final String BASE_URL = "https://mishpahug-java221-team-a.herokuapp.com";
    private Context context;
    private Cicerone<Router> cicerone;
    private Gson gson;

    public MainModule(Context context){
        this.context = context;
        cicerone = Cicerone.create();
        gson = new Gson();
    }

    @Provides @Singleton
    Context provideContext(){
        return context;
    }

    @Provides @Singleton
    Router provideRouter(){
        return cicerone.getRouter();
    }

    @Provides @Singleton
    NavigatorHolder provideNavigatorHolder(){
        return cicerone.getNavigatorHolder();
    }

    @Provides @Singleton
    OkHttpClient provideHttpClient(){
        return new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .build();
    }

    @Provides @Singleton
    Api provideApi(OkHttpClient client){
        return new Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())  //for future RXJava integration
                .build()
                .create(Api.class);
    }

    @Provides @Singleton
    Gson provideGson(){
        return gson;
    }

    @Provides @Singleton
    IAuthRepository provideAuthRepository(Context context, Gson gson){
        return new AuthRepository(context, gson);
    }

    @Provides @Singleton
    IUserDataRepository provideUserDataRepository(IAuthRepository authRepository, Api api){
        return new UserDataRepository(api, authRepository);
    }
}
