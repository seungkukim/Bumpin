package com.example.bumpin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.bumpin.ServerRequestProto.DeleteActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Intent intent = new Intent(MainActivity.this, DeleteActivity.class);
        startActivity(intent);
        //
//        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//        startActivity(intent);
    }
}