package com.example.bumpin.ApiService;

import android.content.res.Resources;

import com.example.bumpin.R;
import com.example.bumpin.ServerRequestProto.ApiService;
import com.example.bumpin.userPathService.userPathService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = Resources.getSystem().getString(R.string.API_URL);

    // Retrofit Client 생성 후
    public static ApiService getApiService(){
        return getInstance().create(ApiService.class);
    }

    private  static Retrofit getInstance(){
        // setLenient: 처리 시 입력한 값이 오류일 경우. Json 형식의 response를 객체로 변환하기 위해 확인
        Gson gson = new GsonBuilder().setLenient().create();
        // Builder()로 base url 설정 후 GsonConverter로 RetroClient 생성
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL).build();
    }
}
