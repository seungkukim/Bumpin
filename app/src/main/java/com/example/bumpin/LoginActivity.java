package com.example.bumpin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bumpin.AccountService.ApiService;
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
    private Boolean login_success;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_id = findViewById(R.id.editTextUserName);
        et_pwd = findViewById(R.id.editTextPassword);


        // Login
        Button btn_login = findViewById(R.id.loginButton);
        btn_login.setOnClickListener(view -> {
            str_id = et_id.getText().toString();
            str_pwd = et_pwd.getText().toString();

            if (str_id.isEmpty() || str_pwd.isEmpty()) {
                et_id.setText("");
                et_pwd.setText("");
                Toast.makeText(getApplicationContext(), "Fill in Input Form",
                        Toast.LENGTH_SHORT).show();
            }
            else{
                // login_success();
                login_success = false;
                Log.e("login post",  Boolean.toString(login_success));
                //retrofit///////////////////////////////////////////////////////////////////////////////
                retrofit = new Retrofit
                        .Builder()
                        .baseUrl(ApiService.API_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                apiService = retrofit.create(ApiService.class);
                int_call = apiService.login( str_id, str_pwd);
                int_call.enqueue(new Callback<json_pk>() {

                                     @Override
                                     public void onResponse(Call<json_pk> call, Response<json_pk> response) {

                                         Log.e("login post",  "1");
                                         if(response.isSuccessful()){
                                             json_pk pk =  response.body();
                                             if (!(pk.get_pk().equals("-1"))) {
                                                 login_success = true;
                                                 Intent intent = new Intent(LoginActivity.this, MapsActivity.class);
                                                 intent.putExtra("id", str_id);
                                                 Toast.makeText(getApplicationContext(), "Hello ! ".concat(str_id),
                                                         Toast.LENGTH_SHORT).show();
                                                 startActivity(intent);
                                             }
                                             else{
                                                 Toast.makeText(getApplicationContext(), "Invalid Account. Try Again.",
                                                         Toast.LENGTH_SHORT).show();
                                             }
                                             et_id.setText("");
                                             et_pwd.setText("");
                                             Log.e("login post success",  pk.get_pk());
                                         }
                                         else{
                                             Toast.makeText(getApplicationContext(), "error= "+ String.valueOf(response.code()),
                                                     Toast.LENGTH_SHORT).show();
                                             Log.e("login post",  String.valueOf(response.code()));


                                             et_id.setText("");
                                             et_pwd.setText("");
                                             Toast.makeText(getApplicationContext(), "Invalid Account. Try Again.",
                                                     Toast.LENGTH_SHORT).show();

                                         }
                                     }

                                     @Override
                                     public void onFailure(Call<json_pk> call, Throwable t) {
                                         Log.e("login post", t.getMessage());


                                         et_id.setText("");
                                         et_pwd.setText("");
                                         Toast.makeText(getApplicationContext(), "Invalid Account. Try Again.",
                                                 Toast.LENGTH_SHORT).show();
                                     }
                                 }
                );
                /////////////////////////////////////////////////////////////////////////////////////////
            }
            Log.e("login post",  "2");
        });


        // Register
        Button btn_register = findViewById(R.id.registerButton);
        btn_register.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}