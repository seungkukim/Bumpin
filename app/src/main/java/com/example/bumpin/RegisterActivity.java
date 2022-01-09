package com.example.bumpin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.example.bumpin.userAccountService.AccountPostActivity;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        EditText editTextRegisterUserName = findViewById(R.id.editTextRegisterUserName);
        EditText editTextRegisterPassword = findViewById(R.id.editTextRegisterPassword);
        // Register
        Button registerNewButton = findViewById(R.id.registerNewButton);
        registerNewButton.setOnClickListener(view -> {
            String username = editTextRegisterUserName.getText().toString();
            String password = editTextRegisterPassword.getText().toString();
            Log.e("D_Register", username);
            Intent intent = new Intent(RegisterActivity.this, AccountPostActivity.class);
            intent.putExtra("userName", username);
            intent.putExtra("passWord", password);

            startActivity(intent);
        });
    }

}