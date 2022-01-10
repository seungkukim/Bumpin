package com.example.bumpin.AccountService;

import android.content.res.Resources;

import com.example.bumpin.R;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    public static final String API_URL = ("http://192.249.18.117").concat("/");

    @GET("login/")
    Call<json_pk> login(@Query("format") String json, @Body json_Account json_account);

    @GET("login/register/{userName}/{passWord}")
    Call<json_pk> register(@Path("userName") String userName, @Path("passWord") String passWord);


    @GET("account/")
    Call<Integer> addMap(@Query("format") String json, @Body json_Trip json_trip);

    @GET("validate/")
    Call<json_pk> validate(@Query("format") String json, @Body json_Account json_account);
}
