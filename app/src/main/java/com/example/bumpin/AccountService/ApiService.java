package com.example.bumpin.AccountService;

import android.content.res.Resources;

import com.example.bumpin.R;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    public static final String API_URL = ("http://179d-143-248-229-141.ngrok.io").concat("/");

    @POST("login")
    Call<Integer> login(@Query("format") String json, @Body json_Account json_account);

    @POST("login/")
    Call<json_pk> register(@Query("format") String json, @Body json_Account json_account);


    @POST("account/")
    Call<Integer> addMap(@Query("format") String json, @Body json_Trip json_trip);

    @POST("validate/")
    Call<Integer> validate(@Query("format") String json, @Body json_Account json_account);
}
