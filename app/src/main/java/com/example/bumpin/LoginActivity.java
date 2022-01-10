package com.example.bumpin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bumpin.AccountService.ApiService;
import com.example.bumpin.AccountService.json_Account;
import com.example.bumpin.AccountService.json_pk;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private EditText et_id, et_pwd, et_pwd_confirm;
    private String str_id, str_pwd, str_pwd_confirm;
    private Retrofit retrofit;
    private ApiService apiService;
    private Call<json_pk> int_call;
    private String pKey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_id = findViewById(R.id.editTextUserName);
        et_pwd = findViewById(R.id.editTextPassword);


        // Login
        Button btn_login = findViewById(R.id.loginButton);
        btn_login.setOnClickListener(view -> {
            //retrofit
            str_id = et_id.getText().toString();
            str_pwd = et_pwd.getText().toString();
            retrofit = new Retrofit
                    .Builder()
                    .baseUrl(ApiService.API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            apiService = retrofit.create(ApiService.class);
            json_Account json_account = new json_Account(str_id, str_pwd);
            int_call = apiService.login( str_id, str_pwd);
            int_call.enqueue(new Callback<json_pk>() {
                @Override
                public void onResponse(Call<json_pk> call, Response<json_pk> response) {
                    if(response.isSuccessful()){
                        json_pk pk =  response.body();
                        pKey = pk.get_pk();
                        Log.e("login post success",  pk.get_pk());

                    }
                    else{
                        Toast.makeText(getApplicationContext(), "error= "+ String.valueOf(response.code()),
                                Toast.LENGTH_LONG).show();
                        Log.e("login post",  String.valueOf(response.code()));
                    }
                }

                @Override
                public void onFailure(Call<json_pk> call, Throwable t) {
                    Log.e("login post", t.getMessage());
                }
            });
            ////

            Intent intent = new Intent(LoginActivity.this, MapsActivity.class);
            intent.putExtra("id", str_id);
            startActivity(intent);
        });

        // Register
        Button btn_register = findViewById(R.id.registerButton);
        btn_register.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}