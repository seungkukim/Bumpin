package com.example.bumpin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

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
    }
}