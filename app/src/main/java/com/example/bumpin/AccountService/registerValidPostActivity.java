package com.example.bumpin.AccountService;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bumpin.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class registerValidPostActivity extends AppCompatActivity {
    private Retrofit retrofit;
    private ApiService apiService;
    private String str_id, str_pwd;
    private Call<json_pk> int_call;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_valid_post);

        str_id = getIntent().getStringExtra("id");
        str_pwd = getIntent().getStringExtra("pwd");
        Log.e("str, pwd passed", "str_id");
        retrofit = new Retrofit
                .Builder()
                .baseUrl(ApiService.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
        json_Account json_account = new json_Account(str_id, str_pwd);
        int_call = apiService.validate("json", json_account);
        int_call.enqueue(new Callback<json_pk>() {
            @Override
            public void onResponse(Call<json_pk> call, Response<json_pk> response) {
                if(response.isSuccessful()){
                    json_pk body = response.body();
                    Log.e("valid post", body.get_pk());
                }
                else{
                    Toast.makeText(getApplicationContext(), "error= "+ String.valueOf(response.code()),
                            Toast.LENGTH_LONG).show();
                    Log.e("valid post",  String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<json_pk> call, Throwable t) {
                Log.e("valid post", t.getMessage());
            }
        });
    }
}