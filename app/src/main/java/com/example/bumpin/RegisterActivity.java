package com.example.bumpin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.example.bumpin.AccountService.json_Account;
import com.example.bumpin.AccountService.registerPostActivity;
import com.example.bumpin.AccountService.registerValidPostActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText et_id, et_pwd, et_pwd_confirm;
    private String str_id, str_pwd, str_pwd_confirm;
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

                if (is_Valid_input(str_id, str_pwd)) {

                    Intent intent = new Intent(RegisterActivity.this, registerPostActivity.class);
                    intent.putExtra("id", str_id);
                    intent.putExtra("pwd", str_pwd);

                    // 예외처리
                    startActivity(intent);
                    Log.e("valid", "shoould not be here");
                }
                else{

                }

        });
    }

    public boolean is_Valid_input(String str_id, String str_pwd){
        Log.e("check_valid", "id");
        Intent intent = new Intent(RegisterActivity.this, registerValidPostActivity.class);
        intent.putExtra("id", str_id);
        intent.putExtra("pwd", str_pwd);

        // 예외처리
        startActivity(intent);
        Log.e("check_valid", "shoould not be here");

        return true;
//           return false;
    }

}