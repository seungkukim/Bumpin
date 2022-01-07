package com.example.bumpin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Register
        Button registerNewButton = findViewById(R.id.registerNewButton);
        registerNewButton.setOnClickListener(view -> {
            Intent intent = new Intent(RegisterActivity.this, MapsActivity.class);
            startActivity(intent);
        });
    }

}