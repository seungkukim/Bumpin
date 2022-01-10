package com.example.bumpin.AccountService;

import android.content.res.Resources;

import com.example.bumpin.R;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient(){
        if (retrofit == null){
            retrofit = new Retrofit
                    .Builder()
                    .baseUrl(Resources.getSystem().getString(R.string.ARI_URL))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
