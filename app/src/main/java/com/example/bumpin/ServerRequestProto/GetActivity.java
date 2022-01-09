package com.example.bumpin.ServerRequestProto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.bumpin.R;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GetActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private ApiService apiService;
    private Call<ResponseBody> comment;
    private String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get);

        // get
        retrofit = new Retrofit.Builder().baseUrl(ApiService.API_URL).build();
        apiService = retrofit.create(ApiService.class);
        comment = apiService.get_Test("json");

        Log.e("D_Test", "response.body().toString())");
        comment.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.isSuccessful()){
                                    try {
                                        Log.e("D_Test", response.body().string());
                                        result = response.body().string();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                                else{
                                    Toast.makeText(getApplicationContext(), "error= "+ String.valueOf(response.code()),
                                            Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                result = "error!!";
                                Log.d("D_GetActivity", t.getMessage().toString());
                                Log.e("D_Test", "페일!");
                            }
                        }
        );

        // Post

    }
}