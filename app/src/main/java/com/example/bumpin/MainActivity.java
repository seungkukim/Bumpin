package com.example.bumpin;

import androidx.appcompat.app.AppCompatActivity;
import android.content.pm.Signature;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.bumpin.LoginService.LoginPostActivity;
import com.google.gson.Gson;
import com.kakao.sdk.common.util.Utility;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Log.e("GET_KEYHASH", Utility.INSTANCE.getKeyHash(this));

        Intent intent = new Intent(MainActivity.this, LoginPostActivity.class);
        startActivity(intent);

//            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//            startActivity(intent);

//        // Map
//        Button btn_map = findViewById(R.id.btn_map);
//        btn_map.setOnClickListener(view -> {
//            Intent intent = new Intent(MainActivity.this, MapsActivity.class);
//            startActivity(intent);
//        });
//
//        // Login
//        Button btn_login = findViewById(R.id.btn_login);
//        btn_login.setOnClickListener(view -> {
//            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//            startActivity(intent);
//        });
//
//        // Register
//        Button btn_register = findViewById(R.id.btn_register);
//        btn_register.setOnClickListener(view -> {
//            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
//            startActivity(intent);
//        });
}

    }