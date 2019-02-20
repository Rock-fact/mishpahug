package com.kor.foodmanager.di.login;

import com.google.gson.Gson;
import com.kor.foodmanager.buissness.login.ILoginInteractor;
import com.kor.foodmanager.buissness.login.LoginInteractor;
import com.kor.foodmanager.buissness.login.validator.IValidator;
import com.kor.foodmanager.buissness.login.validator.Validator;
import com.kor.foodmanager.data.auth.IAuthRepository;
import com.kor.foodmanager.data.login.ILoginRepository;
import com.kor.foodmanager.data.login.LoginRepository;
import com.kor.foodmanager.data.provider.web.Api;

import dagger.Module;
import dagger.Provides;

@Module
public class LoginModule {

    @Provides @LoginScope
    ILoginRepository provideLoginRepository(Api api, Gson gson){
    return new LoginRepository(api, gson);
    }

    @Provides @LoginScope
    ILoginInteractor provideLoginInteractor(ILoginRepository loginRepository, IAuthRepository authRepository, IValidator validator){
    return new LoginInteractor(loginRepository, authRepository, validator);
    }

    @Provides @LoginScope
    IValidator provideValidator(){
    return new Validator();
    }

}



