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

public class RegisterActivity extends AppCompatActivity {

    private EditText et_id, et_pwd, et_pwd_confirm;
    private String str_id, str_pwd, str_pwd_confirm;
    private Retrofit retrofit;
    private ApiService apiService;
    private Call<json_pk> int_call;
    private Boolean login_success;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_id = findViewById(R.id.editTextRegisterUserName);
        et_pwd = findViewById(R.id.editTextRegisterPassword);
        et_pwd_confirm = findViewById(R.id.editTextRegisterConfirmPassword);

        // Register
        Button registerNewButton = findViewById(R.id.registerNewButton);
        registerNewButton.setOnClickListener(view -> {
            str_id = et_id.getText().toString();
            str_pwd = et_pwd.getText().toString();
            str_pwd_confirm = et_pwd_confirm.getText().toString();
            // check is form is not empty
            if (str_id.isEmpty() || str_pwd.isEmpty() || str_pwd_confirm.isEmpty()) {
                et_id.setText("");
                et_pwd.setText("");
                et_pwd_confirm.setText("");
                Toast.makeText(getApplicationContext(), "Fill in Input Form",
                        Toast.LENGTH_SHORT).show();
            }
            else {
                // check if confirm and not confirm the same
                if (!str_pwd_confirm.equals(str_pwd)) {
                    et_pwd.setText("");
                    et_pwd_confirm.setText("");
                    Toast.makeText(getApplicationContext(), "Confirm password does not match",
                            Toast.LENGTH_SHORT).show();
                } else {
                    retrofit = new Retrofit
                            .Builder()
                            .baseUrl(ApiService.API_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    apiService = retrofit.create(ApiService.class);
                    int_call = apiService.register( str_id, str_pwd);
                    int_call.enqueue(new Callback<json_pk>() {
                        @Override
                        public void onResponse(Call<json_pk> call, Response<json_pk> response) {
                            if(response.isSuccessful()){
                                json_pk pk =  response.body();
                                Log.e("register post success",  pk.get_pk());
                                // if user id not exists
                                if (!(pk.get_pk().equals("-1"))) {
                                    Intent intent = new Intent(RegisterActivity.this, MapsActivity.class);
                                    intent.putExtra("id", str_id);
                                    Toast.makeText(getApplicationContext(), "Hello ! ".concat(str_id),
                                            Toast.LENGTH_LONG).show();

                                    startActivity(intent);
                                }
                                else{
                                    Toast.makeText(getApplicationContext(), "Invalid Account. Try Another ID.",
                                            Toast.LENGTH_SHORT).show();
                                }
                                et_id.setText("");
                                et_pwd.setText("");
                                et_pwd_confirm.setText("");

                            }
                            else{
                                Toast.makeText(getApplicationContext(), "error= "+ String.valueOf(response.code()),
                                        Toast.LENGTH_LONG).show();
                                Log.e("register post",  String.valueOf(response.code()));
                            }
                        }

                        @Override
                        public void onFailure(Call<json_pk> call, Throwable t) {
                            Log.e("register post", t.getMessage());
                        }
                    });
                    /////////////////
                    Log.e("valid", "shoould not be here");
                    ////////////////
                }
            }

        });
    }
}