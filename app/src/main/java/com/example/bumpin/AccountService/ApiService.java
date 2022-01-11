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

//    public static final String API_URL = ("http://a540-143-248-229-141.ngrok.io").concat("/");
    public static final String API_URL = ("http://192.249.18.117").concat("/");

    @GET("login/validate/{userName}/{passWord}")
    Call<json_pk> login(@Path("userName") String userName, @Path("passWord") String passWord);

    @GET("login/register/{userName}/{passWord}")
    Call<json_pk> register(@Path("userName") String userName, @Path("passWord") String passWord);
    //uN: userName, tN: tripName, data:JsonToString format, len: total number of pin point in path
    @GET("trip/create/{uN}/{tN}/{data}/{len}")
    Call<json_pk> add_Path(@Path("uN") String uN, @Path("tN") String tN, @Path("data") String data, @Path("len") Integer len);
    @GET("trip/delete/{uN}/{tN}")
    Call<json_pk> delete_Path(@Path("uN") String uN, @Path("tN") String tN);
    @GET("trip/get/{uN}")
    Call<Object> total_Path(@Path("uN") String uN);
}
