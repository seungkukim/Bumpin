package com.example.bumpin.kakaoOauth;

import android.app.Application;

import com.kakao.sdk.common.KakaoSdk;


public class GlobalApplication extends Application {
        @Override
        public void onCreate() {
        super.onCreate();

        KakaoSdk.init(this, "28aabd53258b01ba09cc78f089f06c30");
        }
}