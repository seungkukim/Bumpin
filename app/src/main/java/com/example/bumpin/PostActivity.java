package com.example.bumpin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private ApiService apiService;
    private Call<Json_Test_Java> json_test_javaCall;
    private Call<ResponseBody> comment;
    private  String result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ApiService.API_URL).build();
        apiService = retrofit.create(ApiService.class);
        Json_Test_Java version = new Json_Test_Java("test5");
        json_test_javaCall = apiService.post_json_test_java("json", version);
        json_test_javaCall.enqueue(new Callback<Json_Test_Java>() {
           @Override
           public void onResponse(Call<Json_Test_Java> call, Response<Json_Test_Java> response) {
               if (response.isSuccessful()){
                   Log.e("D_Test", "2차");
                   if (response.isSuccessful()) {
                       Log.e("post", "성공");
                   } else {
                        Integer StatusCode = response.code();


                       Log.e("post", "Status Code : $StatusCode");
                   }
               }
               else{
                   Toast.makeText(getApplicationContext(), "error= "+ String.valueOf(response.code()),
                           Toast.LENGTH_LONG).show();
               }
           }

           @Override
           public void onFailure(Call<Json_Test_Java> call, Throwable t) {
               result = "error!!";
               Log.e("D_Test", "페일!");
           }
       }
        );

    }
}