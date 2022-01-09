package com.example.bumpin.userPathService;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bumpin.R;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PathPostActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private userPathService loginService;
    private Call<Json_Path_Java> json_account_java;
    private Call<ResponseBody> comment;
    private  String username;
    private String password;
    private String result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        // retrieve extra from intent
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                Log.e("D_Test", "in null");

                username= "null";
                password= "null";
            } else {
                Log.e("D_Test", extras.getString("userName"));

                username= extras.getString("userName");
                password= extras.getString("passWord");

            }
        } else {
            Log.e("D_Test", "in outer else");

            username= (String) savedInstanceState.getSerializable("userName");
            password= (String) savedInstanceState.getSerializable("passWord");
        }

        // retrofit
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(userPathService.API_URL).build();
        loginService = retrofit.create(userPathService.class);
        Json_Path_Java version = new Json_Path_Java(username, password);
        json_account_java = loginService.post_json_account_java("json", version);
        json_account_java.enqueue(new Callback<Json_Path_Java>() {
           @Override
           // response 에 같이 토큰이 날라오도록
           public void onResponse(Call<Json_Path_Java> call, Response<Json_Path_Java> response) {
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
           public void onFailure(Call<Json_Path_Java> call, Throwable t) {
               result = "error!!";
               Log.e("D_Test", "페일!");
           }
       }
        );

    }
}