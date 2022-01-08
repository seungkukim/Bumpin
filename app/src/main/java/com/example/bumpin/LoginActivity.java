package com.example.bumpin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.kakao.sdk.user.UserApiClient;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Login
        Button btn_login = findViewById(R.id.loginButton);
        btn_login.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, MapsActivity.class);
            startActivity(intent);
        });

        // Register
        Button btn_register = findViewById(R.id.registerButton);
        btn_register.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        // Login
        Button btn_kakaologin = findViewById(R.id.kakaoLoginButton);
        btn_login.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, KakaoActivity.class);
            startActivity(intent);
        });
        
    }
}