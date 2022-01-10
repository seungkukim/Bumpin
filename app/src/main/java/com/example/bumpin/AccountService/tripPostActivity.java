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


public class tripPostActivity extends AppCompatActivity {
    private Retrofit retrofit;
    private ApiService apiService;
    private String userName, tripName;
    float latitude, logitude;
    Integer index;
    private Call<Integer> int_call;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_post);

//        userName = getIntent().getStringExtra("userName");
//        tripName = getIntent().getStringExtra("tripName");
//        latitude = getIntent().getFloatExtra("latitude");
//        logitude = getIntent().getFloatExtra("logitude");
//        index = getIntent().getIntExtra("index");

        Log.e("tripPost ", "extra");
        retrofit = new Retrofit
                .Builder()
                .baseUrl(ApiService.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
        json_Trip json_trip = new json_Trip("Seunggu", "Broken Finger", 12, 34, 5);
        int_call = apiService.addMap("json", json_trip);
        int_call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.isSuccessful()){
                    Log.e("register post", "success");
                }
                else{
                    Toast.makeText(getApplicationContext(), "error= "+ String.valueOf(response.code()),
                            Toast.LENGTH_LONG).show();
                    Log.e("register post",  String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.e("register post", t.getMessage());
            }
        });
    }
}