package com.example.bumpin.userAccountService;

import android.content.res.Resources;

import com.example.bumpin.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientAccount {
    private static final String BASE_URL = Resources.getSystem().getString(R.string.API_URL);

    public static userAccountService getApiService(){
        return getInstance().create(userAccountService.class);
    }

    private  static Retrofit getInstance(){
        // setLenient: 처리 시 입력한 값이 오류일 경우
        Gson gson = new GsonBuilder().setLenient().create();

        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL).build();
    }
}
